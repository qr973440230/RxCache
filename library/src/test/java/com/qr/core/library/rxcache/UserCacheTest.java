package com.qr.core.library.rxcache;

import com.alibaba.fastjson.JSON;
import com.qr.core.library.rxcache.keys.DynamicKey;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class UserCacheTest {
    Map<String, User> userMap;
    RxCache rxCache;
    UserCache using;
    User user;

    @Before
    public void setUp() throws Exception {
        rxCache = new RxCache.Builder()
                .setCacheDirectory(new File("D://RxCache//"))
                .build();
        using = rxCache.using(UserCache.class);

        user = new User();
        user.password = "122";
        user.username = "23432";
        userMap = new HashMap<>();
        userMap.put("ass", user);
    }

    @Test
    public void testDefault() {
        rxCache.using(UserCache.class)
                .userMapDefault(Observable.just(userMap))
                .subscribe(stringUserMap -> {
                    System.out.println(JSON.toJSONString(stringUserMap));
                }, throwable -> {
                    System.out.println(throwable.getMessage());
                });

        rxCache.using(UserCache.class)
                .userMapDefault(Observable.error(new Throwable("123456")))
                .subscribe(stringUserMap -> {
                    System.out.println(JSON.toJSONString(stringUserMap));
                }, throwable -> {
                    System.out.println(throwable.getMessage());
                });
    }

    @Test
    public void testNetworkPriority() {
        rxCache.using(UserCache.class)
                .userMapNetworkPriority(Observable.just(userMap))
                .subscribe(stringUserMap -> {
                    System.out.println(JSON.toJSONString(stringUserMap));
                }, throwable -> {
                    System.out.println(throwable.getMessage());
                });

        rxCache.using(UserCache.class)
                .userMapNetworkPriority(Observable.error(new Exception("123456")))
                .subscribe(stringUserMap -> {
                    System.out.println(JSON.toJSONString(stringUserMap));
                }, throwable -> {
                    System.out.println(throwable.getMessage());
                });
    }

    @Test
    public void testCachePriority() {
        rxCache.using(UserCache.class)
                .userMapCachePriority(Observable.just(userMap))
                .subscribe(stringUserMap -> {
                    System.out.println(JSON.toJSONString(stringUserMap));
                }, throwable -> {
                    System.out.println(throwable.getMessage());
                });
        rxCache.using(UserCache.class)
                .userMapCachePriority(Observable.error(new Throwable("123456")))
                .subscribe(stringUserMap -> {
                    System.out.println(JSON.toJSONString(stringUserMap));
                }, throwable -> {
                    System.out.println(throwable.getMessage());
                });
    }

    @Test
    public void testOnlyNetwork() {
        rxCache.using(UserCache.class)
                .userMapOnlyNetwork(Observable.just(userMap))
                .subscribe(stringUserMap -> {
                    System.out.println(JSON.toJSONString(stringUserMap));
                }, throwable -> {
                    System.out.println(throwable.getMessage());
                });

        rxCache.using(UserCache.class)
                .userMapOnlyNetwork(Observable.error(new Throwable("123456")))
                .subscribe(stringUserMap -> {
                    System.out.println(JSON.toJSONString(stringUserMap));
                }, throwable -> {
                    System.out.println(throwable.getMessage());
                });
    }

    @Test
    public void testOnlyCache() {
        rxCache.using(UserCache.class)
                .userMapOnlyCache(Observable.just(userMap))
                .subscribe(stringUserMap -> {
                    System.out.println(JSON.toJSONString(stringUserMap));
                }, throwable -> {
                    System.out.println(throwable.getMessage());
                });

        rxCache.using(UserCache.class)
                .userMapOnlyCache(Observable.error(new Throwable("123456")))
                .subscribe(stringUserMap -> {
                    System.out.println(JSON.toJSONString(stringUserMap));
                }, throwable -> {
                    System.out.println(throwable.getMessage());
                });
    }

    @Test
    public void testCacheControl() {
        rxCache.using(UserCache.class)
                .userMapCacheControl(Observable.just(userMap))
                .subscribe(stringUserMap -> {
                    System.out.println(JSON.toJSONString(stringUserMap));
                }, throwable -> {
                    System.out.println(throwable.getMessage());
                });

        rxCache.using(UserCache.class)
                .userMapCacheControl(Observable.error(new Throwable("123456")))
                .subscribe(stringUserMap -> {
                    System.out.println(JSON.toJSONString(stringUserMap));
                }, throwable -> {
                    System.out.println(throwable.getMessage());
                });
    }

    @Test
    public void testDynamicKey() {
        Observable.range(1, 100)
                .flatMap(integer -> {
                    return Observable.range(100, 100);
                })
                .flatMap(integer -> {
                    user.username = integer + "";
                    return using.observable(Observable.just(user), new DynamicKey(user.username));
                })
                .subscribe(user1 -> {
                    System.out.println(user1);
                }, throwable -> {

                });
    }

    @After
    public void tearDown() throws Exception {
    }
}