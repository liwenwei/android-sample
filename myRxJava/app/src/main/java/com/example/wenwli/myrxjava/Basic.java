package com.example.wenwli.myrxjava;

import android.util.Log;

import rx.Subscriber;

public class Basic {

    public static void run() {
        rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext(i);
                }

                subscriber.onCompleted();
            }
        }).subscribe(new rx.Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.i("TAG", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("TAG", "onError");
            }

            @Override
            public void onNext(Integer integer) {
                Log.i("TAG", "Item is " + integer);
            }
        });
    }
}
