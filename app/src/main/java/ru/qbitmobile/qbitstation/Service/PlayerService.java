package ru.qbitmobile.qbitstation.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.media.session.MediaButtonReceiver;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import ru.qbitmobile.qbitstation.Activity.MainActivity;
import ru.qbitmobile.qbitstation.Const;
import ru.qbitmobile.qbitstation.Helper.Player;
import ru.qbitmobile.qbitstation.R;
import ru.qbitmobile.qbitstation.Receiver.ActionReceiver;

public class PlayerService extends Service {

    private NotificationManagerCompat notificationManagerCompat;
    private MediaSessionCompat mediaSessionCompat;
    private Bitmap mBitmap;
    private String mTitleStation;
    private String mStreamURL;
    private String mImageURL;
    private ActionReceiver receiver;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mTitleStation = (String) intent.getStringExtra(Const.EXTRA_TITLE_STATION);
        mStreamURL = (String) intent.getStringExtra(Const.EXTRA_STREAM_URL);
        mBitmap = intent.getParcelableExtra(Const.EXTRA_BITMAP_STATION);

        receiver = new ActionReceiver();
        IntentFilter iFilter = new IntentFilter("my.awesome.intent.filter");
        registerReceiver(receiver, iFilter);

        createNotification();
        Log.d("checkService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        final Intent activityIntent = new Intent(this, MainActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, Const.NOTIFICATION_MEDIA_ID, activityIntent, 0);

        mediaSessionCompat = new MediaSessionCompat(this, Const.TAG_MEDIA_SESSION);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        Log.d("checkService", "onCreate");

    }

    private void createNotification() {

        Intent exitIntent = new Intent(this, PlayerService.class);
        exitIntent.setAction(Const.ACTION_EXIT);
        PendingIntent exitPendingIntent = PendingIntent.getService(this, 0, exitIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, Const.CHANEL_MEDIA_ID)
                .setOngoing(true)
                .setSmallIcon(R.drawable.exo_notification_play)
                .setLargeIcon(mBitmap)
                .setContentTitle(mTitleStation)
                .setShowWhen(false)
                .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(getApplicationContext(), PlaybackStateCompat.ACTION_STOP))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setOnlyAlertOnce(true)
                .addAction(R.drawable.ic_close_white_24dp, "Close", exitPendingIntent)
                .addAction(R.drawable.exo_icon_previous, "Previous", MediaButtonReceiver.buildMediaButtonPendingIntent(getApplicationContext(), PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS))
                .addAction(new NotificationCompat.Action(R.drawable.ic_pause_white_24dp, "Pause", MediaButtonReceiver.buildMediaButtonPendingIntent(getApplicationContext(), PlaybackStateCompat.ACTION_PLAY_PAUSE)))
                .addAction(R.drawable.exo_icon_next, "Next", MediaButtonReceiver.buildMediaButtonPendingIntent(getApplicationContext(), PlaybackStateCompat.ACTION_SKIP_TO_NEXT))
                .addAction(R.drawable.ic_favorite_border_white_24dp, "Favorite", null)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1,2,3)
                        .setMediaSession(mediaSessionCompat.getSessionToken())
                        .setShowCancelButton(true)
                        .setCancelButtonIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_STOP)))
//                .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
                .setCategory(NotificationCompat.CATEGORY_TRANSPORT)
                .build();

        notificationManagerCompat.notify(Const.NOTIFICATION_MEDIA_ID, notification);
        Log.d("checkService", "Notification created");
//        startForeground(Const.NOTIFICATION_MEDIA_ID, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
