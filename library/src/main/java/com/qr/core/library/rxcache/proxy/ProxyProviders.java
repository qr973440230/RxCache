package com.qr.core.library.rxcache.proxy;

import com.qr.core.library.rxcache.RxCache;
import com.qr.core.library.rxcache.handle.processor.ProcessorProviders;
import com.qr.core.library.rxcache.handle.processor.ProcessorProvidersBehaviour;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;

public class ProxyProviders implements InvocationHandler {
    private ProcessorProviders processorProviders;
    private ProxyTranslator proxyTranslator;

    public ProxyProviders(RxCache.Builder builder,Class<?> clazz){
        // TODO: 配置处理
        proxyTranslator = new ProxyTranslator();
    }

    @Override
    public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
        return Observable.defer(new Callable<ObservableSource<?>>() {
            @Override
            public ObservableSource<?> call() throws Exception {
                Observable<Object> observable = processorProviders.process(proxyTranslator.processMethod(method, args));
                Class<?> returnType = method.getReturnType();
                if(returnType == Observable.class){
                    return Observable.just(observable);
                }
                if(returnType == Single.class){
                    return Observable.just(Single.fromObservable(observable));
                }
                if(returnType == Maybe.class){
                    return Observable.just(Maybe.fromSingle(Single.fromObservable(observable)));
                }
                if(returnType == Flowable.class){
                    return Observable.just(observable.toFlowable(BackpressureStrategy.MISSING));
                }
                String errorMsg = method.getName() + "无效的返回值类型";
                throw new RuntimeException(errorMsg);
            }
        }).blockingFirst();
    }
}
