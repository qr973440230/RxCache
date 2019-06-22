package com.qr.core.library.rxcache;

import org.junit.Test;

import java.io.File;

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