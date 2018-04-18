package com.example.wenwei.mychart.MPAndroidChart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeXAxisValueFormatter implements IAxisValueFormatter {
    public enum Type {
        DATE,
        WEEK,
        MONTH
    }

    private Type type;

    public TimeXAxisValueFormatter(Type type) {
        this.type = type;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        switch (type) {
            case DATE:
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis((long) value);
                SimpleDateFormat format = new SimpleDateFormat("MM-dd");
                return format.format(calendar.getTime());
            case WEEK:
                return String.format("Week", -(int) value);
            default:
                if (value % 2 == 0) return "";
                return String.format("Year", -(int) value);
        }
    }
}
