package com.qr.core.library.rxcache.cache;

import com.qr.core.library.rxcache.cache.action.impl.EvictRecord;
import com.qr.core.library.rxcache.cache.action.impl.RetrieveRecord;
import com.qr.core.library.rxcache.cache.action.impl.SaveRecord;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class TwoLayersCache {
    private final SaveRecord saveRecord;
    private final RetrieveRecord retrieveRecord;
    private final EvictRecord evictRecord;

    @Inject
    public TwoLayersCache(SaveRecord saveRecord, RetrieveRecord retrieveRecord, EvictRecord evictRecord) {
        this.saveRecord = saveRecord;
        this.retrieveRecord = retrieveRecord;
        this.evictRecord = evictRecord;
    }

    public <T> void save(final String providerKey, final Object[] dynamicKeys,
                         final T data, final long survivalTime) {
        saveRecord.save(providerKey, dynamicKeys, data, survivalTime);
    }

    public <T> Record<T> retrieve(final String providerKey, final Object[] dynamicKeys) {
        return retrieveRecord.retrieveRecord(providerKey, dynamicKeys);
    }

    public void evictDynamicGroupKey(String providerKey, final Object[] dynamicKeys) {
        evictRecord.evictRecordsMatchingDynamicKey(providerKey, dynamicKeys);
    }

    public void evictAll() {
        evictRecord.evictAll();
    }
}
