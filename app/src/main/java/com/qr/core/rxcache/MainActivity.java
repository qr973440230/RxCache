package com.qr.core.rxcache;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.alibaba.fastjson.JSON;
import com.qr.core.library.rxcache.keys.CacheStrategyKey;
import com.qr.core.library.rxcache.keys.DynamicKey;
import com.qr.core.library.rxcache.RxCache;
import com.qr.core.library.rxcache.keys.EvictKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.spiner)
    AppCompatSpinner spiner;
    @BindView(R.id.tv_text)
    TextView tvText;

    private UserCache userCache;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        RxCache.Builder builder = new RxCache.Builder();
        builder.setCacheDirectory(getExternalCacheDir());
        RxCache rxCache = builder.build();
        userCache = rxCache.using(UserCache.class);
    }

    @OnClick({R.id.ok, R.id.error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ok:
                getSuccessObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(user -> {
                            tvText.setText(JSON.toJSONString(user));
                        }, throwable -> {
                            tvText.setText(throwable.toString());
                        });
                break;
            case R.id.error:
                getErrorObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(user -> {
                            tvText.setText(JSON.toJSONString(user));
                        }, throwable -> {
                            tvText.setText(throwable.toString());
                        });
                break;
        }
    }

    public Observable<User> getSuccessObservable() {
        User user = new User();
        Observable<User> just = Observable.just(user);
        return userCache.getUserCacheControl(just, new CacheStrategyKey(index), new DynamicKey(user.userName), new EvictKey(false));
    }

    public Observable<User> getErrorObservable() {
        User user = new User();
        Observable<User> just = Observable.error(new Throwable("Error"));
        return userCache.getUserCacheControl(just, new CacheStrategyKey(index), new DynamicKey(user.userName), new EvictKey(false));
    }

}
