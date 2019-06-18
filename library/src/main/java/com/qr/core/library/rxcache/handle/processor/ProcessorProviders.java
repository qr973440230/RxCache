package com.qr.core.library.rxcache.handle.processor;

import com.qr.core.library.rxcache.configuration.ConfigProvider;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface ProcessorProviders {
    <T> Observable<T> process(final ConfigProvider configProvider);
}
