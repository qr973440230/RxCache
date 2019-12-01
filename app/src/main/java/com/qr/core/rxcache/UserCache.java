package com.qr.core.rxcache;

import com.qr.core.library.rxcache.keys.CacheStrategyKey;
import com.qr.core.library.rxcache.keys.DynamicKey;
import com.qr.core.library.rxcache.annotation.LifeCache;
import com.qr.core.library.rxcache.annotation.ProviderKey;
import com.qr.core.library.rxcache.keys.EvictKey;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public interface UserCache {

    @ProviderKey(providerKey = "user6")
    @LifeCache(survivalTime = 1, timeUnit = TimeUnit.MINUTES)
    Observable<User> getUserCacheControl(Observable<User> observable, CacheStrategyKey cacheStrategyKey, DynamicKey dynamicKey, EvictKey evictKey);
}
