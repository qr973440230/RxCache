package com.qr.core.library.rxcache.provider.processor;

import com.qr.core.library.rxcache.TwoLayersCache;
import com.qr.core.library.rxcache.entity.Record;
import com.qr.core.library.rxcache.provider.config.ConfigProvider;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

public final class ProcessorProvidersBehaviour implements ProcessorProviders {
    private final TwoLayersCache twoLayersCache;

    public ProcessorProvidersBehaviour(TwoLayersCache twoLayersCache) {
        this.twoLayersCache = twoLayersCache;
    }

    @Override
    public <T> Observable<T> process(ConfigProvider configProvider) {
        return Observable.defer(new Callable<ObservableSource<? extends T>>() {
            @Override
            public ObservableSource<? extends T> call() throws Exception {

                return null;
            }
        });
    }

    private <T> Observable<T> getData(final  ConfigProvider configProvider){
        Record<T> record = twoLayersCache.retrieve(configProvider.getProviderKey(), configProvider.getDynamicKey(), configProvider.getDynamicGroupKey(),
                configProvider.isUseExpiredData(), configProvider.isEncrypted());

        return null;
    }

    @Override
    public Observable<Void> evictAll() {
        return null;
    }
}
