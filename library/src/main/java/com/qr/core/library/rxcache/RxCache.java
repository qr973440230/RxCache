package com.qr.core.library.rxcache;

import com.alibaba.fastjson.parser.ParserConfig;
import com.qr.core.library.rxcache.di.component.DaggerRxCacheComponent;
import com.qr.core.library.rxcache.processor.ProcessorProviders;
import com.qr.core.library.rxcache.proxy.ProxyProviders;

import java.io.File;
import java.lang.reflect.Proxy;

import javax.inject.Inject;

public class RxCache {
    @Inject
    ProcessorProviders processorProviders;

    private RxCache(Builder builder) {
        DaggerRxCacheComponent.builder()
                .cacheDirectory(builder.cacheDirectory)
                .build()
                .inject(this);
    }

    public <T> T using(Class<T> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                new ProxyProviders(processorProviders));
    }

    public static class Builder{
        // 缓存目录
        private File cacheDirectory;

        public Builder setCacheDirectory(File cacheDirectory) {
            this.cacheDirectory = cacheDirectory;
            return this;
        }

        public RxCache build(){
            return new RxCache(this);
        }
    }
}

