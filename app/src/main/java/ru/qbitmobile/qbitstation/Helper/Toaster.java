package ru.qbitmobile.qbitstation.Helper;

import android.content.Context;
import android.widget.Toast;

import ru.qbitmobile.qbitstation.BuildConfig;

public class Toaster {
    public static void Toast(Context context, String textMessage){
        if (BuildConfig.DEBUG)
        Toast.makeText(context, textMessage, Toast.LENGTH_SHORT).show();
    }
}
