package com.qr.core.library.rxcache;

public class EvictKey {
    private final boolean evict;

    public EvictKey(boolean evict) {
        this.evict = evict;
    }

    public boolean isEvict() {
        return evict;
    }
}
