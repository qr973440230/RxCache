package com.qr.core.library.rxcache;

import com.qr.core.library.rxcache.annotation.CacheProvider;

import io.reactivex.Observable;

public interface UserCache {
    public Observable<User> userCache(Observable<User> userObservable);
}
