package ru.qbitmobile.qbitstation.Service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media.MediaBrowserServiceCompat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ru.qbitmobile.qbitstation.BaseObject.Station;
import ru.qbitmobile.qbitstation.Const;
import ru.qbitmobile.qbitstation.Helper.Toaster;
import ru.qbitmobile.qbitstation.Notification.CreateChannelNotification;
import ru.qbitmobile.qbitstation.Notification.CreateNotification;
import ru.qbitmobile.qbitstation.Player.Player;
import ru.qbitmobile.qbitstation.R;
import ru.qbitmobile.qbitstation.Receiver.BroadcastActioner;

public class NewPlayerService extends MediaBrowserServiceCompat {
    private Station mStation;
    private Bitmap mBitmap;
    public static List<Station> mStations;

    ComponentName broadcastActioner;
    MediaSessionCompat mediaSessionCompat;
    @Override
    public void onCreate() {
        super.onCreate();
        broadcastActioner = new ComponentName(getApplicationContext(),BroadcastActioner.class );
        mediaSessionCompat = new MediaSessionCompat(getApplicationContext(), Const.TAG_MEDIA_SESSION, broadcastActioner, null);
        mediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSessionCompat.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                super.onPlay();
                Toaster.Toast(getApplicationContext(), "onPlay");


            }

            @Override
            public void onPause() {
                super.onPause();
                Player.stop();
                CreateNotification.createNotification(getApplicationContext(), CreateNotification.sStation, CreateNotification.sBitmap, R.drawable.ic_play_arrow_white_24dp, CreateNotification.sPos, CreateNotification.sSize);
                Toaster.Toast(getApplicationContext(), "onPause");
            }

            @Override
            public void onSkipToNext() {
                super.onSkipToNext();
            }

            @Override
            public void onSkipToPrevious() {
                super.onSkipToPrevious();
            }
        });
        mediaSessionCompat.setActive(true);

        CreateChannelNotification.create(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mStations = (ArrayList<Station>)intent.getSerializableExtra("STATIONS");
        mStation = (Station) intent.getSerializableExtra("STATION");
        mBitmap = intent.getParcelableExtra("BITMAP");
        int buttonPlay = intent.getIntExtra("BUTTON", R.drawable.ic_play_arrow_white_24dp);
        int pos = intent.getIntExtra("POSITION", 0);
        int size = intent.getIntExtra("SIZE", 0);
        String status = intent.getStringExtra("STATUS");

        CreateNotification.createNotification(getApplicationContext(), mStation, mBitmap, buttonPlay, pos, size);
        CreateNotification.notificationManagerCompat.notify(1, CreateNotification.notification);

        if (status.equals("PLAY"))
        Player.start(getApplicationContext(), mStations, mStation);
        else Player.stop();

//        startForeground(Const.NOTIFICATION_MEDIA_ID, CreateNotification.notification);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(broadcastActioner);
        stopSelf();
        CreateNotification.notificationManagerCompat.cancelAll();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return null;
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {

    }
}
