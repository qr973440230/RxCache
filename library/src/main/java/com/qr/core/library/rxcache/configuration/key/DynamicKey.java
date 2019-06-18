package com.qr.core.library.rxcache.configuration.key;

public class DynamicKey {
    private final Object dynamicKey;

    public DynamicKey(Object dynamicKey) {
        this.dynamicKey = dynamicKey;
    }
    public Object getDynamicKey() {
        return dynamicKey;
    }
}
