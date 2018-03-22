package com.example.wenwei.hybrid;

import android.content.Context;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liwenwei on 3/22/2018.
 */

public class JavaScriptMethods {

    private static final String TAG = "JsMethods";
    private Context mContext;
    private WebView mWebView;

    Handler mHandler = new Handler();

    public JavaScriptMethods(Context context, WebView webView) {
        mContext = context;
        mWebView = webView;
    }

    /**
     * Show the toast message
     *
     * @param msg
     */
    @JavascriptInterface
    public void showMsg(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * js callback sample
     * @param msg
     */
    @JavascriptInterface
    public void getDetail(final String msg) {
        try {
            JSONObject jsonObject = new JSONObject(msg);
            final String method = jsonObject.optString("callback");
            showMsg(msg);

            // callback，mWebView.loadUrl must been called on MainThread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript:" + method + "(" + msg + ")");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
