package com.liwenwei.bitmapdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import com.liwenwei.bitmapdemo.util.ShareUtils;
import com.liwenwei.bitmapdemo.util.ViewToImageUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btShare(View v) {
        View sharedView = addShareView();
        Bitmap bm = ViewToImageUtil.generateBitmap(this, sharedView);
        ShareUtils.shareImage(this, bm);
    }

    private View addShareView() {
        return View.inflate(this, R.layout.layout_share, null);
    }
}
