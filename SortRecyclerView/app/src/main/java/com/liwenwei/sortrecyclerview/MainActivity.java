package com.liwenwei.sortrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CountryAdapter adapter;

    List<Country> countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_country_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CountryAdapter();
        recyclerView.setAdapter(adapter);

        //now add some UNSORTED countries
        countries = new ArrayList<>();
        countries.add(new Country("Zimbabwe"));
        countries.add(new Country("Uzbekistan"));
        countries.add(new Country("Japan"));
        countries.add(new Country("Argentina"));
        countries.add(new Country("New Zealand"));
        countries.add(new Country("Malaysia"));

        adapter.addAll(countries);
        adapter.setItemListener((courseId, isPined, position) -> {
            Log.d("PinedUser", Integer.toString(position));
            if (isPined) {
                adapter.unpin(position);
                adapter.notifyDataSetChanged();
            } else {
                adapter.pin(position, Calendar.getInstance().getTime());
                adapter.notifyDataSetChanged();
            }
        });
    }
}
