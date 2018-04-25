package com.example.wenwli.myrxjava.mapping;

import android.util.Log;

import com.example.wenwli.myrxjava.MainActivity;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class OperatorMapTest {

    public static void run() {
        Observable.just(1, 2, 3, 4, 5)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "This is " + integer;
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(MainActivity.TAG, s);
                    }
                });

        Observable<Integer> observableA = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        });

        Subscriber<String> mSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(MainActivity.TAG, "onCompleted!");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(MainActivity.TAG, "onError!");
            }

            @Override
            public void onNext(String s) {
                Log.i(MainActivity.TAG, s);
            }
        };

        Observable<String> observableB = observableA
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "This is " + integer;
                    }
                });

        observableB.subscribe(mSubscriber);


        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        }).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return "This is " + integer;
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(MainActivity.TAG, "onCompleted!");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(MainActivity.TAG, "onError!");
            }

            @Override
            public void onNext(String s) {
                Log.i(MainActivity.TAG, s);
            }
        });

    }
}
