package com.qr.core.library.rxcache.configuration;

import io.reactivex.Observable;

public class Configure {
    private final String providerKey;
    private final String dynamicKey;
    private final String dynamicGroupKey;
    private final int    cacheStrategy;
    private final long    survivalTime;
    private final Observable<Object> loaderObservable;

    public Configure(String providerKey, String dynamicKey, String dynamicGroupKey, int cacheStrategy, long survivalTime, Observable<Object> loaderObservable) {
        this.providerKey = providerKey;
        this.dynamicKey = dynamicKey;
        this.dynamicGroupKey = dynamicGroupKey;
        this.cacheStrategy = cacheStrategy;
        this.survivalTime = survivalTime;
        this.loaderObservable = loaderObservable;
    }

    public String getProviderKey() {
        return providerKey;
    }

    public String getDynamicKey() {
        return dynamicKey;
    }

    public String getDynamicGroupKey() {
        return dynamicGroupKey;
    }

    public int getCacheStrategy() {
        return cacheStrategy;
    }

    public Observable<Object> getLoaderObservable() {
        return loaderObservable;
    }

    public long getSurvivalTime() {
        return survivalTime;
    }
}
