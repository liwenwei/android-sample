package com.example.wenwli.myrxjava;

import android.util.Log;

import com.example.wenwli.myrxjava.models.Community;
import com.example.wenwli.myrxjava.models.House;
import com.example.wenwli.myrxjava.utils.DataSimulator;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class filter {

    public static void run() {
        // Filter
        Observable.from(DataSimulator.getCommunities())
                .filter(new Func1<Community, Boolean>() {
                    @Override
                    public Boolean call(Community community) {
                        return community.getHouses().size() > 2;
                    }
                })
                .subscribe(new Action1<Community>() {
                    @Override
                    public void call(Community community) {
                        Log.i(MainActivity.TAG,
                                "[Filter] The name of community houses greater than 2 is : " + community.getCommunityName());
                    }
                });

        // Take
        Observable.from(DataSimulator.getCommunities())
                .take(2)
                .subscribe(new Action1<Community>() {
                    @Override
                    public void call(Community community) {
                        Log.i(MainActivity.TAG, "[Take] Top 2 : " + community.getCommunityName());
                    }
                });

        // Take Last
        Observable.from(DataSimulator.getCommunities())
                .takeLast(2)
                .subscribe(new Action1<Community>() {
                    @Override
                    public void call(Community community) {
                        Log.i(MainActivity.TAG, "[TakeLast] Last 2 : " + community.getCommunityName());
                    }
                });

        // Take until
        Observable.just(1, 2, 3, 4, 5, 6, 7)
                .takeUntil(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 5;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i(MainActivity.TAG, "[TakeUntil] Take Until : " + integer);
                    }
                });

        // Skip
        Observable.just(1, 2, 3, 4, 5, 6, 7)
                .skip(3)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i(MainActivity.TAG, "[Skip] Skip 3 : " + integer);
                    }
                });

        // Debounce
        // 过滤掉了由Observable发射的速率过快的数据；如果在一个指定的时间间隔过去了仍旧没有发射一个，那么它将发射最后的那个。
        // 通常我们用来结合RxBing(Jake Wharton大神使用RxJava封装的Android UI组件)使用，防止button重复点击。
        Observable.just(1, 2, 3, 4, 5, 6, 7)
                .debounce(30, TimeUnit.MICROSECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i(MainActivity.TAG, "[Debounce] Timeout 30 micro seconds : " + integer);
                    }
                });

        // Last one
        Observable.from(DataSimulator.getHouses())
                .last()
                .subscribe(new Action1<House>() {
                    @Override
                    public void call(House house) {
                        Log.i(MainActivity.TAG, "[Last] Last one - 小区:" + house.getCommunityName() + "; 房源描述:" + house.getDesc());
                    }
                });

        // Last one
        Observable.from(DataSimulator.getHouses())
                .last(new Func1<House, Boolean>() {
                    @Override
                    public Boolean call(House house) {
                        return house.getCommunityName().equals("竹园新村");
                    }
                })
                .subscribe(new Action1<House>() {
                    @Override
                    public void call(House house) {
                        Log.i(MainActivity.TAG, "[Last] Last - 小区:" + house.getCommunityName() + "; 房源描述:" + house.getDesc());
                    }
                });
    }

}
