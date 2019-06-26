package com.qr.core.library.rxcache;

public class DynamicGroupKey {
    private final Object dynamicKey;
    private final Object dynamicGroupKey;

    public DynamicGroupKey(Object dynamicKey, Object dynamicGroupKey) {
        this.dynamicKey = dynamicKey;
        this.dynamicGroupKey = dynamicGroupKey;
    }

    public Object getDynamicKey() {
        return dynamicKey;
    }

    public Object getDynamicGroupKey() {
        return dynamicGroupKey;
    }
}
