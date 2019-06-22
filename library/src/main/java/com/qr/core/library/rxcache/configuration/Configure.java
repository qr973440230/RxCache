package com.qr.core.library.rxcache.configuration;

import io.reactivex.Observable;

public class Configure {
    private final String providerKey;
    private final String dynamicKey;
    private final String dynamicGroupKey;
    private final int    cacheStrategy;
    private final Observable loader;

    public Configure(String providerKey, String dynamicKey, String dynamicGroupKey, int cacheStrategy, Observable loader) {
        this.providerKey = providerKey;
        this.dynamicKey = dynamicKey;
        this.dynamicGroupKey = dynamicGroupKey;
        this.cacheStrategy = cacheStrategy;
        this.loader = loader;
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
}
