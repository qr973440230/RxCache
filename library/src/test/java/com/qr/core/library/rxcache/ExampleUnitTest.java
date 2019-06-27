package com.qr.core.library.rxcache;

import android.renderscript.ScriptIntrinsicYuvToRGB;

import com.alibaba.fastjson.JSON;
import com.qr.core.library.rxcache.cache.Record;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Maybe;
import io.reactivex.Observable;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws IOException {
        RxCache rxCache = new RxCache.Builder()
                .setCacheDirectory(new File("D://"))
                .build();

        User user = new User();
        user.password = "345";
        user.username = "567";
//        rxCache.using(UserCache.class)
//                .userCache(Observable.just(user))
//                .subscribe(user1 -> {
//                    System.out.println(user1.password);
//                    System.out.println(user1.username);
//                },throwable -> {
//                    System.out.println(throwable.getMessage());
//                });

        List<User>  users = new ArrayList<>();
        users.add(user);
        users.add(user);
        users.add(user);
        users.add(user);
        rxCache.using(UserCache.class)
                .userListCache(Observable.just(users))
                .subscribe(users1 -> {
                    for (User user1 : users1) {
                        System.out.println(user1);
                    }
                },throwable -> {
                    System.out.println(throwable.getMessage());
                });
    }
}