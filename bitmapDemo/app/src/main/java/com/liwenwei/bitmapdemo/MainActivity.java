package com.liwenwei.bitmapdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.liwenwei.bitmapdemo.util.BitmapUtils;
import com.liwenwei.bitmapdemo.util.ShareUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_scenario_chat_result);
    }

    public void btShare(View v) {
        /*
        *
        ConstraintLayout layout = findViewById(R.id.main);
        layout.setDrawingCacheEnabled(true);
        layout.buildDrawingCache();
        Bitmap bm = layout.getDrawingCache();
        ShareUtils.shareImage(this, bm);
        */

        /*
        Bitmap bm = BitmapUtils.getBitmapFromView(findViewById(R.id.main));
        ShareUtils.shareImage(this, bm);
        */

        /*
        Intent intent = new Intent(this, ShareActivity.class);
        startActivity(intent);
        */

        /*
        Bitmap bm = BitmapUtils.getViewBitmap(findViewById(R.id.main));
        ShareUtils.shareImage(this, bm);
        */

        View sharedView = createShareView();
        int origin_width = sharedView.getWidth();
        int origin_height = sharedView.getHeight();
        Bitmap bm = BitmapUtils.getBitmapFromViewWithoutDisplay(sharedView);
        int width = bm.getWidth();
        int height = bm.getHeight();
        ShareUtils.shareImage(this, bm);
    }

    private View createShareView() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new LinearLayout.LayoutParams(500, 500));
        root.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        TextView titleView = new TextView(this);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        titleView.setLayoutParams(layoutParams);
        titleView.setText("Hallo Welt!");
        root.addView(titleView);

        return root;
    }
}
