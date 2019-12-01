package com.qr.core.library.rxcache.configuration;


import com.qr.core.library.rxcache.keys.CacheStrategyKey;
import com.qr.core.library.rxcache.keys.DynamicKey;
import com.qr.core.library.rxcache.keys.EvictKey;
import com.qr.core.library.rxcache.annotation.LifeCache;
import com.qr.core.library.rxcache.annotation.ProviderKey;

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
        Configure<T> configure = getCacheConfigure(method, args);
        configure.loaderObservable = getLoaderObservable(method, args);
        CacheStrategyKey cacheStrategyKey = getCacheStrategyKey(method, args);
        configure.cacheStrategy = cacheStrategyKey.getOnCacheStrategy();
        DynamicKey dynamicKeys = getDynamicKeys(method, args);
        configure.dynamicKeys = dynamicKeys.getDynamicKeys();
        EvictKey evictKey = getEvictKey(method, args);
        configure.evict = evictKey.isEvict();

        return configure;
    }

    private <T> Configure<T> getCacheConfigure(Method method, Object[] args) {
        Configure<T> result;
        synchronized (configureMap) {
            result = configureMap.get(method);
            if (result == null) {
                ProviderKey providerKey = method.getAnnotation(ProviderKey.class);
                LifeCache lifeCache = method.getAnnotation(LifeCache.class);

                String provider;
                if (providerKey != null) {
                    provider = providerKey.providerKey();
                } else {
                    provider = method.getName();
                }

                long survivalTime;
                if (lifeCache == null) {
                    survivalTime = 0;
                } else {
                    survivalTime = lifeCache.timeUnit().toMillis(lifeCache.survivalTime());
                }

                result = new Configure<T>(provider, survivalTime);

                configureMap.put(method, result);
            }
        }
        return result;
    }

    private CacheStrategyKey getCacheStrategyKey(Method method, Object[] args) {
        CacheStrategyKey cacheStrategyKey = getObjectFromMethodParam(method, args, CacheStrategyKey.class);
        if (cacheStrategyKey == null) {
            return new CacheStrategyKey(CacheStrategyKey.CacheControl);
        }
        return cacheStrategyKey;
    }

    private EvictKey getEvictKey(Method method, Object[] args) {
        EvictKey evictKey = getObjectFromMethodParam(method, args, EvictKey.class);
        if (evictKey == null) {
            return new EvictKey(false);
        }

        return evictKey;
    }

    private DynamicKey getDynamicKeys(Method method, Object[] args) {
        DynamicKey dynamicKey = getObjectFromMethodParam(method, args, DynamicKey.class);
        if (dynamicKey == null) {
            return new DynamicKey();
        }
        return dynamicKey;
    }

    private <T> Observable<T> getLoaderObservable(Method method, Object[] args) {
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

    private <T> T getObjectFromMethodParam(Method method, Object[] args, Class<T> expectedClass) {
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
