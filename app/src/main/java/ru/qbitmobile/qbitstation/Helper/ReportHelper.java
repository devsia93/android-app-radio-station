package ru.qbitmobile.qbitstation.Helper;

import com.yandex.metrica.YandexMetrica;

import java.util.List;

import ru.qbitmobile.qbitstation.BaseObject.Radio;
import ru.qbitmobile.qbitstation.BaseObject.Station;

public class ReportHelper {

    private static List<Radio> radioList;

    public static void report(Station station){
        String selectedGenre = "";
        for (Radio r : radioList){
            if (r.getStations().contains(station)){
                selectedGenre = r.getGenre();
                StringBuilder sb = new StringBuilder();
                sb.append("{\"").append(station.getName()).append("\"}");
                YandexMetrica.reportEvent(selectedGenre, sb.toString());
                return;
            }
        }
    }

    public static void setRadioList(List<Radio> radio){
        radioList = radio;
    }
}
