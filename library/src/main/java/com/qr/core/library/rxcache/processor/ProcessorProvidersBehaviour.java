package com.qr.core.library.rxcache.processor;

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

                return loaderObservable;
            }
        });
    }
}
