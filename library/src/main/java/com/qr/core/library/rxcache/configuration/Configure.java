package com.qr.core.library.rxcache.configuration;

import io.reactivex.Observable;

public class Configure<T> {
    private final String providerKey;
    Observable<T> loaderObservable;
    String dynamicKey;
    String dynamicGroupKey;
    boolean evict;
    private final int cacheStrategy;
    private final long survivalTime;

    Configure(String providerKey, int cacheStrategy, long survivalTime) {
        this.providerKey = providerKey;
        this.cacheStrategy = cacheStrategy;
        this.survivalTime = survivalTime;
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

    public boolean isEvict() {
        return evict;
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
