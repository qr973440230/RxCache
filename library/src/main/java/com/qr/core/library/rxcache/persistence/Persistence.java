package com.qr.core.library.rxcache.persistence;

import com.qr.core.library.rxcache.entity.Record;

import java.util.List;

public interface Persistence {
    // 保存对象数据
    void save(String key,Object object,boolean isEncrypted,String encryptKey);
    void saveRecord(String key, Record record, boolean isEncrypted, String encryptKey);
    // 驱逐指定Key数据
    void evict(String key);
    // 驱逐所有数据
    void evictAll();
    // 获取所有Key
    List<String> allKeys();
    // 计算累计缓存大小
    int storedMB();
    <T> T retrieve(String key,Class<T> clazz,boolean isEncrypted,String encryptKey);
    <T> Record<T> retrieveRecord(String key,boolean isEncrypted,String encryptKey);
}
