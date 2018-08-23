package com.liwenwei.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> list = new ArrayList<>();
        list.add("liwenwei");
        list.add("liwenwei01");
        list.add("liwenwei02");
        list.add("liwenwei03");

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(new RecyclerViewAdapter(list));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
