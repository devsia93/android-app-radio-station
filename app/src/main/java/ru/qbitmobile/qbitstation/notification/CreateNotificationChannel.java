package ru.qbitmobile.qbitstation.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import ru.qbitmobile.qbitstation.Const;

public class CreateNotificationChannel {

    public static void create(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel(context);
        }
    }

    private static void createChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(Const.CHANEL_MEDIA_ID,
                    Const.NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
