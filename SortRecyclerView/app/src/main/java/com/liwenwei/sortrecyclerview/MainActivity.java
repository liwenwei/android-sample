package com.liwenwei.sortrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
    }

    int i = 0;

    public void add(View v) {
        // Country china = new Country("China" + i);
        // china.setPined(true);
        // china.setPinedTime(Calendar.getInstance().getTime());
        // adapter.add(china);
        i++;


        adapter.pin(i, Calendar.getInstance().getTime());
        adapter.notifyItemRemoved(i);
        adapter.notifyItemInserted(0);
        adapter.notifyItemMoved(i, 0);
    }
}
