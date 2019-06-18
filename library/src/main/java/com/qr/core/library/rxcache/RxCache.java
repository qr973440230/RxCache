package com.qr.core.library.rxcache;

import com.qr.core.library.rxcache.proxy.ProxyProviders;

import java.io.File;
import java.lang.reflect.Proxy;

public class RxCache {
    private final Builder builder;

    private RxCache(Builder builder) {
        this.builder = builder;
    }

    public <T> T using(Class<T> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                new ProxyProviders(builder,clazz));
    }

    public static class Builder{
        // 缓存目录
        private File cacheDirectory;
        private int  maxMBPersistenceCache;

        public Builder setCacheDirectory(File cacheDirectory) {
            this.cacheDirectory = cacheDirectory;
            return this;
        }

        public Builder setMaxMBPersistenceCache(int maxMBPersistenceCache) {
            this.maxMBPersistenceCache = maxMBPersistenceCache;
            return this;
        }

        public RxCache build(){
            return new RxCache(this);
        }
    }
}

