package com.qr.core.library.rxcache.cache.action.impl;

import com.qr.core.library.rxcache.entity.Record;
import com.qr.core.library.rxcache.cache.action.Action;
import com.qr.core.library.rxcache.enums.Source;
import com.qr.core.library.rxcache.cache.memory.Memory;
import com.qr.core.library.rxcache.cache.persistence.Persistence;

public class RetrieveRecord extends Action {
    private final String encryptKey;
    public RetrieveRecord(Memory memory, Persistence persistence, String encryptKey) {
        super(memory, persistence);
        this.encryptKey = encryptKey;
    }

    public <T> Record<T> retrieveRecord(String providerKey,String dynamicKey,String dynamicGroupKey,
                                        boolean useExpiredData,boolean isEncrypted){
        String composeKey = composeKey(providerKey, dynamicKey, dynamicGroupKey);
        Record<T> record = memory.get(composeKey);
        if(record != null){
            record.setSource(Source.MEMORY);
        }else{
            try {
                record = persistence.retrieveRecord(composeKey,isEncrypted,encryptKey);
                record.setSource(Source.PERSISTENCE);
                memory.put(composeKey,record);
            }catch (Exception ignore){
                return null;
            }
        }

        long survivalTime = record.getSurvivalTime();
        if(survivalTime > 0 &&
                System.currentTimeMillis() - survivalTime > record.getPersistedTime() &&
                !useExpiredData){
            record = null;
        }

        return record;
    }
}
