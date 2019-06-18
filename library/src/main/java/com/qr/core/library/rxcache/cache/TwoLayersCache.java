package com.qr.core.library.rxcache.cache;

import com.qr.core.library.rxcache.cache.action.impl.EvictRecord;
import com.qr.core.library.rxcache.cache.action.impl.RetrieveRecord;
import com.qr.core.library.rxcache.cache.action.impl.SaveRecord;
import com.qr.core.library.rxcache.entity.Record;

public final class TwoLayersCache {
    private final SaveRecord saveRecord;
    private final RetrieveRecord retrieveRecord;
    private final EvictRecord evictRecord;

    public TwoLayersCache(SaveRecord saveRecord, RetrieveRecord retrieveRecord, EvictRecord evictRecord) {
        this.saveRecord = saveRecord;
        this.retrieveRecord = retrieveRecord;
        this.evictRecord = evictRecord;
    }
    public void save(final String providerKey,final String dynamicKey,final String dynamicGroupKey,
                     final Object data,final long survivalTime,final boolean isEncrypted){
        saveRecord.save(providerKey,dynamicKey,dynamicGroupKey,data,survivalTime,isEncrypted);
    }
    public <T> Record<T> retrieve(final String providerKey, final String dynamicKey, final String dynamicGroupKey,
                                  boolean useExpiredData, boolean isEncrypted){
        return retrieveRecord.retrieveRecord(providerKey,dynamicKey,dynamicGroupKey,useExpiredData,isEncrypted);
    }
    public void evictProviderKey(String providerKey){
        evictRecord.evictRecordsMatchingProviderKey(providerKey);
    }
    public void evictDynamicKey(String providerKey,String dynamicKey){
        evictRecord.evictRecordsMatchingDynamicKey(providerKey,dynamicKey);
    }
    public void evictDynamicGroupKey(String providerKey,String dynamicKey,String dynamicGroupKey){
        evictRecord.evictRecordsMatchingDynamicGroupKey(providerKey,dynamicKey,dynamicGroupKey);
    }
    public void evictAll(){
        evictRecord.evictAll();
    }
}
