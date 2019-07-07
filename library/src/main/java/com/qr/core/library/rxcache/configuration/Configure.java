package com.qr.core.library.rxcache.configuration;

import io.reactivex.Observable;

public class Configure <T>{
    private final String providerKey;
    private final String dynamicKey;
    private final String dynamicGroupKey;
    private final int    cacheStrategy;
    private final long    survivalTime;
    private final Observable<T> loaderObservable;

    Configure(String providerKey, String dynamicKey, String dynamicGroupKey, int cacheStrategy, long survivalTime, Observable<T> loaderObservable) {
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

    public Observable<T> getLoaderObservable() {
        return loaderObservable;
    }

    public long getSurvivalTime() {
        return survivalTime;
    }
}
