package com.qr.core.library.rxcache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheProvider {
    String providerKey();
    long survivalTime() default 0;
    @OnCacheStrategy
    int onCacheStrategy() default OnCacheStrategy.Default;
}
