package ru.qbitmobile.qbitstation.Notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.session.MediaSession;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.media.MediaBrowserServiceCompat;
import androidx.media.session.MediaButtonReceiver;

import java.io.Serializable;

import ru.qbitmobile.qbitstation.BaseObject.Station;
import ru.qbitmobile.qbitstation.Const;
import ru.qbitmobile.qbitstation.R;
import ru.qbitmobile.qbitstation.Receiver.ActionReceiver;

public class CreateNotification {

    public static Notification notification;
    public static NotificationManagerCompat notificationManagerCompat;
    public static Station sStation;
    public static Bitmap sBitmap;
    public static int sPos;
    public static int sSize;


    public static void createNotification(Context context, Station station, Bitmap bitmap, int buttonPlay, int pos, int size){
        sStation = station;
        sBitmap = bitmap;
        sPos = pos;
        sSize = size;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            notificationManagerCompat = NotificationManagerCompat.from(context);
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat( context, Const.TAG_MEDIA_SESSION);

            PendingIntent pendingIntentPrevious;
            int drw_previous;
            if (pos == 0){
                pendingIntentPrevious = null;
                drw_previous = 0;
            } else {
                Intent intentPrevious = new Intent(context, ActionReceiver.class)
                        .setAction(Const.ACTION_PREVIOUS);
                pendingIntentPrevious = PendingIntent.getBroadcast(context, 0,
                        intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT);
                drw_previous = R.drawable.ic_skip_previous_white_24dp;
            }

            Intent intentPlay = new Intent(context, ActionReceiver.class)
                    .setAction(Const.ACTION_PLAY_PAUSE);
            intentPlay.putExtra("STATION",station);
            intentPlay.putExtra("BITMAP", (Parcelable) bitmap);
            intentPlay.putExtra("POSITION", pos);
            intentPlay.putExtra("SIZE", size);
            PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0,
                    intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);

            PendingIntent pendingIntentNext;
            int drw_next;
            if (pos == size){
                pendingIntentNext = null;
                drw_next = 0;
            } else {
                Intent intentNext = new Intent(context, ActionReceiver.class)
                        .setAction(Const.ACTION_NEXT);
                pendingIntentNext = PendingIntent.getBroadcast(context, 0,
                        intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
                drw_next = R.drawable.ic_skip_next_white_24dp;
            }

            //create notification
            notification = new NotificationCompat.Builder(context, Const.CHANEL_MEDIA_ID)
                    .setOngoing(true)
                    .setSmallIcon(R.drawable.ic_play_arrow_white_24dp)
                    .setContentTitle(station.getName())
                    .setLargeIcon(bitmap)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setOnlyAlertOnce(true)//show notification for only first time
                    .setShowWhen(false)
                    .addAction(drw_previous, "Previous", pendingIntentPrevious)
                    .addAction(buttonPlay, "Play", MediaButtonReceiver.buildMediaButtonPendingIntent(context, PlaybackStateCompat.ACTION_PLAY_PAUSE))
                    .addAction(drw_next, "Next", pendingIntentNext)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0, 1, 2)
                            .setMediaSession(mediaSessionCompat.getSessionToken()))
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .build();

//            notificationManagerCompat.notify(1, notification);

        }
    }
}

