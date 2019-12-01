package com.qr.core.library.rxcache.processor;

import com.qr.core.library.rxcache.cache.Record;
import com.qr.core.library.rxcache.cache.TwoLayersCache;
import com.qr.core.library.rxcache.configuration.Configure;
import com.qr.core.library.rxcache.configuration.ConfigureProviders;
import com.qr.core.library.rxcache.exception.CacheExpirationException;
import com.qr.core.library.rxcache.keys.CacheStrategyKey;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

@Singleton
public final class ProcessorProvidersBehaviour implements ProcessorProviders {
    private final TwoLayersCache twoLayersCache;
    private final ConfigureProviders configureProviders;

    @Inject
    public ProcessorProvidersBehaviour(TwoLayersCache twoLayersCache, ConfigureProviders configureProviders) {
        this.twoLayersCache = twoLayersCache;
        this.configureProviders = configureProviders;
    }

    @Override
    public <T> Observable<T> process(Method method, Object[] args) {
        return Observable.defer((Callable<ObservableSource<T>>) () -> {
            Configure<T> configure = configureProviders.process(method, args);
            return getCacheObservable(configure);
        });
    }

    @Override
    public void evictAll() {
        twoLayersCache.evictAll();
    }

    private <T> Observable<T> getCacheObservable(Configure<T> configure) {
        if (configure.isEvict()) {
            // 先清掉缓存
            twoLayersCache.evictDynamicGroupKey(configure.getProviderKey(),
                    configure.getDynamicKeys());
        }

        if (configure.getCacheStrategy() == CacheStrategyKey.NetworkPriority) {
            return configure.getLoaderObservable()
                    .map(t -> {
                        twoLayersCache.save(configure.getProviderKey(),
                                configure.getDynamicKeys(),
                                t,
                                configure.getSurvivalTime());
                        return t;
                    })
                    .onErrorResumeNext(throwable -> {
                        Record<T> record = twoLayersCache.retrieve(configure.getProviderKey(),
                                configure.getDynamicKeys());
                        if (record != null) {
                            // 有缓存数据就返回缓存数据
                            return Observable.just(record.getData());
                        }

                        // 无缓存数据 返回loader错误信息
                        return Observable.error(throwable);
                    });
        } else if (configure.getCacheStrategy() == CacheStrategyKey.CachePriority) {
            return Observable.fromCallable(() -> {
                Record<T> record = twoLayersCache.retrieve(configure.getProviderKey(),
                        configure.getDynamicKeys());
                if (record != null) {
                    return record.getData();
                }

                return null;
            }).onErrorResumeNext(configure.getLoaderObservable().map(t -> {
                twoLayersCache.save(configure.getProviderKey(),
                        configure.getDynamicKeys(),
                        t,
                        configure.getSurvivalTime());
                return t;
            }));
        } else if (configure.getCacheStrategy() == CacheStrategyKey.NetworkOnly) {
            return configure.getLoaderObservable().map(t -> {
                twoLayersCache.save(configure.getProviderKey(),
                        configure.getDynamicKeys(),
                        t,
                        configure.getSurvivalTime());
                return t;
            });
        } else if (configure.getCacheStrategy() == CacheStrategyKey.CacheOnly) {
            return Observable.fromCallable(() -> {
                Record<T> retrieve = twoLayersCache.retrieve(configure.getProviderKey(),
                        configure.getDynamicKeys());
                if (retrieve != null) {
                    return retrieve.getData();
                }
                return null;
            });
        } else if (configure.getCacheStrategy() == CacheStrategyKey.CacheControl) {
            return Observable.fromCallable(() -> {
                Record<T> retrieve = twoLayersCache.retrieve(configure.getProviderKey(),
                        configure.getDynamicKeys());
                if (retrieve == null) {
                    return null;
                }

                // 重新设置过期时间 防止接口更改时间后 数据可能不过期的情况
                retrieve.setSurvivalTime(configure.getSurvivalTime());

                // survivalTime <= 0 为永久保存
                if (retrieve.getSurvivalTime() > 0) {
                    if (retrieve.getPersistedTime() + retrieve.getSurvivalTime() < System.currentTimeMillis()) {
                        // 缓存过期 清楚缓存
                        twoLayersCache.evictDynamicGroupKey(configure.getProviderKey(),
                                configure.getDynamicKeys());
                        throw new CacheExpirationException(String.format("RxCache: 缓存过期!!! ProviderKey: %s DynamicKey: %s",
                                configure.getProviderKey(),
                                Arrays.toString(configure.getDynamicKeys())));
                    }
                }

                return retrieve.getData();
            }).onErrorResumeNext(configure.getLoaderObservable().map(t -> {
                twoLayersCache.save(configure.getProviderKey(),
                        configure.getDynamicKeys(),
                        t,
                        configure.getSurvivalTime());
                return t;
            }));
        }

        throw new RuntimeException("未实现的策略: " + configure.getCacheStrategy());
    }
}
