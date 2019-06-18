package com.qr.core.library.rxcache.cache.persistence;

import com.qr.core.library.rxcache.entity.Record;

import java.util.List;

public interface Persistence {
    // 保存对象数据
    void saveRecord(String key, Record record, boolean isEncrypted, String encryptKey);
    // 驱逐指定Key数据
    void evict(String key);
    // 驱逐所有数据
    void evictAll();
    // 获取所有Key
    List<String> allKeys();
    // 计算累计缓存大小
    int storedMB();
    // 获取对象数据
    <T> Record<T> retrieveRecord(String key,boolean isEncrypted,String encryptKey);
}
