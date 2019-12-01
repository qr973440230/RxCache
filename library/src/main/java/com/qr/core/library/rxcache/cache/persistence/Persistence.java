package com.qr.core.library.rxcache.cache.persistence;

import com.qr.core.library.rxcache.cache.Record;

import java.util.List;

public interface Persistence {
    // 保存对象数据
    <T> void saveRecord(String key, Record<T> record);

    // 获取对象数据
    <T> Record<T> retrieveRecord(String key);

    // 驱逐指定Key数据
    void evict(String key);

    // 驱逐所有数据
    void evictAll();

    // 获取所有Key
    List<String> allKeys();

    // 计算累计缓存大小
    int storedMB();

}
