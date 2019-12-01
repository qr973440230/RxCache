package com.qr.core.library.rxcache.keys;

public class DynamicKey {
    private final Object[] dynamicKeys;

    public DynamicKey(Object... dynamicKeys) {
        this.dynamicKeys = dynamicKeys;
    }

    public Object[] getDynamicKeys() {
        return dynamicKeys;
    }
}
