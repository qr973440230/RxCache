package com.qr.core.library.rxcache.configuration;

import com.qr.core.library.rxcache.configuration.evict.EvictDynamicGroupKey;
import com.qr.core.library.rxcache.configuration.evict.EvictDynamicKey;
import com.qr.core.library.rxcache.configuration.evict.EvictProvider;

import io.reactivex.Observable;

public class ConfigProvider {
    private final String providerKey;
    private final String dynamicKey;
    private final String dynamicGroupKey;
    private final long survivalTime;
    private final boolean useExpiredData;
    private final boolean requireDetailResponse;
    private final boolean encrypted;
    private final Observable loaderObservable;
    private final EvictProvider evictProvider;

    public ConfigProvider(String providerKey, String dynamicKey, String dynamicGroupKey,
                          long survivalTime, boolean useExpiredData, boolean requireDetailResponse,
                          boolean encrypted, Observable loaderObservable, EvictProvider evictProvider) {
        this.providerKey = providerKey;
        this.dynamicKey = dynamicKey;
        this.dynamicGroupKey = dynamicGroupKey;
        this.survivalTime = survivalTime;
        this.useExpiredData = useExpiredData;
        this.requireDetailResponse = requireDetailResponse;
        this.encrypted = encrypted;
        this.loaderObservable = loaderObservable;
        this.evictProvider = evictProvider;
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

    public long getSurvivalTime() {
        return survivalTime;
    }

    public boolean isUseExpiredData() {
        return useExpiredData;
    }

    public boolean isRequireDetailResponse() {
        return requireDetailResponse;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public Observable getLoaderObservable() {
        return loaderObservable;
    }

    public EvictProvider getEvictProvider() {
        return evictProvider;
    }

    private void checkIntegrity(){
        if(evictProvider instanceof EvictDynamicGroupKey && dynamicGroupKey.isEmpty()){
            String errorMsg = providerKey + "evictDynamicGroup && dynamicGroupKey.isEmpty()";
            throw new IllegalArgumentException(errorMsg);
        }

        if(evictProvider instanceof EvictDynamicKey && dynamicKey.isEmpty()){
            String errorMsg = providerKey + "evictDynamicKey && dynamicKey.isEmpty()";
            throw new IllegalArgumentException(errorMsg);
        }
    }
}
