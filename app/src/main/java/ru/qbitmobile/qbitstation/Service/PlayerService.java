package ru.qbitmobile.qbitstation.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.media.session.MediaButtonReceiver;

import ru.qbitmobile.qbitstation.Activity.MainActivity;
import ru.qbitmobile.qbitstation.Const;
import ru.qbitmobile.qbitstation.R;

public class PlayerService extends Service {

    private NotificationManagerCompat notificationManagerCompat;
    private MediaSessionCompat mediaSessionCompat;
    private Bitmap mBitmap;
    private String

    public PlayerService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mBitmap = (Bitmap) intent.getParcelableExtra(Const.EXTRA_BITMAP_STATION);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        final Intent activityIntent = new Intent(this, MainActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        mediaSessionCompat = new MediaSessionCompat(this, Const.TAG_MEDIA_SESSION);
        notificationManagerCompat = NotificationManagerCompat.from(this);

        createNotification();
    }

    private void createNotification() {
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), Const.CHANEL_MEDIA_ID)
                .setSmallIcon(R.drawable.exo_notification_play)
                .setLargeIcon(mBitmap)
                .setContentTitle("chanel 2")
                .setContentText("e-boy")
                .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(getApplicationContext(), PlaybackStateCompat.ACTION_STOP))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(new NotificationCompat.Action(R.drawable.ic_pause_white_24dp, "Pause", MediaButtonReceiver.buildMediaButtonPendingIntent(getApplicationContext(), PlaybackStateCompat.ACTION_PLAY_PAUSE)))
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0)
                        .setMediaSession(mediaSessionCompat.getSessionToken())
                        .setShowCancelButton(true)
                        .setCancelButtonIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(getApplicationContext(), PlaybackStateCompat.ACTION_STOP)))
                .setSubText("QbitStation")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManagerCompat.notify(2, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
