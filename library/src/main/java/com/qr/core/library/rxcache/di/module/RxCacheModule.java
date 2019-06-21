package com.qr.core.library.rxcache.di.module;

import com.qr.core.library.rxcache.cache.memory.Memory;
import com.qr.core.library.rxcache.cache.memory.ReferenceMapMemory;
import com.qr.core.library.rxcache.cache.persistence.DiskPersistence;
import com.qr.core.library.rxcache.cache.persistence.Persistence;
import com.qr.core.library.rxcache.processor.ProcessorProviders;
import com.qr.core.library.rxcache.processor.ProcessorProvidersBehaviour;


import dagger.Binds;
import dagger.Module;

@Module
public abstract class RxCacheModule {
    // 内存
    @Binds
    abstract Memory memory(ReferenceMapMemory referenceMapMemory);
    // 磁盘
    @Binds
    abstract Persistence persistence(DiskPersistence diskPersistence);
    // 处理
    @Binds
    abstract ProcessorProviders processorProviders(ProcessorProvidersBehaviour processorProvidersBehaviour);

}
