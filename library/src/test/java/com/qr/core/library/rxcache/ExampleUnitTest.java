package com.qr.core.library.rxcache;

import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.qr.core.library.rxcache.cache.TwoLayersCache;
import com.qr.core.library.rxcache.cache.memory.Memory;
import com.qr.core.library.rxcache.cache.persistence.Persistence;
import com.qr.core.library.rxcache.entity.Record;

import org.junit.Test;

import java.io.File;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        RxCache rxCache = new RxCache.Builder()
                .setCacheDirectory(new File("D:\\"))
                .build();

        rxCache.using(Testsss.class)
                .getObservable(Observable.just(1,2,3))
                .subscribe(integer -> {
                    System.out.println(integer);
                });

    }
}