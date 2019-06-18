package com.qr.core.library.rxcache.provider.processor;

import com.qr.core.library.rxcache.provider.config.ConfigProvider;

import io.reactivex.Observable;

public interface ProcessorProviders {

    <T> Observable<T> process(final ConfigProvider configProvider);

    Observable<Void> evictAll();
}
