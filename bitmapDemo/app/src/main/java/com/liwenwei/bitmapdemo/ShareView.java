package com.liwenwei.bitmapdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;

public class ShareView {
    //分享页面的View
    private View mView;
    private Activity mActivity;

    public ShareView(Activity activity) {
        // TODO Auto-generated constructor stub
        this.mActivity = activity;
        //inflate XML布局
        this.mView = LayoutInflater.from(activity).inflate(R.layout.layout_share, null);
    }

    /**
     * 生成图片核心代码
     */
    public Bitmap generateBitmap() {
        mView.setDrawingCacheEnabled(true);
        //图片的宽度为屏幕宽度，高度为wrap_content
        mView.measure(
                View.MeasureSpec.makeMeasureSpec(
                        mActivity.getResources().getDisplayMetrics().widthPixels,
                        View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //放置mView
        mView.layout(0, 0, mView.getMeasuredWidth(), mView.getMeasuredHeight());
        mView.buildDrawingCache();
        Bitmap bitmap = mView.getDrawingCache();
        return bitmap;
    }
}
