package com.qr.core.library.rxcache.action.impl;

import com.qr.core.library.rxcache.action.Action;
import com.qr.core.library.rxcache.memory.Memory;
import com.qr.core.library.rxcache.persistence.Persistence;

import java.util.List;

public class EvictRecord extends Action {
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
