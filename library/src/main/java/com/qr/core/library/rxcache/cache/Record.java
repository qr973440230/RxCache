package com.qr.core.library.rxcache.cache;

public final class Record<T>{
    // 数据
    private final T data;
    // 存活时间
    private long survivalTime;
    // 保存时的时间
    private final long persistedTime;

    public Record(T data, long survivalTime,long persistedTime){
        this.data = data;
        this.survivalTime = survivalTime;
        this.persistedTime = persistedTime;
    }

    public long getPersistedTime() {
        return persistedTime;
    }
    public long getSurvivalTime() {
        return survivalTime;
    }
    public void setSurvivalTime(long survivalTime){
        this.survivalTime = survivalTime;
    }
    public T getData() {
        return data;
    }
}
