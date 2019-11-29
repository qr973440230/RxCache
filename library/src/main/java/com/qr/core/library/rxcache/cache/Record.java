package com.qr.core.library.rxcache.cache;

public final class Record<T> {
    // 数据
    private T data;
    // 存活时间
    private long survivalTime;
    // 保存时的时间
    private long persistedTime;

    public Record() {
    }

    public Record(T data, long survivalTime, long persistedTime) {
        this.data = data;
        this.survivalTime = survivalTime;
        this.persistedTime = persistedTime;
    }

    public long getPersistedTime() {
        return persistedTime;
    }

    public void setPersistedTime(long persistedTime) {
        this.persistedTime = persistedTime;
    }

    public void setSurvivalTime(long survivalTime) {
        this.survivalTime = survivalTime;
    }


    public long getSurvivalTime() {
        return survivalTime;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
