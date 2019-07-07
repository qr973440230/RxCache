package com.qr.core.library.rxcache;

import com.qr.core.library.rxcache.annotation.CacheProvider;
import com.qr.core.library.rxcache.annotation.OnCacheStrategy;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public interface UserCache {
    @CacheProvider(providerKey = "userMapCacheDefault")
    Observable<Map<String,User>> userMapDefault(Observable<Map<String,User>> observable);
    @CacheProvider(providerKey = "userMapNetworkPriority",onCacheStrategy = OnCacheStrategy.NetworkPriority)
    Observable<Map<String,User>> userMapNetworkPriority(Observable<Map<String,User>> observable);
    @CacheProvider(providerKey = "userMapCachePriority",onCacheStrategy = OnCacheStrategy.CachePriority)
    Observable<Map<String,User>> userMapCachePriority(Observable<Map<String,User>> observable);
    @CacheProvider(providerKey = "userMapOnlyNetwork",onCacheStrategy = OnCacheStrategy.OnlyNetwork)
    Observable<Map<String,User>> userMapOnlyNetwork(Observable<Map<String,User>> observable);
    @CacheProvider(providerKey = "userMapOnlyCache",onCacheStrategy = OnCacheStrategy.OnlyCache)
    Observable<Map<String,User>> userMapOnlyCache(Observable<Map<String,User>> observable);
    @CacheProvider(providerKey = "userMapCacheControl",onCacheStrategy = OnCacheStrategy.CacheControl,survivalTime = 10000)
    Observable<Map<String,User>> userMapCacheControl(Observable<Map<String,User>> observable);
}
