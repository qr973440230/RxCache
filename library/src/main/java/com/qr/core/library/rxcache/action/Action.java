package com.qr.core.library.rxcache.action;

import com.qr.core.library.rxcache.memory.Memory;
import com.qr.core.library.rxcache.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public abstract class Action {
    private static final String PREFIX_DYNAMIC_KEY = "$d$d$d$";
    private static final String PREFIX_DYNAMIC_GROUP_KEY = "$g$g$g$";
    protected final Memory memory;
    protected final Persistence persistence;

    protected Action(Memory memory, Persistence persistence) {
        this.memory = memory;
        this.persistence = persistence;
    }

    protected String composeKey(String providerKey,String dynamicKey,String dynamicGroupKey){
        return providerKey
                + PREFIX_DYNAMIC_KEY
                + dynamicKey
                + PREFIX_DYNAMIC_GROUP_KEY
                + dynamicGroupKey;
    }

    protected List<String> getKeysOnMemoryMatchingProviderKey(String providerKey){
        List<String> keysMatchingProviderKey = new ArrayList<>();

        for(String composeKeyMemory : memory.keySet()){
            String providerKeyOnMemory = composeKeyMemory.substring(0, composeKeyMemory.lastIndexOf(PREFIX_DYNAMIC_KEY));
            if(providerKey.equals(providerKeyOnMemory)){
                keysMatchingProviderKey.add(providerKeyOnMemory);
            }
        }

        return keysMatchingProviderKey;
    }

    protected List<String> getKeysOnMemoryMatchingDynamicKey(String providerKey,String dynamicKey){
        List<String> keysMatchingDynamicKey = new ArrayList<>();
        String composeProviderKeyAndDynamicKey = providerKey + PREFIX_DYNAMIC_KEY + dynamicKey;
        for(String composeKeyMemory : memory.keySet()){
            String providerKeyAndDynamicKeyOnMemory = composeKeyMemory.substring(0,composeKeyMemory.lastIndexOf(PREFIX_DYNAMIC_GROUP_KEY));
            if(composeProviderKeyAndDynamicKey.equals(providerKeyAndDynamicKeyOnMemory)){
                keysMatchingDynamicKey.add(providerKeyAndDynamicKeyOnMemory);
            }
        }
        return keysMatchingDynamicKey;
    }

    protected String getKeysOnMemoryMatchingDynamicGroupKey(String providerKey,String dynamicKey,String dynamicGroupKey){
        return composeKey(providerKey,dynamicKey,dynamicGroupKey);
    }
}
