package com.qr.core.library.rxcache.cache.memory;

import com.qr.core.library.rxcache.cache.Record;

import java.util.Set;

public interface Memory {
    // 从内存取出Record
    <T> Record<T> get(String key);
    // 保存Record到内存
    <T> void put(String key,Record<T> record);
    // 获取Key集合
    Set<String> keySet();
    // 驱逐内存指定key数据
    void evict(String key);
    // 驱逐内存所有数据
    void evictAll();
}
