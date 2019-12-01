package com.qr.core.library.rxcache;

import android.content.Context;
import android.os.Environment;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.util.Log;

import com.qr.core.library.rxcache.keys.DynamicKey;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws InterruptedException {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"RxCache");
        file.mkdir();
        RxCache rxCache = new RxCache.Builder().setCacheDirectory(file).build();
        Observable.range(1,100)
                .observeOn(Schedulers.io())
                .flatMap(integer -> {
                    return rxCache.using(UserCache.class)
                            .observable(Observable.just(new User()),new DynamicKey(integer));
                })
                .subscribe(user -> {
                    Log.d("user",user.toString());
                },throwable -> {
                    Log.d("user",throwable.getMessage());
                });

        Thread.sleep(100000);
    }
}
