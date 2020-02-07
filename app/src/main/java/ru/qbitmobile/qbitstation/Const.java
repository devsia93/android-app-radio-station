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

    public static List<String> ALL_STATIONS = new ArrayList<>();

}
