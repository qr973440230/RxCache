package com.qr.core.library.rxcache.cache.persistence;

import com.qr.core.library.rxcache.cache.Record;

import java.util.List;

public interface Persistence {
    // 保存对象数据
    void saveRecord(String key, Record record);
    // 驱逐指定Key数据
    void evict(String key);
    // 驱逐所有数据
    void evictAll();
    // 获取所有Key
    List<String> allKeys();
    // 计算累计缓存大小
    int storedMB();
    // 获取对象数据
    Record retrieveRecord(String key);
}
