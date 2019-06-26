package com.qr.core.library.rxcache;

public class DynamicKey {
    private final Object dynamicKey;

    public DynamicKey(Object dynamicKey) {
        this.dynamicKey = dynamicKey;
    }

    public Object getDynamicKey() {
        return dynamicKey;
    }
}
