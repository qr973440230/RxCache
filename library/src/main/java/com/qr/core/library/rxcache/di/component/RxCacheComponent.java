package com.qr.core.library.rxcache.di.component;

import com.qr.core.library.rxcache.RxCache;
import com.qr.core.library.rxcache.di.module.RxCacheModule;

import java.io.File;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {RxCacheModule.class})
public interface RxCacheComponent {
    void inject(RxCache rxCache);

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder cacheDirectory(File cacheDirectory);

        RxCacheComponent build();
    }
}
