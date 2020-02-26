package ru.qbitmobile.qbitstation.controller;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import ru.qbitmobile.qbitstation.baseObject.Radio;
import ru.qbitmobile.qbitstation.baseObject.Station;
import ru.qbitmobile.qbitstation.helper.AnimatorHelper;

public class RadioStationController {
    private static List<Radio> listRadios;
    private static List<Station> listStations;
    private static Radio selectedRadio;
    private static Station selectedStation;
    private static int position;
    private static Bitmap imageSelectedStation;
    private static HashMap<Station, Bitmap> bitmapHashMap;

    public static int getPosition(){
        return position;
    }

    public static void setPosition(int position) {
        RadioStationController.position = position;
    }

    public static void setHashMap(Station station, Bitmap bitmap){
        if (bitmapHashMap == null)
            bitmapHashMap = new HashMap<>();
        bitmapHashMap.put(station, bitmap);
    }

    public static Bitmap getBitmapFromhashmap(Station station){
        return bitmapHashMap.get(station);
    }

    public static void setImageSelectedStation(Bitmap imageSelectedStation) {
        RadioStationController.imageSelectedStation = imageSelectedStation;
    }

    public static void setListRadios(List<Radio> listRadios) {
        if (RadioStationController.listRadios == null)
            RadioStationController.listRadios = new ArrayList<>();
        RadioStationController.listRadios = listRadios;
    }

    public static void setListStations(List<Station> listStations) {
        if (RadioStationController.listStations == null)
            RadioStationController.listStations = new ArrayList<>();
        RadioStationController.listStations = listStations;
    }

    public static void setSelectedRadio(Radio selectedRadio) {
        RadioStationController.selectedRadio = selectedRadio;
    }

    public static void setSelectedStation(Station selectedStation) {
        position = selectedRadio.getStations().indexOf(selectedStation);
        RadioStationController.selectedStation = selectedStation;
    }

    public static List<Radio> getListRadios() {
        return listRadios;
    }

    public static List<Station> getListStations() {
        if (RadioStationController.listStations == null)
            RadioStationController.listStations = new ArrayList<>();
        return listStations;
    }

    public static void getPauseStation(){
        AnimatorHelper.stopAnimation(position);
    }

    public static void getPlayStation(){
        AnimatorHelper.startAnimation(position);
    }

    public static Radio getSelectedRadio() {
        return selectedRadio;
    }

    public static Station getSelectedStation() {
        return selectedStation;
    }

    public static Station getNextStation() {
        AnimatorHelper.stopAnimation(position);
        if (position == selectedRadio.getStations().size()-1) {
            position=0;
        } else {
            position++;
        }
        selectedStation = selectedRadio.getStations().get(position);
        AnimatorHelper.startAnimation(position);
        return selectedStation;
    }

    public static Station getPreviousStation() {
        AnimatorHelper.stopAnimation(position);
        if (position != 0) {
            position--;
        } else{
            position = selectedRadio.getStations().size()-1;
        }
        AnimatorHelper.startAnimation(position);
        selectedStation = selectedRadio.getStations().get(position);
        return selectedStation;
    }

    public static Station getRandomStation(){
        Random random = new Random(0);
        position = random.nextInt(selectedRadio.getStations().size());
        selectedStation = selectedRadio.getStations().get(position);
        return selectedStation;
    }
}
