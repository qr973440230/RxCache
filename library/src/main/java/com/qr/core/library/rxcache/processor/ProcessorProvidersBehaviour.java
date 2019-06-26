package com.qr.core.library.rxcache.processor;

import com.qr.core.library.rxcache.annotation.OnCacheStrategy;
import com.qr.core.library.rxcache.cache.Record;
import com.qr.core.library.rxcache.cache.TwoLayersCache;
import com.qr.core.library.rxcache.configuration.Configure;
import com.qr.core.library.rxcache.configuration.ConfigureProviders;

import java.lang.reflect.Method;
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
    public Observable<Object> process(Method method, Object[] args) {
        return Observable.defer(new Callable<ObservableSource<Object>>() {
            @Override
            public ObservableSource<Object> call() throws Exception {
                Configure configure = configureProviders.process(method, args);
                Observable<Object> loaderObservable = configure.getLoaderObservable();
                return getCacheObservable(configure,loaderObservable);
            }
        });
    }

    public Observable<Object> getCacheObservable(Configure configure,Observable<Object> loaderObservable){
        if(configure.getCacheStrategy() == OnCacheStrategy.Default){
            return Observable.create(emitter -> {
                Record retrieve = twoLayersCache.retrieve(configure.getProviderKey(), configure.getDynamicKey(), configure.getDynamicGroupKey());
                if(retrieve != null){
                    emitter.onNext(retrieve.getData());
                }

                Object o = loaderObservable.blockingFirst();
                twoLayersCache.save(configure.getProviderKey(),
                        configure.getDynamicKey(),
                        configure.getDynamicGroupKey(),
                        o,
                        configure.getSurvivalTime());
                emitter.onNext(o);
                emitter.onComplete();
            });
        }else if(configure.getCacheStrategy() == OnCacheStrategy.NetworkPriority){
            return loaderObservable
                    .doOnNext(o -> {
                        twoLayersCache.save(configure.getProviderKey(),
                                configure.getDynamicKey(),
                                configure.getDynamicGroupKey(),
                                o,
                                configure.getSurvivalTime());
                    })
                    .onErrorResumeNext(throwable -> {
                        Record retrieve = twoLayersCache.retrieve(configure.getProviderKey(),
                                configure.getDynamicKey(),
                                configure.getDynamicGroupKey());
                        if(retrieve != null){
                            return Observable.just(retrieve.getData());
                        }
                        return Observable.error(throwable);
                    });
        }else if(configure.getCacheStrategy() == OnCacheStrategy.CachePriority){
            return Observable.fromCallable(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    return twoLayersCache.retrieve(configure.getProviderKey(),
                            configure.getDynamicKey(),
                            configure.getDynamicGroupKey()).getData();
                }
            }).onErrorResumeNext(loaderObservable.doOnNext(o -> {
                twoLayersCache.save(configure.getProviderKey(),
                        configure.getDynamicKey(),
                        configure.getDynamicGroupKey(),
                        o,
                        configure.getSurvivalTime());
            }));
        }else if(configure.getCacheStrategy() == OnCacheStrategy.OnlyNetwork){
            return loaderObservable.doOnNext(o -> {
                twoLayersCache.save(configure.getProviderKey(),
                        configure.getDynamicKey(),
                        configure.getDynamicGroupKey(),
                        o,
                        configure.getSurvivalTime());
            });
        }else if(configure.getCacheStrategy() == OnCacheStrategy.OnlyCache){
            return Observable.create(emitter -> {
                Record retrieve = twoLayersCache.retrieve(configure.getProviderKey(),
                        configure.getDynamicKey(),
                        configure.getDynamicGroupKey());
                emitter.onNext(retrieve.getData());
                emitter.onComplete();
            });
        }

        throw new RuntimeException("未实现的策略: " + configure.getCacheStrategy());
    }
}
