package com.qr.core.library.rxcache.cache.action.impl;

import com.qr.core.library.rxcache.cache.action.Action;
import com.qr.core.library.rxcache.cache.memory.Memory;
import com.qr.core.library.rxcache.cache.persistence.Persistence;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EvictRecord extends Action {

    @Inject
    public EvictRecord(Memory memory, Persistence persistence) {
        super(memory, persistence);
    }

    public void evictRecordsMatchingDynamicKey(String providerKey, Object[] dynamicKeys) {
        List<String> keysOnMemoryMatchingDynamicKey = getKeysOnMemoryMatchingDynamicKey(providerKey, dynamicKeys);
        for (String keyMatchingDynamicKey : keysOnMemoryMatchingDynamicKey) {
            memory.evict(keyMatchingDynamicKey);
            persistence.evict(keyMatchingDynamicKey);
        }
    }

    public void evictAll() {
        memory.evictAll();
        persistence.evictAll();
    }
}
