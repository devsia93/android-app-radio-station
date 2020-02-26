//package ru.qbitmobile.qbitstation.Receiver;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.BitmapDrawable;
//import android.support.v4.media.session.PlaybackStateCompat;
//
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//
//import ru.qbitmobile.qbitstation.Const;
//import ru.qbitmobile.qbitstation.Helper.Toaster;
//import ru.qbitmobile.qbitstation.Player.Player;
//import ru.qbitmobile.qbitstation.R;
//import ru.qbitmobile.qbitstation.Service.NewPlayerService;
//import ru.qbitmobile.qbitstation.Service.PlayerService;
//
//import static java.lang.String.valueOf;
//
//public class ActionReceiver extends BroadcastReceiver {
//    private NotificationCompat.Builder mBuilder;
//    private NotificationManager mNotificationManager;
//    private Notification mNotification;
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//        String action = intent.getAction();
//        Toaster.Toast(context, "ActionReceiver."+intent.getAction());
//        if (action != null) {
//            switch (action) {
//                case Const.ACTION_EXIT:
//                    Toaster.Toast(context, "ActionReceiver.ACTION_EXIT");
//                    Intent serviceIntent = new Intent(context, NewPlayerService.class);
//                    context.stopService(serviceIntent);
//                    Toaster.Toast(context, "ActionReceiver.ACTION_EXIT");
//                    break;
//                case Const.ACTION_PAUSE:
//                    Player.stop();
//                    notificationHelper = new NotificationHelper();
//                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
//                    Toaster.Toast(context, "ActionReceiver.ACTION_PLAY_PAUSE");
//                    break;
//                case Const.ACTION_NEXT:
//                    //TODO next station
//                    Toaster.Toast(context, "ActionReceiver.ACTION_NEXT");
//                    break;
//                case Const.ACTION_PREVIOUS:
//                    //TODO previous station
//                    Toaster.Toast(context, "ActionReceiver.ACTION_PREVIOUS");
//                    break;
//                case Const.ACTION_FAVORITE:
//                    //TODO favorite and unfavorite
//
//                    Toaster.Toast(context, "ActionReceiver.ACTION_FAVORITE");
//                    break;
//            }
//        }
//
//    }
//}
