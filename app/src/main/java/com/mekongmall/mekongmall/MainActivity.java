package com.mekongmall.mekongmall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.onesignal.OneSignal;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by koingdev on 1/3/18.
 */

public class MainActivity extends AppCompatActivity {

    private static Context context;
    private String urlFromNotification;
    private final String MAIN_URL = "http://mekong-mall.com/";
    private final String SCREEN_TITLE = "Mekong Mall";

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        // MyNotificationOpenedHandler: This will be called when a notification is tapped on
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MyNotificationOpenedHandler())
                .init();

        setContentView(R.layout.activity_main);
        this.setTitle(SCREEN_TITLE);

        final AVLoadingIndicatorView indicatorView = findViewById(R.id.avi);
        indicatorView.show();

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        /**
         * Hide loading indicator when page is loading
         */
        webView.setWebViewClient(new WebViewClient(){
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                indicatorView.hide();
            }
        });

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            urlFromNotification = extra.getString("urlFromNotification");
        }

        if (urlFromNotification != null) {
            webView.loadUrl(urlFromNotification);
        } else {
            webView.loadUrl(MAIN_URL);
        }
    }
}
