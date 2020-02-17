package ru.qbitmobile.qbitstation.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import java.util.ArrayList;

import ru.qbitmobile.qbitstation.BaseObject.Station;
import ru.qbitmobile.qbitstation.Const;
import ru.qbitmobile.qbitstation.Helper.Toaster;
import ru.qbitmobile.qbitstation.Notification.CreateNotification;
import ru.qbitmobile.qbitstation.Player.Player;
import ru.qbitmobile.qbitstation.R;
import ru.qbitmobile.qbitstation.Service.NewPlayerService;

public class BroadcastActioner extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getExtras().getString("RADIO_STATION");

        switch (action) {
            case Const.ACTION_PLAY_PAUSE:
                onPlayPause(context);
                break;
        }
    }

    private void onPlayPause(Context context) {
        int button = 0;
        if (Player.isPlayed()) {
            button = R.drawable.ic_pause_white_24dp;

        } else {
            button = R.drawable.ic_play_arrow_white_24dp;
            Player.stop();
        }
        CreateNotification.createNotification(context, CreateNotification.sStation, CreateNotification.sBitmap, button, CreateNotification.sPos,
                CreateNotification.sSize);
        CreateNotification.notificationManagerCompat.notify(1, CreateNotification.notification);
        Toaster.Toast(context, "onPlayPause");
    }
}
