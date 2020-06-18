package com.example.notificationdemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import static com.example.notificationdemo.NotificationDemo.CHANNEL_1_ID;
import static com.example.notificationdemo.NotificationDemo.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private NotificationManagerCompat notificationManagerCompat;
    private AppCompatEditText editTextTitle;
    private AppCompatEditText editTextMessage;

    private MediaSessionCompat mediaSessionCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManagerCompat = NotificationManagerCompat.from(this);

        editTextMessage = findViewById(R.id.edit_text_message);
        editTextTitle = findViewById(R.id.edit_text_title);

        mediaSessionCompat = new MediaSessionCompat(this, "tag");
    }

    public void sendOnChannel1(View v) {
        Intent activityIntent = new Intent(this, TestingActivity2.class);

        /**
         * this three lines will not only start activity but also the parent activities on back press, but the applications activity hierarchy must be well defined.
         * Like this ---
         *         <activity android:name=".TestingActivity2"
         *             android:parentActivityName=".TestingActivity"></activity>
         */
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(activityIntent);
        PendingIntent contentIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        /**
         * If this particular line is added then, tapping on the notification will start the only activity which is called. back button will take to the Main Activity
         */
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.naruto);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle(editTextTitle.getText().toString())
                .setContentText(editTextMessage.getText().toString())
                .setLargeIcon(picture)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(picture)
                        .bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();

        notificationManagerCompat.notify(1, notification);
    }

    public void sendOnChannel2(View v) {
        Intent playPauseIntent = new Intent(this, NotificationReceiver.class);
        playPauseIntent.setAction("Play");
        playPauseIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent playPausePendingIntent = PendingIntent.getBroadcast(this, 3, playPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap artwork = BitmapFactory.decodeResource(getResources(), R.drawable.naruto);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_two)
                .setContentTitle(editTextTitle.getText().toString())
                .setContentText(editTextMessage.getText().toString())
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