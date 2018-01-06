package com.mekongmall.mekongmall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.onesignal.OneSignal;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by koingdev on 1/3/18.
 */

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private AVLoadingIndicatorView indicatorView;
    private ImageView sadCloud;
    private ImageButton btnRetry;
    private TextView noInternetDetail;
    private TextView noInternet;

    private String url = "http://mekong-mall.com/";
    private boolean clearHistory = false;

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
        sadCloud = findViewById(R.id.sad_cloud);
        btnRetry = findViewById(R.id.retry);
        noInternet = findViewById(R.id.no_internet);
        noInternetDetail = findViewById(R.id.no_internet_detail);

        webView.setWebViewClient(new CustomWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        // Get notification data from SplashActivity
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            url = extra.getString("urlFromNotification");
        }
        webView.loadUrl(url);
    }

    public void retry(View view) {
        webView.setVisibility(View.VISIBLE);
        sadCloud.setVisibility(View.INVISIBLE);
        btnRetry.setVisibility(View.INVISIBLE);
        noInternet.setVisibility(View.INVISIBLE);
        noInternetDetail.setVisibility(View.INVISIBLE);
        webView.loadUrl(url);
        clearHistory = true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            // Show nothing when page error
            webView.loadUrl("");
            webView.setVisibility(View.INVISIBLE);
            sadCloud.setVisibility(View.VISIBLE);
            btnRetry.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.VISIBLE);
            noInternetDetail.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            indicatorView.show();
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            // Hide loading indicator when page is loading
            indicatorView.hide();
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            if (clearHistory) {
                clearHistory = false;
                webView.clearHistory();
                webView.clearCache(true);
            }
            super.onPageFinished(view, url);
        }
    }

}
