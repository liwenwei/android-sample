package com.example.wenwei.mychart;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.wenwei.mychart.MPAndroidChart.CheckInLineChartFragment;
import com.example.wenwei.mychart.MPAndroidChart.TimeXAxisValueFormatter;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        createChart();
    }

    private void createChart() {
        Fragment fragmentDay = CheckInLineChartFragment.newInstance(
                CheckInLineChartFragment.toCalendar(Calendar.getInstance().getTime()),
                TimeXAxisValueFormatter.Type.DATE);
        Fragment fragmentWeek = CheckInLineChartFragment.newInstance(
                CheckInLineChartFragment.toCalendar(Calendar.getInstance().getTime()),
                TimeXAxisValueFormatter.Type.WEEK);
        Fragment fragmentYear = CheckInLineChartFragment.newInstance(
                CheckInLineChartFragment.toCalendar(Calendar.getInstance().getTime()),
                TimeXAxisValueFormatter.Type.MONTH);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fg_container_day, fragmentDay )
                .commit();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fg_container_week, fragmentWeek )
                .commit();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fg_container_year, fragmentYear )
                .commit();
    }
}
