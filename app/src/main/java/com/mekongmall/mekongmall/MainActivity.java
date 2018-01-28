package com.mekongmall.mekongmall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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

    Runnable uiThread;
    Runnable backgroundThread;
    WebChromeClient webChromeClient;

    private String url = "http://mekongmalls.com/";

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

        // Get notification data from SplashActivity
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            url = extra.getString("urlFromNotification");
        }

        indicatorView = findViewById(R.id.avi);
        webView = findViewById(R.id.webView);
        sadCloud = findViewById(R.id.sad_cloud);
        btnRetry = findViewById(R.id.retry);
        noInternet = findViewById(R.id.no_internet);
        noInternetDetail = findViewById(R.id.no_internet_detail);

        backgroundThread = new Runnable() {
            @Override
            public void run() {

                webView.setVisibility(View.INVISIBLE);
                webView.setWebViewClient(new CustomWebViewClient());
                webView.getSettings().setJavaScriptEnabled(true);
                webView.clearCache(true);
                webView.clearHistory();
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.getSettings().setUseWideViewPort(true);
                webView.loadUrl(url);

            }
        };

        webChromeClient = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 70) {
                    view.setVisibility(View.VISIBLE);
                    indicatorView.hide();
                }
            }
        };

        uiThread = new Runnable() {
            @Override
            public void run() {
                webView.setWebChromeClient(webChromeClient);
            }
        };

        webView.post(backgroundThread);

        runOnUiThread(uiThread);

    }

    // btnRetry's onClick
    public void retry(View view) {

        indicatorView.setVisibility(View.VISIBLE);
        indicatorView.show();

        sadCloud.setVisibility(View.INVISIBLE);
        btnRetry.setVisibility(View.INVISIBLE);
        noInternet.setVisibility(View.INVISIBLE);
        noInternetDetail.setVisibility(View.INVISIBLE);

        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.setVisibility(View.INVISIBLE);
                webView.loadUrl(url);
            }
        });

        runOnUiThread(uiThread);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class CustomWebViewClient extends WebViewClient {

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            // Show nothing when page error and hide webview
            view.loadUrl("about:blank");
            view.setVisibility(View.INVISIBLE);

            sadCloud.setVisibility(View.VISIBLE);
            btnRetry.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.VISIBLE);
            noInternetDetail.setVisibility(View.VISIBLE);

        }

    }

}
