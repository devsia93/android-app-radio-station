package ru.qbitmobile.qbitstation;

import java.util.ArrayList;
import java.util.List;

import ru.qbitmobile.qbitstation.BaseObject.Station;

public class Const {

    public interface ACTION {
        String MAIN_ACTION = "ru.qbitmobile.qbitstation.action.main";
        String PLAY_ACTION = "ru.qbitmobile.qbitstation.action.play";
        String STARTFOREGROUND_ACTION = "ru.qbitmobile.qbitstation.action.startforeground";
        String STOPFOREGROUND_ACTION = "ru.qbitmobile.qbitstation.action.stopforeground";
    }

    public static float CURRENT_ROTATE_ARROW = 0;

    public static int FOREGROUND_SERVICE = 101;

    public static String YANDEX_API_KEY = "f204189a-9d5c-425a-b19f-4f96ee739ee7";

    public static final String TAG_MEDIA_SESSION = "MEDIA_SESSION";
    public static final String CHANEL_MEDIA_ID = "chanel_media_1";
    public static final int NOTIFICATION_MEDIA_ID = 1;

    /*Extras*/
    public static final String EXTRA_BITMAP_STATION = "EXTRA_BITMAP_STATION";
    public static final String EXTRA_TITLE_STATION = "EXTRA_TITLE_STATION";
    public static final String EXTRA_STREAM_URL = "EXTRA_STREAM_URL";
    public static final String EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL";

    /*Actions*/
    public static final String ACTION_EXIT = "ACTION_EXIT";
    public static final String ACTION_PLAY = "ACTION_PLAY";
    public static final String ACTION_PAUSE = "ACTION_PAUSE";
    public static final String ACTION_NEXT = "ACTION_NEXT";
    public static final String ACTION_PREVIOUS = "ACTION_PREVIOUS";
    public static final String ACTION_FAVORITE = "ACTION_FAVORITE";
    public static final String ACTION_STOP = "ACTION_STOP";
}
