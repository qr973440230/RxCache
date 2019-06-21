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

    public void evictRecordsMatchingProviderKey(String providerKey){
        List<String> keysOnMemoryMatchingProviderKey = getKeysOnMemoryMatchingProviderKey(providerKey);
        for (String keyMatchingProviderKey : keysOnMemoryMatchingProviderKey) {
            memory.evict(keyMatchingProviderKey);
            persistence.evict(keyMatchingProviderKey);
        }
    }

    public void evictRecordsMatchingDynamicKey(String providerKey,String dynamicKey){
        List<String> keysOnMemoryMatchingDynamicKey = getKeysOnMemoryMatchingDynamicKey(providerKey, dynamicKey);
        for (String keyMatchingDynamicKey : keysOnMemoryMatchingDynamicKey) {
            memory.evict(keyMatchingDynamicKey);
            persistence.evict(keyMatchingDynamicKey);
        }
    }

    public void evictRecordsMatchingDynamicGroupKey(String providerKey,String dynamicKey,String dynamicGroupKey){
        String keysOnMemoryMatchingDynamicGroupKey = getKeysOnMemoryMatchingDynamicGroupKey(providerKey, dynamicKey, dynamicGroupKey);
        memory.evict(keysOnMemoryMatchingDynamicGroupKey);
        persistence.evict(keysOnMemoryMatchingDynamicGroupKey);
    }

    public void evictAll(){
        memory.evictAll();
        persistence.evictAll();
    }
}
