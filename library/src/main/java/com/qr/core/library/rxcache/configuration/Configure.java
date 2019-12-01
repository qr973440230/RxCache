package com.qr.core.library.rxcache.configuration;

import io.reactivex.Observable;

public class Configure<T> {
    Observable<T> loaderObservable;
    int cacheStrategy;
    Object[] dynamicKeys;
    boolean evict;

    private String providerKey;
    private long survivalTime;

    Configure(String providerKey, long survivalTime) {
        this.providerKey = providerKey;
        this.survivalTime = survivalTime;
    }

    public String getProviderKey() {
        return providerKey;
    }

    public long getSurvivalTime() {
        return survivalTime;
    }

    public Observable<T> getLoaderObservable() {
        return loaderObservable;
    }

    public int getCacheStrategy() {
        return cacheStrategy;
    }

    public Object[] getDynamicKeys() {
        return dynamicKeys;
    }

    public boolean isEvict() {
        return evict;
    }
}
