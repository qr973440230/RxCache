package com.qr.core.library.rxcache.action.impl;

import com.qr.core.library.rxcache.entity.Record;
import com.qr.core.library.rxcache.action.Action;
import com.qr.core.library.rxcache.memory.Memory;
import com.qr.core.library.rxcache.persistence.Persistence;

public class SaveRecord extends Action {
    private final int maxMBPersistenceCache;
    private final String encryptKey;
    public SaveRecord(Memory memory, Persistence persistence, int maxMBPersistenceCache, String encryptKey) {
        super(memory, persistence);
        this.maxMBPersistenceCache = maxMBPersistenceCache;
        this.encryptKey = encryptKey;
    }

    public void save(final String providerKey,final String dynamicKey,final String dynamicGroupKey,
              final Object data,final long survivalTime,final boolean isEncrypted){
        String composeKey = composeKey(providerKey, dynamicKey, dynamicGroupKey);
        Record<Object> record = new Record<Object>(data,survivalTime);
        memory.put(composeKey,record);

        if(persistence.storedMB() >= maxMBPersistenceCache){
            // 超过最大存储上限
        }else{
            persistence.saveRecord(composeKey,record,isEncrypted,encryptKey);
        }
    }
}
