package com.liwenwei.bitmapdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.liwenwei.bitmapdemo.util.BitmapUtils;
import com.liwenwei.bitmapdemo.util.ShareUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btShare(View v) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        View sharedView = addShareView(width, height);
        Bitmap bm = BitmapUtils.getBitmapFromViewWithoutDisplay(sharedView, width, height);
        ShareUtils.shareImage(this, bm);
    }

    private View addShareView(int parentWidth, int parentHeight) {
        View view = View.inflate(this, R.layout.layout_share, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(parentWidth, parentHeight);
        view.setLayoutParams(params);

        return view;
    }
}
