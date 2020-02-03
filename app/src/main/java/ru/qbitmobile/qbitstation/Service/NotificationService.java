package ru.qbitmobile.qbitstation.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ru.qbitmobile.qbitstation.Activity.MainActivity;
import ru.qbitmobile.qbitstation.Const;
import ru.qbitmobile.qbitstation.Helper.Player;
import ru.qbitmobile.qbitstation.R;

public class NotificationService extends Service {
    public static Context context;
    Notification status;
    boolean isPause = true;

    private NotificationManagerCompat notificationManagerCompat;

    private MediaSessionCompat mediaSessionCompat;


    private void showNotification(int pos) {
        RemoteViews views = new RemoteViews(getPackageName(),
                R.layout.status_bar);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Const.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Intent playIntent = new Intent(this, NotificationService.class);
        playIntent.setAction(Const.ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, PendingIntent.FLAG_NO_CREATE);

        Intent closeIntent = new Intent(this, NotificationService.class);
        closeIntent.setAction(Const.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0,
                closeIntent, 0);

        views.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);

        views.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);

        if (pos == 0)
        {
            views.setImageViewResource(R.id.status_bar_play,
                    R.drawable.pause_ntf);
        }

//        if(pos == 1) {
//            views.setImageViewResource(R.id.status_bar_play,
//                    R.drawable.pause_ntf);
//            if(MainActivity.control_button!=null)
//            {
//                MainActivity.control_button.setImageResource(R.drawable.ic_play_arrow_black_24dp);
//                MainActivity.playing_animation.setVisibility(View.GONE);
//                MainActivity.loading_animation.setVisibility(View.VISIBLE);
//                MainActivity.control_button.setVisibility(View.GONE);
//                MainActivity.controlIsActivated = true;
//            }
//        }
//        if(pos == 2)
//        {
//            views.setImageViewResource(R.id.status_bar_play,
//                    R.drawable.play_ntf);
//            if(MainActivity.control_button!=null)
//            {
//                MainActivity.control_button.setImageResource(R.drawable.play);
//                MainActivity.playing_animation.setVisibility(View.GONE);
//                MainActivity.loading_animation.setVisibility(View.GONE);
//                MainActivity.control_button.setVisibility(View.VISIBLE);
//                MainActivity.controlIsActivated = false;
//            }
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String NOTIFICATION_CHANNEL_ID = "ru.qbitmobile.qbitstation";
            String channelName = "QbitStation Radio Channel";
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.RED);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            status = notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.drawable.ic_play_circle_filled_white_24dp)
                    .setCustomContentView(views)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle())
                    .setContentIntent(pendingIntent)
                    .setShowWhen(false)
                    .build();
            startForeground(Const.FOREGROUND_SERVICE, status);
        } else {
            status = new Notification.Builder(this).build();
            status.contentView = views;
            status.flags = Notification.FLAG_ONGOING_EVENT;
            status.icon = R.drawable.ic_play_circle_filled_white_24dp;
            status.contentIntent = pendingIntent;
            startForeground(Const.FOREGROUND_SERVICE, status);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Player player = new Player(Player.getCurrentUrlStream());
        context = this;
        if (intent.getAction().equals(Const.ACTION.STARTFOREGROUND_ACTION)) {
            isPause = false;
            showNotification(0);
            player.start(context);
        }
        else if (intent.getAction().equals(Const.ACTION.PLAY_ACTION)) {
            if(!isPause) {
                showNotification(2);
                player.stop();
                isPause = true;
            }
            else
            {
                showNotification(1);
                isPause = false;
                player.start(context);
            }
        }
        else if (intent.getAction().equals(
                Const.ACTION.STOPFOREGROUND_ACTION)) {
//            if(MainActivity.control_button!=null)
//            {
//                MainActivity.control_button.setImageResource(R.drawable.play);
//                MainActivity.playing_animation.setVisibility(View.GONE);
//                MainActivity.loading_animation.setVisibility(View.GONE);
//                MainActivity.control_button.setVisibility(View.VISIBLE);
//                MainActivity.controlIsActivated = false;
//            }
            player.stop();
            stopForeground(true);
            stopSelf();
        }

        return START_STICKY;
    }
}
