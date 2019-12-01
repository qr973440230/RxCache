package com.qr.core.library.rxcache.keys;

public class CacheStrategyKey {
    public static final int CacheControl = 0;
    public static final int NetworkPriority = 1;
    public static final int CachePriority = 2;
    public static final int NetworkOnly = 3;
    public static final int CacheOnly = 4;

    private final int onCacheStrategy;

    public CacheStrategyKey(int onCacheStrategy) {
        this.onCacheStrategy = onCacheStrategy;
    }

    public int getOnCacheStrategy() {
        return onCacheStrategy;
    }
}
