package com.liwenwei.myrxjava2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MYRXJAVA2";

    public static List<User> usersResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Flowable.range(1, 10)
                .observeOn(Schedulers.computation())
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return integer * integer;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(MainActivity.TAG, "[Join] : " + Integer.toString(integer));
                    }
                });

        List<User> users = new ArrayList<>();
        users.add(new User(1, "ham", 21, false));
        users.add(new User(2, "lily", 18, false));
        users.add(new User(3, "potter", 38, false));

        List<VIP> vips = new ArrayList<>();
        vips.add(new VIP(2, true));

        Observable<List<User>> obUser = Observable.fromArray(users);

        Observable<List<VIP>> obVIP = Observable.fromArray(vips);

        obUser.join(obVIP,
                new Function<User, Observable<User>>() {
                    @Override
                    public ObservableSource<User> apply(List<User> users) throws Exception {
                        return null;
                    }
                },
                new Function<List<VIP>, ObservableSource<Object>>() {
                    @Override
                    public ObservableSource<Object> apply(List<VIP> vips) throws Exception {
                        return null;
                    }
                },
                new BiFunction<List<User>, List<VIP>, List<User>>() {
                    @Override
                    public List<User> apply(List<User> users, List<VIP> vips) throws Exception {
                        return null;
                    }
                }
        ).subscribe(new Consumer<List<User>>() {
            @Override
            public void accept(List<User> users) throws Exception {
                usersResult = users;
            }
        });

        Log.i(MainActivity.TAG, "end");
    }
}
