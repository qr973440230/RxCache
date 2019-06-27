package com.qr.core.library.rxcache;

import com.qr.core.library.rxcache.annotation.CacheProvider;

import java.util.List;

import io.reactivex.Observable;

public interface UserCache {
    Observable<User> userCache(Observable<User> userObservable);
    Observable<List<User>> userListCache(Observable<List<User>> listObservable);
}
