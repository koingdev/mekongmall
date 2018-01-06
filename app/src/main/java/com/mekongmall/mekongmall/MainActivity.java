package com.mekongmall.mekongmall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.onesignal.OneSignal;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by koingdev on 1/3/18.
 */

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private AVLoadingIndicatorView indicatorView;
    private String urlFromNotification;
    private final String MAIN_URL = "http://mekong-mall.com/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // MyNotificationOpenedHandler: This will be called when a notification is tapped on
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MyNotificationOpenedHandler())
                .init();

        setContentView(R.layout.activity_main);

        indicatorView = findViewById(R.id.avi);

        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new CustomWebViewClient(indicatorView));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearCache(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        // Get notification data from SplashActivity
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            urlFromNotification = extra.getString("urlFromNotification");
            webView.loadUrl(urlFromNotification);
        } else {
            webView.loadUrl(MAIN_URL);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
