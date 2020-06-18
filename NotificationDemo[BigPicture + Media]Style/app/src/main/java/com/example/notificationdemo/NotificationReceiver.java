package com.example.notificationdemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.notificationdemo.NotificationDemo.CHANNEL_2_ID;

public class NotificationReceiver extends BroadcastReceiver {

    private MediaSessionCompat mediaSessionCompat;
    private NotificationManagerCompat notificationManagerCompat;


    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("toastMessage");

        mediaSessionCompat = new MediaSessionCompat(context, "tag");
        notificationManagerCompat = NotificationManagerCompat.from(context);

        if (intent.getAction().equals("Play")) {
            Intent playPauseIntent = new Intent(context, NotificationReceiver.class);
            playPauseIntent.setAction("Pause");
            playPauseIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent playPausePendingIntent = PendingIntent.getBroadcast(context, 3, playPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Bitmap artwork = BitmapFactory.decodeResource(context.getResources(), R.drawable.naruto);

            Notification notification = new NotificationCompat.Builder(context, CHANNEL_2_ID)
                    .setSmallIcon(R.drawable.ic_two)
                    .setContentTitle("Kam hoiche")
                    .setContentText("Kam hoiche")
                    .setLargeIcon(artwork)
                    .addAction(R.drawable.ic_thumb_down, "Dislike", null)   // Intent is null as we aren't preparing a reak media player here
                    .addAction(R.drawable.ic_skip_previous, "Previous", null)
                    .addAction(R.drawable.ic_pause, "Pause", playPausePendingIntent)
                    .addAction(R.drawable.ic_skip_next, "Next", null)
                    .addAction(R.drawable.ic_thumb_up, "Like", null)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(1, 2, 3)
                            .setMediaSession(mediaSessionCompat.getSessionToken()))
                    .setSubText("Sub Text")
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .build();

            notificationManagerCompat.notify(2, notification);
        } else {
            Intent playPauseIntent = new Intent(context, NotificationReceiver.class);
            playPauseIntent.setAction("Play");
            playPauseIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent playPausePendingIntent = PendingIntent.getBroadcast(context, 3, playPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Bitmap artwork = BitmapFactory.decodeResource(context.getResources(), R.drawable.naruto);

            Notification notification = new NotificationCompat.Builder(context, CHANNEL_2_ID)
                    .setSmallIcon(R.drawable.ic_two)
                    .setContentTitle("Kam hoiche")
                    .setContentText("Kam hoiche")
                    .setLargeIcon(artwork)
                    .addAction(R.drawable.ic_thumb_down, "Dislike", null)   // Intent is null as we aren't preparing a reak media player here
                    .addAction(R.drawable.ic_skip_previous, "Previous", null)
                    .addAction(R.drawable.ic_play_arrow, "Play", playPausePendingIntent)
                    .addAction(R.drawable.ic_skip_next, "Next", null)
                    .addAction(R.drawable.ic_thumb_up, "Like", null)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(1, 2, 3)
                            .setMediaSession(mediaSessionCompat.getSessionToken()))
                    .setSubText("Sub Text")
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .build();

            notificationManagerCompat.notify(2, notification);
        }
    }
}
