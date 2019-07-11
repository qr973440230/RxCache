package com.qr.core.library.rxcache.configuration;


import com.qr.core.library.rxcache.DynamicGroupKey;
import com.qr.core.library.rxcache.DynamicKey;
import com.qr.core.library.rxcache.annotation.LifeCache;
import com.qr.core.library.rxcache.annotation.ProviderKey;
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
    public <T> Configure<T> process(Method method, Object[] args) {
        Configure<T> cacheConfigure = getCacheConfigure(method, args);
        Configure<T> configure = new Configure<>(cacheConfigure.getProviderKey(),cacheConfigure.getCacheStrategy(),cacheConfigure.getSurvivalTime());
        DynamicGroupKey dynamicGroupKey = getDynamicGroupKey(method, args);
        configure.loaderObservable = getLoaderObservable(method,args);
        configure.dynamicGroupKey = dynamicGroupKey.getDynamicGroupKey().toString();
        configure.dynamicKey = dynamicGroupKey.getDynamicKey().toString();
        return configure;
    }

    private <T> Configure<T> getCacheConfigure(Method method,Object[] args){
        Configure<T> result;
        synchronized (configureMap){
            result = configureMap.get(method);
            if(result == null){
                ProviderKey providerKey = method.getAnnotation(ProviderKey.class);
                LifeCache lifeCache = method.getAnnotation(LifeCache.class);
                String provider;
                int cacheStrategy;
                if(providerKey == null){
                    provider = method.getName();
                    cacheStrategy = OnCacheStrategy.Default;
                }
                else{
                    provider = providerKey.providerKey();
                    cacheStrategy = providerKey.onCacheStrategy();
                }

                long survivalTime;
                if(lifeCache == null){
                    survivalTime = 0;
                }else{
                    survivalTime = lifeCache.timeUnit().toMillis(lifeCache.survivalTime());
                }

                result = new Configure<T>(provider,
                        cacheStrategy,
                        survivalTime);

                configureMap.put(method,result);
            }
        }
        return result;
    }

    private DynamicGroupKey getDynamicGroupKey(Method method,Object[] args){
        DynamicGroupKey dynamicGroupKey = getObjectFromMethodParam(method, args, DynamicGroupKey.class);
        if(dynamicGroupKey != null){
            return dynamicGroupKey;
        }

        DynamicKey dynamicKey = getObjectFromMethodParam(method, args, DynamicKey.class);
        if(dynamicKey != null){
            return new DynamicGroupKey(dynamicKey.getDynamicKey(),"");
        }

        return new DynamicGroupKey("","");
    }

    private <T> Observable<T> getLoaderObservable(Method method,Object[] args){
        Observable<T> observable = getObjectFromMethodParam(method, args, Observable.class);
        if (observable != null) return observable;

        Single<T> single = getObjectFromMethodParam(method, args, Single.class);
        if (single != null) return single.toObservable();

        Maybe<T> maybe = getObjectFromMethodParam(method, args, Maybe.class);
        if (maybe != null) return maybe.toObservable();

        Flowable<T> flowable = getObjectFromMethodParam(method, args, Flowable.class);
        if (flowable != null) return flowable.toObservable();

        throw new IllegalArgumentException(method.getName() + "参数错误!!! 未找到: observable、 single、maybe、flowable");
    }

    private <T> T getObjectFromMethodParam(Method method, Object[] args,Class<T> expectedClass) {
        int countSameObjectsType = 0;
        T expectedObject = null;

        for (Object objectParam : args) {
            if (expectedClass.isAssignableFrom(objectParam.getClass())) {
                expectedObject = (T) objectParam;
                countSameObjectsType++;
            }
        }

        if (countSameObjectsType > 1) {
            String errorMessage =
                    method.getName() + "参数错误!!! <<" + expectedObject.getClass().getSimpleName() + ">> 该参数只能出现一次";
            throw new IllegalArgumentException(errorMessage);
        }

        return expectedObject;
    }
}
