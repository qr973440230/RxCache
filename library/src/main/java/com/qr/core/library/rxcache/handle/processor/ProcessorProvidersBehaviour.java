package com.qr.core.library.rxcache.handle.processor;

import com.qr.core.library.rxcache.cache.TwoLayersCache;
import com.qr.core.library.rxcache.entity.Record;
import com.qr.core.library.rxcache.configuration.ConfigProvider;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

public final class ProcessorProvidersBehaviour implements ProcessorProviders {
    private final TwoLayersCache twoLayersCache;

    public ProcessorProvidersBehaviour(TwoLayersCache twoLayersCache) {
        this.twoLayersCache = twoLayersCache;
    }

    @Override
    public <T> Observable<T> process(final ConfigProvider configProvider) {
        return Observable.defer(new Callable<ObservableSource<? extends T>>() {
            @Override
            public ObservableSource<? extends T> call() throws Exception {
                // TODO: 根据配置进行缓存逻辑
                return getData(configProvider);
            }
        });
    }

    private <T> Observable<T> getData(final  ConfigProvider configProvider){
        // TODO: 处理数据
        Record<T> record = twoLayersCache.retrieve(configProvider.getProviderKey(), configProvider.getDynamicKey(), configProvider.getDynamicGroupKey(),
                configProvider.isUseExpiredData(), configProvider.isEncrypted());
        return Observable.just(record.getData());
    }
}
