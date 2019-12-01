package com.qr.core.library.rxcache.cache.action.impl;

import com.qr.core.library.rxcache.cache.Record;
import com.qr.core.library.rxcache.cache.action.Action;
import com.qr.core.library.rxcache.enums.Source;
import com.qr.core.library.rxcache.cache.memory.Memory;
import com.qr.core.library.rxcache.cache.persistence.Persistence;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RetrieveRecord extends Action {
    @Inject
    public RetrieveRecord(Memory memory, Persistence persistence) {
        super(memory, persistence);
    }

    public <T> Record<T> retrieveRecord(final String providerKey, final Object[] dynamicKeys) {
        String composeKey = composeKey(providerKey, dynamicKeys);
        Record record = memory.get(composeKey);
        if (record == null) {
            try {
                record = persistence.retrieveRecord(composeKey);
                memory.put(composeKey, record);
            } catch (Exception ignore) {
                return null;
            }
        }

        return record;
    }
}
