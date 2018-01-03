package com.mekongmall.mekongmall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by koingdev on 1/3/18.
 */

public class WebViewActivity extends AppCompatActivity {

    private final String URL = "http://mekong-mall.com/";
    private final String SCREEN_TITLE = "Mekong Mall";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
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

        webView.loadUrl(URL);

    }
}
