package com.example.wenwei.mychart.MPAndroidChart;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wenwei.mychart.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class CheckInLineChartFragment extends Fragment {
    private static final String TAG_RESULT = "TAG_RESULT";
    private static final String TAG_TYPE = "TAG_TYPE";
    private static final String TAG_TODAY = "TAG_TODAY";
    private static final long MILLISECOND_OF_A_DAY = 24 * 60 * 60 * 1000;
    private Calendar today;

    public CheckInLineChartFragment() {
    }

    public static CheckInLineChartFragment newInstance(Calendar today, TimeXAxisValueFormatter.Type type) {
        CheckInLineChartFragment fragment = new CheckInLineChartFragment();
        Bundle args = new Bundle();
        args.putSerializable(TAG_TYPE, type);
        args.putSerializable(TAG_TODAY, today);
        fragment.setArguments(args);
        return fragment;
    }

    private List<Entry> createLineChartData(HashMap<Calendar, Integer> counts, TimeXAxisValueFormatter.Type type) {
        int numOfDays, step;
        switch (type) {
            case DATE:
                numOfDays = 7;
                step = 1;
                break;
            case WEEK:
                numOfDays = 35;
                step = 7;
                break;
            default:
                numOfDays = 360;
                step = 30;
                break;
        }
        ArrayList<Entry> list = new ArrayList<>();
        today = (Calendar) getArguments().getSerializable(TAG_TODAY);
        Log.d("CheckInLineChart","Today: " + today.getTime().toString());
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        today.add(Calendar.DATE, -numOfDays + 1);
        today.add(Calendar.MILLISECOND, TimeZone.getDefault().getRawOffset());
        for (int i = 0; i < numOfDays && list.size() < numOfDays; ) {
            int curFinishedCount = 0;
            for (int j = 0; j < step; ++j, ++i) {
                Integer cnt = counts.get(today);
                curFinishedCount += cnt == null ? 0 : cnt;
                today.add(Calendar.DATE, 1);
            }
            if (type == TimeXAxisValueFormatter.Type.DATE) {
                list.add(new Entry(today.getTimeInMillis() - MILLISECOND_OF_A_DAY, curFinishedCount));
            } else {
                list.add(new Entry((-numOfDays + i) / step - 1, curFinishedCount));
            }
        }
        return list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_check_in_line_chart, container, false);
//        LineChart chart =  view.findViewById(R.id.line_chart);
        LineChart chart = (LineChart) inflater.inflate(R.layout.fragment_check_in_line_chart, container, false);

        TimeXAxisValueFormatter.Type type = (TimeXAxisValueFormatter.Type) getArguments().getSerializable(TAG_TYPE);
        HashMap<Calendar, Integer> counts = new HashMap<>();
        if (type == TimeXAxisValueFormatter.Type.DATE) {
            fakeNormalDay(counts);
        } else if (type == TimeXAxisValueFormatter.Type.WEEK) {
            fakeNormalWeek(counts);
        } else {
            fakeNormalYear(counts);
        }


        List<Entry> entries = createLineChartData(counts, type);
        LineDataSet dataSet = new LineDataSet(entries, "");
        int gridGray = ResourcesCompat.getColor(getResources(), R.color.grid_gray, null);
        int gridFill = ResourcesCompat.getColor(getResources(), R.color.grid_line, null);
        int gridLine = ResourcesCompat.getColor(getResources(), R.color.grid_line, null);
        dataSet.setLineWidth(2);
        dataSet.setColor(gridLine);
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSet.setValueTextColor(gridLine);
        dataSet.setCircleColor(Color.WHITE);
        dataSet.setFillColor(gridFill);
        dataSet.setCircleColorHole(gridLine);
        dataSet.setCircleHoleRadius(3);
        dataSet.setCircleRadius(5);
        dataSet.setDrawFilled(true);
        dataSet.setDrawValues(false);
        dataSet.setDrawHighlightIndicators(false); // Remove the highlight cross

        LineData lineData = new LineData(dataSet);
        lineData.setValueFormatter(new IntegerValueFormatter());

        chart.setData(lineData);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.getDescription().setEnabled(false);// Remove description
        chart.getLegend().setEnabled(false); // Disable legend
        chart.setMarker(new MyCalendarMarkerView(getContext(), R.layout.layout_my_calendar_marker_view));

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(gridGray);
        xAxis.setValueFormatter(new TimeXAxisValueFormatter(type));
        xAxis.setLabelCount(entries.size(), true);

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setGranularity(1);
        yAxisLeft.setGridColor(gridGray);
        yAxisLeft.setAxisLineColor(gridGray);
        yAxisLeft.setSpaceTop(50f);
        yAxisLeft.setAxisMinimum(-0.1f); // The minimum value of y-axis
        yAxisLeft.setLabelCount(5, false); // The number of y-axis label

        // Due to the MPAndroidChart don't support to set the title/name of axis, so we use the Description instead
        // Set the title/name of y axis
        Description yAxisDesc = new Description();
        yAxisDesc.setText("Lesson");
        yAxisDesc.setTextAlign(Paint.Align.LEFT);
        yAxisDesc.setTextSize(10);
        float x = chart.getRendererLeftYAxis()
                .getPaintAxisLabels()
                .measureText(chart.getAxisLeft().getLongestLabel());
        float xOffset = chart.getAxisLeft().getXOffset();
        yAxisDesc.setPosition(x / 2, 20);
        chart.setDescription(yAxisDesc);

        chart.invalidate();
        return chart;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Calendar toCalendar(int amount) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, amount);
        cal.add(Calendar.MILLISECOND, TimeZone.getDefault().getRawOffset());
        return cal;
    }

    public void fakeNormalDay(HashMap<Calendar, Integer> items) {
        items.put(toCalendar(0), 5);
        items.put(toCalendar(-1), 3);
        items.put(toCalendar(-2), 6);
        items.put(toCalendar(-3), 7);
        items.put(toCalendar(-4), 30);
        items.put(toCalendar(-5), 2);
        items.put(toCalendar(-6), 0);
        items.put(toCalendar(-7), 0);
    }

    public void fakeNormalDay1(HashMap<Calendar, Integer> items) {
        items.put(toCalendar(0), 3);
        items.put(toCalendar(-1), 0);
        items.put(toCalendar(-2), 3);
        items.put(toCalendar(-3), 0);
        items.put(toCalendar(-4), 3);
        items.put(toCalendar(-5), 0);
        items.put(toCalendar(-6), 3);
        items.put(toCalendar(-7), 0);
    }

    public void fakeNormalWeek(HashMap<Calendar, Integer> items) {
        items.put(toCalendar(0), 3);
        items.put(toCalendar(-1), 0);
        items.put(toCalendar(-2), 0);
        items.put(toCalendar(-3), 2);
        items.put(toCalendar(-4), 0);
        items.put(toCalendar(-5), 0);
        items.put(toCalendar(-6), 0);
        items.put(toCalendar(-7), 0);
    }

    public void fakeNormalYear(HashMap<Calendar, Integer> items) {
        items.put(toCalendar(0), 0);
        items.put(toCalendar(-1), 0);
        items.put(toCalendar(-2), 0);
        items.put(toCalendar(-3), 0);
        items.put(toCalendar(-4), 0);
        items.put(toCalendar(-5), 0);
        items.put(toCalendar(-6), 0);
        items.put(toCalendar(-7), 0);
    }


}
