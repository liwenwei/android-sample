package com.example.wenwli.myrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.wenwli.myrxjava.mapping.OperatorMapTest;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MYRXJAVA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Basic.run();

        OperatorMapTest.run();

    }
}
