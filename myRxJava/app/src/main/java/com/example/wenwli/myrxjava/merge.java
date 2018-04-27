package com.example.wenwli.myrxjava;

import android.util.Log;

import com.example.wenwli.myrxjava.models.House;
import com.example.wenwli.myrxjava.models.Location;
import com.example.wenwli.myrxjava.utils.DataSimulator;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class merge {

    public static void run() {

        final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
        Observable<String> letterSequence = Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long position) {
                        return letters[position.intValue()];
                    }
                })
                .take(letters.length);

        Observable<Long> numSequence = Observable.interval(500, TimeUnit.MILLISECONDS).take(5);

        // Merge
        Observable.merge(letterSequence, numSequence)
                .subscribe(new Observer<Serializable>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(MainActivity.TAG, "[Merge] Error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Serializable serializable) {
                        Log.i(MainActivity.TAG, "[Merge] Merged Sequence: " + serializable.toString());
                    }
                });


        // StartWith
        letterSequence.startWith("B")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(MainActivity.TAG, "[StartWith] Letter : " + s);
                    }
                });


        // Zip
        Observable.zip(letterSequence, numSequence, new Func2<String, Long, String>() {
            @Override
            public String call(String letter, Long number) {
                return letter + number;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i(MainActivity.TAG, "[Zip] Error: " + e.getMessage());
            }

            @Override
            public void onNext(String letter) {
                Log.i(MainActivity.TAG, "[Zip] Sequence : " + letter);
            }
        });


        // CombineLatest
        final List<String> communityNames = DataSimulator.getCommunityNames();
        final List<Location> locations = DataSimulator.getLocations();

        Observable<String> communityNameSequence = Observable.interval(1, TimeUnit.SECONDS)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long position) {
                        return communityNames.get(position.intValue());
                    }
                });
        Observable<Location> locationSequence = Observable.interval(1, TimeUnit.SECONDS)
                .map(new Func1<Long, Location>() {
                    @Override
                    public Location call(Long position) {
                        return locations.get(position.intValue());
                    }
                });

        Observable.combineLatest(communityNameSequence, locationSequence, new Func2<String, Location, String>() {
            @Override
            public String call(String s, Location location) {
                return "Community Name: " + s + ", Location: " + location.toString();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i(MainActivity.TAG, "[CombineLatest] Error: " + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.i(MainActivity.TAG, "[CombineLatest] : " + s);
            }
        });


        // Join
        final List<House> houses = DataSimulator.getHouses();
        Observable<House> houseSequence = Observable.interval(1, TimeUnit.SECONDS)
                .map(new Func1<Long, House>() {
                    @Override
                    public House call(Long position) {
                        return houses.get(position.intValue());
                    }
                }).take(houses.size());

        Observable<Long> tictoc = Observable.interval(1, TimeUnit.SECONDS);

        houseSequence.join(tictoc,
                new Func1<House, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(House house) {
                        return Observable.timer(2, TimeUnit.SECONDS);
                    }
                },
                new Func1<Long, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(Long aLong) {
                        return Observable.timer(0, TimeUnit.SECONDS);
                    }
                },
                new Func2<House, Long, String>() {
                    @Override
                    public String call(House house, Long position) {
                        return position + "-->" + house.getDesc();
                    }
                }
        ).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(MainActivity.TAG, "[Join] : " + s);
            }
        });

    }

}
