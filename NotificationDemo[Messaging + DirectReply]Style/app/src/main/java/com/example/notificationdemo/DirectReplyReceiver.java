package com.example.notificationdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.Person;
import androidx.core.app.RemoteInput;

public class DirectReplyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);

        if (remoteInput != null) {
            CharSequence replyText = remoteInput.getCharSequence("key_text_reply");
            NotificationCompat.MessagingStyle.Message answer = new NotificationCompat.MessagingStyle.Message(replyText, System.currentTimeMillis(), (Person) null);
            MainActivity.MESSAGES.add(answer);

            MainActivity.sendChannel1Notification(context);
        }
    }
}
