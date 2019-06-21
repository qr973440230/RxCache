package com.qr.core.library.rxcache.processor;

import com.qr.core.library.rxcache.cache.TwoLayersCache;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

@Singleton
public final class ProcessorProvidersBehaviour implements ProcessorProviders {
    private final TwoLayersCache twoLayersCache;

    @Inject
    public ProcessorProvidersBehaviour(TwoLayersCache twoLayersCache) {
        this.twoLayersCache = twoLayersCache;
    }

    @Override
    public <T> Observable<T> process(Method method, Object[] args) {
        return Observable.defer(new Callable<ObservableSource<? extends T>>() {
            @Override
            public ObservableSource<? extends T> call() throws Exception {
                // TODO: 根据函数配置进行缓存逻辑

                return (ObservableSource<? extends T>) args[0];
            }
        });
    }
}
