//package ru.qbitmobile.qbitstation.Helper;
//
//import android.app.Notification;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.support.v4.media.session.MediaSessionCompat;
//import android.support.v4.media.session.PlaybackStateCompat;
//
//import androidx.core.app.NotificationCompat;
//import androidx.media.session.MediaButtonReceiver;
//
//import ru.qbitmobile.qbitstation.Const;
//import ru.qbitmobile.qbitstation.R;
//import ru.qbitmobile.qbitstation.Receiver.ActionReceiver;
//import ru.qbitmobile.qbitstation.Service.NewPlayerService;
//import ru.qbitmobile.qbitstation.Service.PlayerService;
//
//public class NotificationHelper {
//    public NotificationCompat.Builder createNotification(Context context, String title, Bitmap bitmap, MediaSessionCompat mediaSessionCompat, boolean isPlay) {
//
//        Intent exitIntent = new Intent(context, ActionReceiver.class);
//        exitIntent.setAction(Const.ACTION_EXIT);
//        PendingIntent exitPendingIntent = PendingIntent.getBroadcast(context, 0, exitIntent, 0);
//
//        Intent pauseIntent = new Intent(context, ActionReceiver.class);
//        exitIntent.setAction(Const.ACTION_PAUSE);
//        PendingIntent pausePendingIntent = PendingIntent.getBroadcast(context, 0, exitIntent, 0);
//
//        Intent nextIntent = new Intent(context, ActionReceiver.class);
//        nextIntent.setAction(Const.ACTION_NEXT);
//        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context, 0, nextIntent, 0);
//
//        Intent previousIntent = new Intent(context, ActionReceiver.class);
//        previousIntent.setAction(Const.ACTION_PREVIOUS);
//        PendingIntent previousPendingIntent = PendingIntent.getBroadcast(context, 0, previousIntent, 0);
//
//        Intent favoriteIntent = new Intent(context, ActionReceiver.class);
//        favoriteIntent.setAction(Const.ACTION_FAVORITE);
//        PendingIntent favoritePendingIntent = PendingIntent.getBroadcast(context, 0, favoriteIntent, 0);
//
//        Intent playIntent = new Intent(context, ActionReceiver.class);
//        playIntent.setAction(Const.ACTION_PLAY);
//        PendingIntent playPendingIntent = PendingIntent.getBroadcast(context, 0, playIntent, 0);
//
//        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, Const.CHANEL_MEDIA_ID);
//        notification.setOngoing(true)
//                .setSmallIcon(R.drawable.exo_notification_play)
//                .setLargeIcon(bitmap)
//                .setContentTitle(title)
//                .setShowWhen(false)
//                .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(context, PlaybackStateCompat.ACTION_STOP))
//                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                .setOnlyAlertOnce(true)
//                .addAction(R.drawable.ic_close_white_24dp, "Close", exitPendingIntent)
//                .addAction(R.drawable.exo_icon_previous, "Previous", MediaButtonReceiver.buildMediaButtonPendingIntent(context, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS));
//        if (isPlay) {
//            notification.addAction(R.drawable.ic_pause_white_24dp, "Pause", pausePendingIntent);
//        } else {
//            notification.addAction(R.drawable.ic_play_arrow_white_24dp, "Play", playPendingIntent);
//        }
//
//        notification
//                .addAction(R.drawable.exo_icon_next, "Next", MediaButtonReceiver.buildMediaButtonPendingIntent(context, PlaybackStateCompat.ACTION_SKIP_TO_NEXT))
//                .addAction(R.drawable.ic_favorite_border_white_24dp, "Favorite", null)
//                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
//                        .setShowActionsInCompactView(1, 2, 3)
//                        .setMediaSession(mediaSessionCompat.getSessionToken())
//                        .setShowCancelButton(true)
//                        .setCancelButtonIntent(exitPendingIntent))
//                .setPriority(NotificationCompat.PRIORITY_LOW);
//        return notification;
//    }
//}
