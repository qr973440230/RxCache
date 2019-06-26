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
    public <T> Observable<T> process(Method method, Object[] args) {
        return Observable.defer(new Callable<ObservableSource<? extends T>>() {
            @Override
            public ObservableSource<? extends T> call() throws Exception {
                Configure configure = configureProviders.process(method, args);
                Observable<T> loaderObservable = configure.getLoaderObservable();
                return getCacheObservable(configure,loaderObservable);
            }
        });
    }

    public <T> Observable<T> getCacheObservable(Configure configure,Observable<T> loaderObservable){
        if(configure.getCacheStrategy() == OnCacheStrategy.Default){
            return Observable.create(emitter -> {
                Record<T> retrieve = twoLayersCache.<T>retrieve(configure.getProviderKey(), configure.getDynamicKey(), configure.getDynamicGroupKey());
                if(retrieve != null){
                    emitter.onNext(retrieve.getData());
                }

                T t = loaderObservable.blockingFirst();
                twoLayersCache.save(configure.getProviderKey(),configure.getDynamicKey(),configure.getDynamicGroupKey(),t,configure.getSurvivalTime());
                emitter.onNext(t);
                emitter.onComplete();
            });
        }else if(configure.getCacheStrategy() == OnCacheStrategy.NetworkPriority){
            return loaderObservable
                    .doOnNext(t -> {
                        twoLayersCache.save(configure.getProviderKey(),configure.getDynamicKey(),configure.getDynamicGroupKey(),t,configure.getSurvivalTime());
                    })
                    .onErrorResumeNext(throwable -> {
                        Record<T> retrieve = twoLayersCache.<T>retrieve(configure.getProviderKey(), configure.getDynamicKey(), configure.getDynamicGroupKey());
                        if(retrieve != null){
                            return Observable.just(retrieve.getData());
                        }
                        return Observable.<T>error(throwable);
                    });
        }else if(configure.getCacheStrategy() == OnCacheStrategy.CachePriority){
            return Observable.fromCallable(new Callable<T>() {
                @Override
                public T call() throws Exception {
                    return twoLayersCache.<T>retrieve(configure.getProviderKey(), configure.getDynamicKey(), configure.getDynamicGroupKey()).getData();
                }
            }).onErrorResumeNext(loaderObservable.doOnNext(t -> {
                twoLayersCache.save(configure.getProviderKey(),configure.getDynamicKey(),configure.getDynamicGroupKey(),t,configure.getSurvivalTime());
            }));
        }else if(configure.getCacheStrategy() == OnCacheStrategy.OnlyNetwork){
            return loaderObservable.doOnNext(t -> {
                twoLayersCache.save(configure.getProviderKey(),configure.getDynamicKey(),configure.getDynamicGroupKey(),t,configure.getSurvivalTime());
            });
        }else if(configure.getCacheStrategy() == OnCacheStrategy.OnlyCache){
            return Observable.fromCallable(new Callable<T>() {
                @Override
                public T call() throws Exception {
                    return twoLayersCache.<T>retrieve(configure.getProviderKey(), configure.getDynamicKey(), configure.getDynamicGroupKey()).getData();
                }
            });
        }

        throw new RuntimeException("未实现的策略: " + configure.getCacheStrategy());
    }
}
