package com.qr.core.library.rxcache.configuration.evict;

public class EvictProvider {
    private final boolean evict;
    public EvictProvider(boolean evict){
        this.evict = evict;
    }

    public boolean evict() {
        return evict;
    }
}
