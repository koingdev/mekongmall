package com.mekongmall.mekongmall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by koingdev on 1/6/18.
 */

public class SplashActivity extends AppCompatActivity {

    private static Context context;
    private final int SPLASH_DELAY = 1000;
    private String urlFromNotification;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_splash);

        // Get notification data when user tap on Notification
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            urlFromNotification = extra.getString("urlFromNotification");
        }

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                // Pass notification data to MainActivity
                if (urlFromNotification != null) {
                    intent.putExtra("urlFromNotification", urlFromNotification);
                }
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);
    }

    @Override
    public void onBackPressed() {
        // To prevent user from back press
    }

}
