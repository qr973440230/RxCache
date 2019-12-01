package com.qr.core.library.rxcache.cache.action;

import com.qr.core.library.rxcache.cache.memory.Memory;
import com.qr.core.library.rxcache.cache.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public abstract class Action {
    private static final String PREFIX_KEY = "$d$d";
    protected final Memory memory;
    protected final Persistence persistence;

    protected Action(Memory memory, Persistence persistence) {
        this.memory = memory;
        this.persistence = persistence;
    }

    protected String composeKey(String providerKey, Object[] dynamicKeys) {
        StringBuilder builder = new StringBuilder(providerKey);
        for (Object dynamicKey : dynamicKeys) {
            builder.append(PREFIX_KEY);
            builder.append(dynamicKey);
        }
        return builder.toString();
    }

    protected List<String> getKeysOnMemoryMatchingDynamicKey(String providerKey, Object[] dynamicKeys) {
        List<String> keysMatchingDynamicKey = new ArrayList<>();
        String composeKey = composeKey(providerKey, dynamicKeys);

        for (String composeKeyMemory : memory.keySet()) {
            if (composeKeyMemory.contains(composeKey)) {
                keysMatchingDynamicKey.add(composeKeyMemory);
            }
        }

        return keysMatchingDynamicKey;
    }
}
