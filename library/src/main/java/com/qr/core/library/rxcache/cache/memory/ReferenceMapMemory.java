package com.qr.core.library.rxcache.cache.memory;

import com.qr.core.library.rxcache.cache.memory.apache.ReferenceMap;
import com.qr.core.library.rxcache.cache.Record;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ReferenceMapMemory implements Memory {
    private final Map<String,Record> recordReferenceMap;

    @Inject
    public ReferenceMapMemory() {
        recordReferenceMap = Collections.synchronizedMap(new ReferenceMap<String,Record>());
    }

    @Override
    public Record get(String key) {
        return recordReferenceMap.get(key);
    }

    @Override
    public void put(String key, Record record) {
        recordReferenceMap.put(key,record);
    }

    @Override
    public Set<String> keySet() {
        return recordReferenceMap.keySet();
    }

    @Override
    public void evict(String key) {
        recordReferenceMap.remove(key);
    }

    @Override
    public void evictAll() {
        Set<String> keys = recordReferenceMap.keySet();
        synchronized (recordReferenceMap){
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()){
                iterator.next();
                iterator.remove();
            }
        }
    }
}
