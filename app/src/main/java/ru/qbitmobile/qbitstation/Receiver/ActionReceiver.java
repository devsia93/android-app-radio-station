package ru.qbitmobile.qbitstation.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ru.qbitmobile.qbitstation.Const;
import ru.qbitmobile.qbitstation.Service.PlayerService;

public class ActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action=intent.getStringExtra(Const.ACTION);
        if (action !=null) {
            switch (action) {
                case Const.ACTION_EXIT:
                    Intent serviceIntent = new Intent(context, PlayerService.class);
                    context.stopService(serviceIntent);
                    break;
            }
        }

    }
}
