package com.qr.core.library.rxcache.cache.action.impl;

import com.qr.core.library.rxcache.cache.Record;
import com.qr.core.library.rxcache.cache.action.Action;
import com.qr.core.library.rxcache.cache.memory.Memory;
import com.qr.core.library.rxcache.cache.persistence.Persistence;
import com.qr.core.library.rxcache.enums.Source;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SaveRecord extends Action {
    @Inject
    public SaveRecord(Memory memory, Persistence persistence) {
        super(memory, persistence);
    }

    public <T> void save(final String providerKey,final String dynamicKey,final String dynamicGroupKey,
              final T data,final long survivalTime){
        String composeKey = composeKey(providerKey, dynamicKey, dynamicGroupKey);
        Record record = new Record(data,survivalTime,System.currentTimeMillis());
        memory.put(composeKey,record);
        persistence.saveRecord(composeKey,record);
    }
}
