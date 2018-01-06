package com.mekongmall.mekongmall;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by koingdev on 1/6/18.
 */

public class CustomWebViewClient extends WebViewClient {

    private AVLoadingIndicatorView indicatorView;

    public CustomWebViewClient(AVLoadingIndicatorView indicatorView) {
        this.indicatorView = indicatorView;
    }

    @Override
    public void onPageCommitVisible(WebView view, String url) {
        // Hide loading indicator when page is loading
        indicatorView.hide();
    }

}
