package com.qr.core.library.rxcache;

import android.renderscript.ScriptIntrinsicYuvToRGB;

import com.alibaba.fastjson.JSON;
import com.qr.core.library.rxcache.cache.Record;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
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



    }
}