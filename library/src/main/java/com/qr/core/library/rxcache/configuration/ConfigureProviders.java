package com.qr.core.library.rxcache.configuration;

import java.lang.reflect.Method;

public interface ConfigureProviders {
    Configure process(final Method method, final Object[] args);
}
