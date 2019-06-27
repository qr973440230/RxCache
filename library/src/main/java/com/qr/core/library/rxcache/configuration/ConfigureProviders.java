package com.qr.core.library.rxcache.configuration;

import java.lang.reflect.Method;

public interface ConfigureProviders {
    <T> Configure<T> process(final Method method, final Object[] args);
}
