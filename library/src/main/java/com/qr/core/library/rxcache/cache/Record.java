package com.qr.core.library.rxcache.cache;

public final class Record {
    // 数据
    private final Object data;
    // 存活时间
    private long survivalTime;
    // 保存时的时间
    private final long persistedTime;

    public Record(Object data, long survivalTime){
        this.data = data;
        this.survivalTime = survivalTime;
        this.persistedTime = System.currentTimeMillis();

    }

    public Object getData() {
        return data;
    }
    public long getPersistedTime() {
        return persistedTime;
    }
    public long getSurvivalTime() {
        return survivalTime;
    }
}
