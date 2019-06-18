package com.qr.core.library.rxcache.di.component;

import com.qr.core.library.rxcache.di.module.RxCacheModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RxCacheModule.class})
public interface RxCacheComponent {

}
