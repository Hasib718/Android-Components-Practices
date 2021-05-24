package com.hasib.workmanagerexample;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class WorkManager_Example extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                Constants.CHANNEL_ID,
                Constants.VERBOSE_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
        );
        channel.setDescription(Constants.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION);

        getSystemService(NotificationManager.class).createNotificationChannel(channel);
    }
}
