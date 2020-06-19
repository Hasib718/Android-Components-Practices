package com.example.notificationdemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;
import androidx.core.app.RemoteInput;
import androidx.core.app.TaskStackBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.example.notificationdemo.NotificationDemo.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    /**
     * In real messaging app we will be getting messages from a database.
     * But for presentation purpose I've created this list of messages.
     * <p>
     * We shouldn't use it in real app tho
     */
    static List<NotificationCompat.MessagingStyle.Message> MESSAGES = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MESSAGES.add(new NotificationCompat.MessagingStyle.Message("Good Morning!", System.currentTimeMillis(), new Person.Builder().setName("Toufiq").build()));
        MESSAGES.add(new NotificationCompat.MessagingStyle.Message("Hello", System.currentTimeMillis(), (Person) null));
        MESSAGES.add(new NotificationCompat.MessagingStyle.Message("Hi!", System.currentTimeMillis(), new Person.Builder().setName("Wadud").build()));
    }

    public static void sendChannel1Notification(Context context) {
        Intent activityIntent = new Intent(context, TestingActivity2.class);

        /**
         * context three lines will not only start activity but also the parent activities on back press, but the applications activity hierarchy must be well defined.
         * Like context ---
         *         <activity android:name=".TestingActivity2"
         *             android:parentActivityName=".TestingActivity"></activity>
         */
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(activityIntent);
        PendingIntent contentIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput remoteInput = new RemoteInput.Builder("key_text_reply")
                .setLabel("Your answer...")
                .build();

        Intent replyIntent;
        PendingIntent replyPendingIntent = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            replyIntent = new Intent(context, DirectReplyReceiver.class);
            replyPendingIntent = PendingIntent.getBroadcast(context, 0, replyIntent, 0);
        } else {
            /**
             * This is only for API level lower then nougat. API level lower than nougat doesn't support
             * direct reply feature, instead it open chat activity on clicking action button from notification
             *
             *
             * Start chat activity instead using (PendingIntent.getActivity)
             * cancel notification with notificationCompat.cancel(id)
             */
        }

        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                R.drawable.ic_reply,
                "Reply",
                replyPendingIntent
        ).addRemoteInput(remoteInput).build();

        NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle(
                new Person.Builder()
                        .setName("Me")
                        .build());
        messagingStyle.setConversationTitle("Group Chat");
        messagingStyle.setGroupConversation(true);

        for (NotificationCompat.MessagingStyle.Message message : MESSAGES) {
            messagingStyle.addMessage(message);
        }

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setStyle(messagingStyle)
                .addAction(replyAction)
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, notification);
    }

    public void sendOnChannel1(View v) {
        sendChannel1Notification(this);
    }
}