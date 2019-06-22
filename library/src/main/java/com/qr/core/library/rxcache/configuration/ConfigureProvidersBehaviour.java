package com.qr.core.library.rxcache.configuration;

import com.qr.core.library.rxcache.annotation.CacheProvider;
import com.qr.core.library.rxcache.annotation.OnCacheStrategy;

import java.lang.reflect.Method;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ConfigureProvidersBehaviour implements ConfigureProviders{
    private final Map<Method,Configure> configureMap;

    @Inject
    public ConfigureProvidersBehaviour(Map<Method, Configure> configureMap) {
        this.configureMap = configureMap;
    }

    @Override
    public Configure process(Method method, Object[] args) {
        Configure result = null;
        synchronized (configureMap){
            result = configureMap.get(method);
            if(result == null){
                CacheProvider annotation = method.getAnnotation(CacheProvider.class);
                if(annotation == null){
                    result = new Configure(method.getName(),"","", OnCacheStrategy.Default, loader);
                }else{
                    result = new Configure(annotation.providerKey(),annotation.dynamicKey(),annotation.dynamicGroupKey(),annotation.onCacheStrategy(), loader);
                }
                configureMap.put(method,result);
            }
        }
        return result;
    }
}
