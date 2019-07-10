package com.qr.core.library.rxcache.processor;

import com.qr.core.library.rxcache.annotation.OnCacheStrategy;
import com.qr.core.library.rxcache.cache.Record;
import com.qr.core.library.rxcache.cache.TwoLayersCache;
import com.qr.core.library.rxcache.configuration.Configure;
import com.qr.core.library.rxcache.configuration.ConfigureProviders;
import com.qr.core.library.rxcache.exception.CacheExpirationException;
import com.qr.core.library.rxcache.exception.CacheNoSuchElementException;


import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;
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

    public <T> Observable<T> getCacheObservable(Configure<T> configure){
        if(configure.getCacheStrategy() == OnCacheStrategy.Default){
            // 先缓存后数据
            Observable<T> localObservable = Maybe.fromCallable(() -> {
                Record<T> record = twoLayersCache.retrieve(configure.getProviderKey(),
                        configure.getDynamicKey(),
                        configure.getDynamicGroupKey());
                if (record != null) {
                    return record.getData();
                }
                return null;
            }).toObservable();
            return Observable.concatArrayEagerDelayError(localObservable,
                    configure.getLoaderObservable().map(t -> {
                        twoLayersCache.save(configure.getProviderKey(),
                                configure.getDynamicKey(),
                                configure.getDynamicGroupKey(),
                                t,
                                configure.getSurvivalTime());
                        return t;
                    }));
        }else if(configure.getCacheStrategy() == OnCacheStrategy.NetworkPriority){
            return configure.getLoaderObservable()
                    .map(t -> {
                        twoLayersCache.save(configure.getProviderKey(),
                                configure.getDynamicKey(),
                                configure.getDynamicGroupKey(),
                                t,
                                configure.getSurvivalTime());
                        return t;
                    })
                    .onErrorResumeNext(throwable -> {
                        Record<T> record = twoLayersCache.retrieve(configure.getProviderKey(),
                                configure.getDynamicKey(),
                                configure.getDynamicGroupKey());
                        if (record != null) {
                            // 有缓存数据就返回缓存数据
                            return Observable.just(record.getData());
                        }

                        // 无缓存数据 返回loader错误信息
                        return Observable.error(throwable);
                    });
        }else if(configure.getCacheStrategy() == OnCacheStrategy.CachePriority){
            return Observable.fromCallable(() -> {
                Record<T> record = twoLayersCache.retrieve(configure.getProviderKey(),
                        configure.getDynamicKey(),
                        configure.getDynamicGroupKey());
                if(record == null){
                    throw new NoSuchElementException();
                }
                return record.getData();
            }).onErrorResumeNext(configure.getLoaderObservable().map(t -> {
                twoLayersCache.save(configure.getProviderKey(),
                        configure.getDynamicKey(),
                        configure.getDynamicGroupKey(),
                        t,
                        configure.getSurvivalTime());
                return t;
            }));
        }else if(configure.getCacheStrategy() == OnCacheStrategy.OnlyNetwork){
            return configure.getLoaderObservable().map(t -> {
                twoLayersCache.save(configure.getProviderKey(),
                        configure.getDynamicKey(),
                        configure.getDynamicGroupKey(),
                        t,
                        configure.getSurvivalTime());
                return t;
            });
        }else if(configure.getCacheStrategy() == OnCacheStrategy.OnlyCache){
            return Observable.fromCallable(() -> {
                Record<T> retrieve = twoLayersCache.retrieve(configure.getProviderKey(),
                        configure.getDynamicKey(),
                        configure.getDynamicGroupKey());
                if(retrieve == null){
                    throw new CacheNoSuchElementException(String.format("RxCache: 无数据!!! ProviderKey: %s DynamicKey: %s DynamicGroupKey: %s",
                            configure.getProviderKey(),
                            configure.getDynamicKey(),
                            configure.getDynamicGroupKey()));
                }
                return retrieve.getData();
            });
        }else if(configure.getCacheStrategy() == OnCacheStrategy.CacheControl){

            return Observable.fromCallable(()->{
                Record<T> retrieve = twoLayersCache.retrieve(configure.getProviderKey(),configure.getDynamicKey(),configure.getDynamicGroupKey());
                if(retrieve == null){
                    throw new CacheNoSuchElementException(String.format("RxCache: 无数据!!! ProviderKey: %s DynamicKey: %s DynamicGroupKey: %s",
                            configure.getProviderKey(),
                            configure.getDynamicKey(),
                            configure.getDynamicGroupKey()));
                }


                // 重新设置过期时间 防止接口更改时间后 数据可能不过期的情况
                retrieve.setSurvivalTime(configure.getSurvivalTime());

                // survivalTime <= 0 为永久保存
                if(retrieve.getSurvivalTime() > 0){
                    if(retrieve.getPersistedTime() + retrieve.getSurvivalTime() < System.currentTimeMillis()){
                        throw new CacheExpirationException(String.format("RxCache: 缓存过期!!! ProviderKey: %s DynamicKey: %s DynamicGroupKey: %s",
                                configure.getProviderKey(),
                                configure.getDynamicKey(),
                                configure.getDynamicGroupKey()));
                    }
                }

                return retrieve.getData();
            }).onErrorResumeNext(configure.getLoaderObservable().map(t -> {
                twoLayersCache.save(configure.getProviderKey(),
                        configure.getDynamicKey(),
                        configure.getDynamicGroupKey(),
                        t,
                        configure.getSurvivalTime());
                return t;
            }));
        }

        throw new RuntimeException("未实现的策略: " + configure.getCacheStrategy());
    }
}
