package com.mekongmall.mekongmall;

import android.content.Context;
import android.content.Intent;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import org.json.JSONObject;

/**
 * Created by koingdev on 1/4/18.
 */

public class MyNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler{

    // This fires when a notification is opened by tapping on it.
    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
        JSONObject data = result.notification.payload.additionalData;
        Context appContext = MainActivity.getContext();
        String urlFromNotification;
        // Retrieve the url from Notification and pass it to MainActivity
        // And if user clicks on the notification
        // It will open the webview of that url in MainActivity
        if (data != null) {
            urlFromNotification = data.optString("urlFromNotification", null);
            if (urlFromNotification != null) {
                Intent intent = new Intent(appContext, MainActivity.class);
                intent.putExtra("urlFromNotification", urlFromNotification);
                // Normally when we launch new activity, its previous activities will be kept in a queue
                // like a stack of activities. So if we want to kill all the previous activities
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                appContext.startActivity(intent);
            }
        }
    }
}
