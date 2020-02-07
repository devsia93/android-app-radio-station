package ru.qbitmobile.qbitstation.Receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.media.session.PlaybackStateCompat;

import androidx.core.app.NotificationCompat;

import ru.qbitmobile.qbitstation.Const;
import ru.qbitmobile.qbitstation.Helper.Toaster;
import ru.qbitmobile.qbitstation.Player.Player;
import ru.qbitmobile.qbitstation.R;
import ru.qbitmobile.qbitstation.Service.PlayerService;

import static java.lang.String.valueOf;

public class ActionReceiver extends BroadcastReceiver {
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private Notification mNotification;

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        Toaster.Toast(context, "ActionReceiver."+intent.getAction());
        if (action != null) {
            switch (action) {
                case Const.ACTION_EXIT:
                    Toaster.Toast(context, "ActionReceiver.ACTION_EXIT");
                    Intent serviceIntent = new Intent(context, PlayerService.class);
                    context.stopService(serviceIntent);
                    Toaster.Toast(context, "ActionReceiver.ACTION_EXIT");
                    break;
                case Const.ACTION_PLAY_PAUSE:
                    handlePlayPause(context);
                    Toaster.Toast(context, "ActionReceiver.ACTION_PLAY_PAUSE");
                    break;
                case Const.ACTION_NEXT:
                    //TODO next station
                    Toaster.Toast(context, "ActionReceiver.ACTION_NEXT");
                    break;
                case Const.ACTION_PREVIOUS:
                    //TODO previous station
                    Toaster.Toast(context, "ActionReceiver.ACTION_PREVIOUS");
                    break;
                case Const.ACTION_FAVORITE:
                    //TODO favorite and unfavorite

                    Toaster.Toast(context, "ActionReceiver.ACTION_FAVORITE");
                    break;
            }
        }

    }

    private void handlePlayPause(Context context) {

        int imagePlayPause = Player.isPlayed() ?
                R.drawable.ic_pause_white_24dp : R.drawable.ic_play_arrow_white_24dp;

        if (!Player.isPlayed())
            Player.start(context);
        else Player.stop();

        Intent playIntent = new Intent(context, ActionReceiver.class);
        playIntent.setAction(Const.ACTION_PLAY_PAUSE);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(context, 0, playIntent, 0);

        if (mBuilder == null) {
            mBuilder = PlayerService.getBuilder();
            mNotification = PlayerService.getBuilder().build();
        }
        mNotification.actions[2] = new Notification.Action(imagePlayPause, "Play", playPendingIntent);
        mNotification = PlayerService.getBuilder().build();
        mNotification.notify();
    }
}
