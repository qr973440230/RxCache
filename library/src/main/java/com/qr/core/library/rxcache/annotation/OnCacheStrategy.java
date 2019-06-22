package com.qr.core.library.rxcache.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({OnCacheStrategy.Default, OnCacheStrategy.NetworkPriority, OnCacheStrategy.CachePriority, OnCacheStrategy.OnlyNetwork, OnCacheStrategy.OnlyCache})
public @interface OnCacheStrategy {
    /***
     * 默认策略 先加载缓存，不管是否有缓存数据，仍然加载网络数据并缓存
     * 1.请求缓存数据
     * 1.1 有缓存数据，加载缓存数据,进行操作2
     * 1.2 无缓存数据, 进行操作2
     * 2.请求网络数据
     * 2.1 有网络数据，加载网络数据并缓存,操作结束
     * 2.2 无网络数据, 操作结束
     */
    int Default = 1;

    /***
     * 网络数据优先策略 先加载网络数据，无网络数据时加载缓存数据
     * 1.请求网络数据
     * 1.1 有网络数据，加载网络数据并缓存,操作结束
     * 1.2 无网络数据,进行操作2
     * 2.请求缓存数据
     * 2.1 有缓存数据，加载缓存数据，操作结束
     * 2.2 无缓存数据，操作结束
     */
    int NetworkPriority = 2;

    /***
     * 缓存数据优先策略 先加载缓存数据,无缓存数据时加载网络数据
     * 1.请求缓存数据
     * 1.1 有缓存数据，加载缓存数据,操作结束
     * 1.2 无缓存数据，进行操作2
     * 2.请求网络数据
     * 2.1 有网络数据，加载网络数据并缓存,操作结束
     * 2.2 无网络数据，操作结束
     */
    int CachePriority = 3;

    /***
     * 只加载网络策略 只加载网络数据，但是数据仍然会缓存
     * 1.请求网络数据
     * 1.1 有网络数据，加载网络数据并缓存，操作结束
     * 1.2 无网络数据，操作结束
     */
    int OnlyNetwork = 4;

    /***
     * 只加载缓存策略 只加载缓存数据
     * 1.请求缓存数据
     * 1.1 有缓存数据，加载缓存数据,操作结束
     * 1.2 无缓存数据，操作结束
     */
    int OnlyCache = 5;
}
