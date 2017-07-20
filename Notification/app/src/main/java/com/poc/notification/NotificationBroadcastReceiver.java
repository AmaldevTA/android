package com.poc.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.NotificationCompat;

import static com.poc.notification.MainActivity.KEY_TEXT_REPLY;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    public NotificationBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        CharSequence message = getReplyMessage(intent);
        int messageId = intent.getIntExtra("message_id", 0);
        System.out.println( "Message ID: " + messageId + "\nMessage: " + message);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_backup)
                .setContentText("replay send");
        notificationManager.notify(intent.getIntExtra("notification_id", 0), builder.build());
    }

    private CharSequence getReplyMessage(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(KEY_TEXT_REPLY);
        }
        return null;
    }
}
