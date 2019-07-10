package com.qr.core.library.rxcache.processor;

import java.lang.reflect.Method;

import io.reactivex.Observable;

public interface ProcessorProviders {
    <T> Observable<T> process(final Method method,final Object[] args);
    void evictAll();
}
