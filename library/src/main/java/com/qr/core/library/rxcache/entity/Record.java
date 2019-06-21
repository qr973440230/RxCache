package com.qr.core.library.rxcache.entity;

import com.qr.core.library.rxcache.enums.Source;

public final class Record <T>{
    // 数据源
    private Source source;
    // 数据
    private final T data;
    // 存活时间
    private long survivalTime;
    // 保存时的时间
    private final long persistedTime;

    public Record(T data,long survivalTime){
        this.data = data;
        this.survivalTime = survivalTime;
        this.persistedTime = System.currentTimeMillis();
        this.source = Source.MEMORY;
    }

    public Source getSource() {
        return source;
    }

    public T getData() {
        return data;
    }

    public long getPersistedTime() {
        return persistedTime;
    }

    public long getSurvivalTime() {
        return survivalTime;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public void setSurvivalTime(long survivalTime) {
        this.survivalTime = survivalTime;
    }
}
