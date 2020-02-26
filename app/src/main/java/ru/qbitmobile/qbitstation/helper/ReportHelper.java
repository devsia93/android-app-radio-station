package ru.qbitmobile.qbitstation.helper;

import com.yandex.metrica.YandexMetrica;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.qbitmobile.qbitstation.baseObject.Radio;
import ru.qbitmobile.qbitstation.baseObject.Station;

public class ReportHelper {

    private static List<Radio> radioList;

    public static void report(Station station){
        for (Radio r : radioList){
            if (r.getStations().contains(station)){
                Map<String, Object> eventParameters = new HashMap<String, Object>();
                eventParameters.put("Название станции", station.getName());
                YandexMetrica.reportEvent(r.getGenre(), eventParameters);
                return;
            }
        }
    }

    public static void setRadioList(List<Radio> radio){
        radioList = radio;
    }
}
