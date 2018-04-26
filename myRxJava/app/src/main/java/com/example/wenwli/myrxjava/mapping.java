package com.example.wenwli.myrxjava;

import android.util.Log;

import com.example.wenwli.myrxjava.models.Community;
import com.example.wenwli.myrxjava.models.House;
import com.example.wenwli.myrxjava.utils.DataSimulator;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;

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
                        Log.i(MainActivity.TAG, "[FlatMap] House price : " + house.getPrice());
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
                        Log.i(MainActivity.TAG, "[ConcatMap] House price : " + house.getPrice());
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
                        Log.i(MainActivity.TAG, "[FlatMapIterable] House price : " + house.getPrice());
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
                        Log.i(MainActivity.TAG, "[SwiMap] House price : " + house.getPrice());
                    }
                });
    }

    public static void run_scan() {
        Observable.just(1, 2, 3, 4, 5)
                .scan(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return integer + integer2;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i(MainActivity.TAG, "[Scan] Result : " + integer);
                    }
                });
    }

    public static void run_groupBy() {
        List<House> houses = new ArrayList<>();
        houses.add(new House(78000, 100, "中粮·海景壹号", "中粮海景壹号新出大平层！总价4500W起"));
        houses.add(new House(65000, 98, "竹园新村", "满五唯一，黄金地段"));
        houses.add(new House(28000, 45, "中粮·海景壹号", "毗邻汤臣一品"));
        houses.add(new House(38000, 69, "竹园新村", "顶层户型，两室一厅"));
        houses.add(new House(100000, 130, "中粮·海景壹号", "南北通透，豪华五房"));
        Observable<GroupedObservable<String, House>> groupByCommunityNameObservable = Observable.from(houses)
                .groupBy(new Func1<House, String>() {

                    @Override
                    public String call(House house) {
                        return house.getCommunityName();
                    }
                });

        Observable.concat(groupByCommunityNameObservable)
                .subscribe(new Action1<House>() {
                    @Override
                    public void call(House house) {
                        Log.i(MainActivity.TAG,
                                "[GroupBy] " + "小区:" + house.getCommunityName() + "; 房源描述:" + house.getDesc());
                    }
                });
    }
}
