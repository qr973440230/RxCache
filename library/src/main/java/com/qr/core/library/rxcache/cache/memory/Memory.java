package com.qr.core.library.rxcache.cache.memory;

import com.qr.core.library.rxcache.cache.Record;

import java.util.Set;

public interface Memory {
    // 从内存取出Record
    Record get(String key);
    // 保存Record到内存
    void put(String key,Record record);
    // 获取Key集合
    Set<String> keySet();
    // 驱逐内存指定key数据
    void evict(String key);
    // 驱逐内存所有数据
    void evictAll();
}
