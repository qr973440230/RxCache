package com.qr.core.library.rxcache;

import com.qr.core.library.rxcache.cache.Record;

import io.reactivex.Observable;

public interface Testsss {
    Observable<Integer> getObservable(Observable<Integer> integerObservable);
    Observable<String> getObservableString(Observable<String> observable);
    Observable<Record<Record<Object>>> getObservableRecord(Observable<Record> observable);
}
