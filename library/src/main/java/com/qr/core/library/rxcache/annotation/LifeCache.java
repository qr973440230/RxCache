package com.qr.core.library.rxcache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LifeCache {
    long survivalTime();
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
