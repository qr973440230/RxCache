package com.qr.core.library.rxcache.configuration;


import com.qr.core.library.rxcache.annotation.CacheProvider;
import com.qr.core.library.rxcache.annotation.OnCacheStrategy;

import java.lang.reflect.Method;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class ConfigureProvidersBehaviour implements ConfigureProviders {
    private final Map<Method, Configure> configureMap;

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
                    result = new Configure(method.getName(),"","",
                            OnCacheStrategy.Default, getLoaderObservable(method,args));
                }else{
                    result = new Configure(annotation.providerKey(),annotation.dynamicKey(),annotation.dynamicGroupKey(),
                            annotation.onCacheStrategy(), getLoaderObservable(method,args));
                }
                configureMap.put(method,result);
            }
        }
        return result;
    }

    private Observable getLoaderObservable(Method method, Object[] args) {
        if(args.length != 1){
            throw new IllegalArgumentException(method.getName() + ": 参数个数异常");
        }
        Object arg = args[0];
        Class<?> argClass = arg.getClass();
        if(Observable.class.isAssignableFrom(argClass)){
            return (Observable) arg;
        }
        if(Flowable.class.isAssignableFrom(argClass)){
            return ((Flowable)arg).toObservable();
        }
        if(Single.class.isAssignableFrom(argClass)){
            return ((Single)arg).toObservable();
        }
        if(Maybe.class.isAssignableFrom(argClass)){
            return ((Maybe)arg).toObservable();
        }

        throw new IllegalArgumentException(method.getName() + ": 无效的参数，仅仅支持Observable Flowable Single Maybe");
    }
}
