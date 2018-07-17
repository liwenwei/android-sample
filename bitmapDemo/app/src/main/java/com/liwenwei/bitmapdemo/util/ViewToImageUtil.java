package com.liwenwei.bitmapdemo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;


public class ViewToImageUtil {

    public static Bitmap generateBitmap(Context context, View v) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        if (v.getMeasuredHeight() <= 0) {
            return generateBitmapWithoutDisplay(v, width, height);
        } else {
            return generateBitmapFromRenderedView(v);
        }
    }

    /**
     * The inflated or create manually view not render or attached on windows, so the size is null, and don't layout the view,
     * so we have to manually measure->layout->draw
     *
     * @param v      Inflated view
     * @param width  parent(windows) width
     * @param height parent(windows) height
     * @return
     */
    public static Bitmap generateBitmapWithoutDisplay(View v, int width, int height) {
        // validate view.width and view.height
        v.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

        // validate view.measurewidth and view.measureheight
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        Bitmap bitmap = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.draw(canvas);
        return bitmap;
    }

    public static Bitmap generateBitmapFromRenderedView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return bitmap;
    }

    /**
     * Get layout draw cache
     * <p>
     * The View had rendered, attach on the windows
     *
     * @param view View
     * @return Bitmap
     */
    public static Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }
}
