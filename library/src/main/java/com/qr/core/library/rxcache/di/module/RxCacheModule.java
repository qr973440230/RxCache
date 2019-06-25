package com.qr.core.library.rxcache.di.module;

import com.qr.core.library.rxcache.cache.memory.Memory;
import com.qr.core.library.rxcache.cache.memory.ReferenceMapMemory;
import com.qr.core.library.rxcache.cache.persistence.DiskPersistence;
import com.qr.core.library.rxcache.cache.persistence.Persistence;
import com.qr.core.library.rxcache.configuration.Configure;
import com.qr.core.library.rxcache.configuration.ConfigureProviders;
import com.qr.core.library.rxcache.configuration.ConfigureProvidersBehaviour;
import com.qr.core.library.rxcache.processor.ProcessorProviders;
import com.qr.core.library.rxcache.processor.ProcessorProvidersBehaviour;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;

@Module
public abstract class RxCacheModule {
    // Method Configure 缓存
    @Singleton
    @Provides
    static Map<Method, Configure> methodConfigureMap(){
        return new HashMap<>();
    }
    // Method Loader 缓存
    @Singleton
    @Provides
    static Map<Method, Observable> methodObservableMap(){
        return new HashMap<>();
    }
    // 内存
    @Binds
    abstract Memory memory(ReferenceMapMemory referenceMapMemory);
    // 磁盘
    @Binds
    abstract Persistence persistence(DiskPersistence diskPersistence);
    // 处理商
    @Binds
    abstract ProcessorProviders processorProviders(ProcessorProvidersBehaviour processorProvidersBehaviour);
    // 配置商
    @Binds
    abstract ConfigureProviders configureProviders(ConfigureProvidersBehaviour configureProvidersBehaviour);
}
