package com.example.alarmmanagerdemo;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class AlarmManagerDemo extends Application {

    public static final String CHANNEL_ID = "1";
    public static final String CHANNEL_NAME = "Alarm channel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        NotificationChannel alarmChannel = new NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH
        );

        alarmChannel.setDescription("This channel throwing alarm notification");

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(alarmChannel);
    }
}
