package com.qr.core.library.rxcache.cache;

public final class Record<T>{
    // 数据
    private final T data;
    // 存活时间
    private long survivalTime;
    // 保存时的时间
    private final long persistedTime;

    public Record(T data, long survivalTime){
        this.data = data;
        this.survivalTime = survivalTime;
        this.persistedTime = System.currentTimeMillis();
    }

    public long getPersistedTime() {
        return persistedTime;
    }
    public long getSurvivalTime() {
        return survivalTime;
    }

    public T getData() {
        return data;
    }
}
