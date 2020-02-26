//package ru.qbitmobile.qbitstation.Service;
//
//import android.annotation.SuppressLint;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.IBinder;
//import android.support.v4.media.session.MediaControllerCompat;
//import android.support.v4.media.session.MediaSessionCompat;
//import android.support.v4.media.session.PlaybackStateCompat;
//
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//import androidx.media.MediaSessionManager;
//import androidx.media.session.MediaButtonReceiver;
//
//import com.google.android.exoplayer2.ExoPlayer;
//
//import ru.qbitmobile.qbitstation.Activity.MainActivity;
//import ru.qbitmobile.qbitstation.Const;
//import ru.qbitmobile.qbitstation.Helper.NotificationHelper;
//import ru.qbitmobile.qbitstation.Helper.Toaster;
//import ru.qbitmobile.qbitstation.Player.Player;
//import ru.qbitmobile.qbitstation.R;
//import ru.qbitmobile.qbitstation.Receiver.ActionReceiver;
//
//public class NewPlayerService extends Service {
//
//    private NotificationManagerCompat notificationManagerCompat;
//    private MediaSessionCompat mSession;
//    private androidx.media.MediaSessionManager mManager;
//    private MediaControllerCompat mController;
//    private Bitmap mBitmap;
//    private String mTitleStation;
//    private Player player;
//    private String mStreamURL;
//    private String mImageURL;
//    private ActionReceiver receiver;
//
//    private static NotificationCompat.Builder sBuilder;
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        mTitleStation = (String) intent.getStringExtra(Const.EXTRA_TITLE_STATION);
//        mStreamURL = (String) intent.getStringExtra(Const.EXTRA_STREAM_URL);
//        mBitmap = intent.getParcelableExtra(Const.EXTRA_BITMAP_STATION);
//
//        createNotification();
//        Toaster.Toast(getApplicationContext(), "PlayerService.onStartCommand");
//
//        if (intent.getAction() != null && intent.getAction().equals("STOP_ACTION")) {
//            stopForeground(true);
//        }
//
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        player = new Player(Player.getCurrentUrlStream());
//        player.stop();
//        notificationManagerCompat.cancelAll();
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        final Intent activityIntent = new Intent(this, MainActivity.class);
//        final PendingIntent pendingIntent = PendingIntent.getActivity(this, Const.NOTIFICATION_MEDIA_ID, activityIntent, 0);
//
//        mSession = new MediaSessionCompat(this, Const.TAG_MEDIA_SESSION);
//        notificationManagerCompat = NotificationManagerCompat.from(this);
//        Toaster.Toast(getApplicationContext(), "PlayerService.onCreate");
//    }
//
//    private void createNotification() {
//
//        NotificationHelper notificationHelper = new NotificationHelper();
//        NotificationCompat.Builder notification = notificationHelper.createNotification(getBaseContext(),
//                mTitleStation, mBitmap, mSession, Player.isPlayed());
//
//        notificationManagerCompat.notify(Const.NOTIFICATION_MEDIA_ID, notification.build());
//
////        startForeground(Const.NOTIFICATION_MEDIA_ID, notification.build());
//    }
//
//    public static NotificationCompat.Builder getBuilder() {
//        return sBuilder;
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        return null;
//    }
//}
