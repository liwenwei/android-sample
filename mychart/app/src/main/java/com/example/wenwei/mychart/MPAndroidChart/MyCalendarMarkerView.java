package com.example.wenwei.mychart.MPAndroidChart;

import android.content.Context;
import android.widget.TextView;

import com.example.wenwei.mychart.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class MyCalendarMarkerView extends MarkerView {
    private TextView txtContent;
    private MPPointF point;

    public MyCalendarMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        txtContent = (TextView) findViewById(R.id.text_content);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        txtContent.setText(Integer.toString((int) e.getY()));
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        if (point == null) {
            point = new MPPointF(-(getWidth() / 2), -getHeight());
        }
        return point;
    }
}
