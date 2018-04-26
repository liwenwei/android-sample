package com.example.wenwli.myrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MYRXJAVA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Basic usage
        Basic.run();

        // Mapping
        mapping.run_map();
        mapping.run_flatMap();
        mapping.run_concatMap();
        mapping.run_flatMapIterable();
        mapping.run_switchMap();
    }
}
