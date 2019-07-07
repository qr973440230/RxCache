package com.qr.core.library.rxcache;

import com.qr.core.library.rxcache.annotation.LifeCache;
import com.qr.core.library.rxcache.annotation.ProviderKey;
import com.qr.core.library.rxcache.annotation.OnCacheStrategy;

import java.util.Map;

import io.reactivex.Observable;

public interface UserCache {
    @ProviderKey(providerKey = "userMapCacheDefault")
    Observable<Map<String,User>> userMapDefault(Observable<Map<String,User>> observable);
    @ProviderKey(providerKey = "userMapNetworkPriority",onCacheStrategy = OnCacheStrategy.NetworkPriority)
    Observable<Map<String,User>> userMapNetworkPriority(Observable<Map<String,User>> observable);
    @ProviderKey(providerKey = "userMapCachePriority",onCacheStrategy = OnCacheStrategy.CachePriority)
    Observable<Map<String,User>> userMapCachePriority(Observable<Map<String,User>> observable);
    @ProviderKey(providerKey = "userMapOnlyNetwork",onCacheStrategy = OnCacheStrategy.OnlyNetwork)
    Observable<Map<String,User>> userMapOnlyNetwork(Observable<Map<String,User>> observable);
    @ProviderKey(providerKey = "userMapOnlyCache",onCacheStrategy = OnCacheStrategy.OnlyCache)
    Observable<Map<String,User>> userMapOnlyCache(Observable<Map<String,User>> observable);
    @ProviderKey(providerKey = "userMapCacheControl",onCacheStrategy = OnCacheStrategy.CacheControl)
    @LifeCache(survivalTime = 10000)
    Observable<Map<String,User>> userMapCacheControl(Observable<Map<String,User>> observable);
}
