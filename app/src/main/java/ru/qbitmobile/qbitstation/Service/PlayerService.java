package ru.qbitmobile.qbitstation.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.media.MediaSessionManager;
import androidx.media.session.MediaButtonReceiver;

import ru.qbitmobile.qbitstation.Activity.MainActivity;
import ru.qbitmobile.qbitstation.Const;
import ru.qbitmobile.qbitstation.Helper.Toaster;
import ru.qbitmobile.qbitstation.Player.Player;
import ru.qbitmobile.qbitstation.R;
import ru.qbitmobile.qbitstation.Receiver.ActionReceiver;

public class PlayerService extends Service {

    private NotificationManagerCompat notificationManagerCompat;
    private MediaSessionCompat mSession;
    private MediaSessionManager mManager;;
    private MediaControllerCompat mController;
    private Bitmap mBitmap;
    private String mTitleStation;
    private Player player;
    private String mStreamURL;
    private String mImageURL;
    private ActionReceiver receiver;

    private static NotificationCompat.Builder sBuilder;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mTitleStation = (String) intent.getStringExtra(Const.EXTRA_TITLE_STATION);
        mStreamURL = (String) intent.getStringExtra(Const.EXTRA_STREAM_URL);
        mBitmap = intent.getParcelableExtra(Const.EXTRA_BITMAP_STATION);

        createNotification();
        Toaster.Toast(getApplicationContext(), "PlayerService.onStartCommand");

        if (intent.getAction() != null && intent.getAction().equals("STOP_ACTION")){
            stopForeground(true);
        }

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        player = new Player(Player.getCurrentUrlStream());
        player.stop();
        notificationManagerCompat.cancelAll();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        final Intent activityIntent = new Intent(this, MainActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, Const.NOTIFICATION_MEDIA_ID, activityIntent, 0);

        mSession = new MediaSessionCompat(this, Const.TAG_MEDIA_SESSION);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        Toaster.Toast(getApplicationContext(), "PlayerService.onCreate");
    }

    private void createNotification() {

        Intent exitIntent = new Intent(this, ActionReceiver.class);
        exitIntent.setAction(Const.ACTION_EXIT);
        PendingIntent exitPendingIntent = PendingIntent.getBroadcast(this, 0, exitIntent, 0);

        Intent nextIntent = new Intent(this, ActionReceiver.class);
        nextIntent.setAction(Const.ACTION_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this, 0, nextIntent, 0);

        Intent previousIntent = new Intent(this, ActionReceiver.class);
        previousIntent.setAction(Const.ACTION_PREVIOUS);
        PendingIntent previousPendingIntent = PendingIntent.getBroadcast(this, 0, previousIntent, 0);

        Intent favoriteIntent = new Intent(this, ActionReceiver.class);
        favoriteIntent.setAction(Const.ACTION_FAVORITE);
        PendingIntent favoritePendingIntent = PendingIntent.getBroadcast(this, 0, favoriteIntent, 0);

        Intent playIntent = new Intent(this, ActionReceiver.class);
        playIntent.setAction(Const.ACTION_PLAY);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(this, 0, playIntent, 0);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, Const.CHANEL_MEDIA_ID);
                notification.setOngoing(true)
                .setSmallIcon(R.drawable.exo_notification_play)
                .setLargeIcon(mBitmap)
                .setContentTitle(mTitleStation)
                .setShowWhen(false)
                .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(getApplicationContext(), PlaybackStateCompat.ACTION_STOP))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setOnlyAlertOnce(true)
                .addAction(R.drawable.ic_close_white_24dp, "Close", exitPendingIntent)
                .addAction(R.drawable.exo_icon_previous, "Previous", MediaButtonReceiver.buildMediaButtonPendingIntent(getApplicationContext(), PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS))
                .addAction(new NotificationCompat.Action(R.drawable.ic_pause_white_24dp, "Pause", playPendingIntent))
                .addAction(R.drawable.exo_icon_next, "Next", MediaButtonReceiver.buildMediaButtonPendingIntent(getApplicationContext(), PlaybackStateCompat.ACTION_SKIP_TO_NEXT))
                .addAction(R.drawable.ic_favorite_border_white_24dp, "Favorite", null)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1,2,3)
                        .setMediaSession(mSession.getSessionToken())
                        .setShowCancelButton(true)
                        .setCancelButtonIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_STOP)))
                .setPriority(NotificationCompat.PRIORITY_LOW);
        sBuilder = notification;

        notificationManagerCompat.notify(Const.NOTIFICATION_MEDIA_ID, notification.build());
//        startForeground(Const.NOTIFICATION_MEDIA_ID, notification.build());
    }

    public static NotificationCompat.Builder getBuilder(){
        return sBuilder;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
