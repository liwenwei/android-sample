package com.example.wenwli.myrxjava;

import android.util.Log;

import com.example.wenwli.myrxjava.models.Community;
import com.example.wenwli.myrxjava.models.House;
import com.example.wenwli.myrxjava.utils.DataSimulator;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class mapping {

    public static void run_map() {
        Observable.just(1, 2, 3, 4, 5)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "[Map] This is " + integer;
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
                        return "[Observable] This is " + integer;
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

    public static void run_flatMap() {

        /*
        * Get the price of house in each of community by map
        *
        Community[] communities = {};
        Observable.from(communities)
                .subscribe(new Action1<Community>() {
                @Override
                public void call(Community community) {
                    for (House house : community.houses) {
                        Log.i(MainActivity.TAG ,"House price : " + house.getPrice());
                    }
                }
            });
        */

        Observable.from(DataSimulator.getCommunities())
                .flatMap(new Func1<Community, Observable<House>>() {
                    @Override
                    public Observable<House> call(Community community) {
                        return Observable.from(community.getHouses());
                    }
                })
                .subscribe(new Action1<House>() {
                    @Override
                    public void call(House house) {
                        Log.i(MainActivity.TAG ,"[FlatMap] House price : " + house.getPrice());
                    }
                });
    }

    public static void run_concatMap() {
        Observable.from(DataSimulator.getCommunities())
                .concatMap(new Func1<Community, Observable<House>>() {
                    @Override
                    public Observable<House> call(Community community) {
                        return Observable.from(community.getHouses());
                    }
                })
                .subscribe(new Action1<House>() {
                    @Override
                    public void call(House house) {
                        Log.i(MainActivity.TAG ,"[ConcatMap] House price : " + house.getPrice());
                    }
                });
    }

    public static void run_flatMapIterable() {
        Observable.from(DataSimulator.getCommunities())
                .flatMapIterable(new Func1<Community, Iterable<House>>() {
                    @Override
                    public Iterable<House> call(Community community) {
                        return community.getHouses();
                    }
                })
                .subscribe(new Action1<House>() {
                    @Override
                    public void call(House house) {
                        Log.i(MainActivity.TAG ,"[FlatMapIterable] House price : " + house.getPrice());
                    }
                });
    }

    public static void run_switchMap() {
        Observable.from(DataSimulator.getCommunities())
                .switchMap(new Func1<Community, Observable<House>>() {
                    @Override
                    public Observable<House> call(Community community) {
                        return Observable.from(community.getHouses());
                    }
                })
                .subscribe(new Action1<House>() {
                    @Override
                    public void call(House house) {
                        Log.i(MainActivity.TAG ,"[SwiMap] House price : " + house.getPrice());
                    }
                });
    }
}
