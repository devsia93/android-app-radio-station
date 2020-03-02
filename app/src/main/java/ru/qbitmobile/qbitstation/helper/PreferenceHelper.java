package ru.qbitmobile.qbitstation.helper;

import android.content.Context;

import java.util.ArrayList;

import ru.qbitmobile.qbitstation.Const;
import ru.qbitmobile.qbitstation.baseObject.Radio;
import ru.qbitmobile.qbitstation.baseObject.Station;

public class PreferenceHelper {
    private TinyDB tinyDB;
    private ArrayList<Station> mStations;
    private Radio mRadio;

    public PreferenceHelper(Context context) {
        tinyDB = new TinyDB(context);
        configureRadio();
    }

    public void addFavoriteStation(Station station) {
        mStations = getFavoriteStationsList();
        boolean isExists = false;

        for (Station s : mStations) {
            isExists = s.getName().equals(station.getName());
            if (isExists)
                break;
        }

        if (!isExists) {
            mStations.add(station);
        }

        ArrayList<Object> objects = convertStationListToObjectList();

        tinyDB.putListObject(Const.Preference.PK_STATIONS, objects);
        configureRadio();
    }

    public int deleteFavoriteStation(Station station) {
        mStations = getFavoriteStationsList();
        int position = -1;
        
        if (mStations.size() > 0) {
            Station removableStation = null;
            for (int i = 0; i < mStations.size()-1; i++) {
                if (mStations.get(i).getName().equals(station.getName())) {
                    removableStation = mStations.get(i);
                    position = i;
                    break;
                }
            }
            if (removableStation!= null)
                mStations.remove(removableStation);
        }

        ArrayList<Object> objects = convertStationListToObjectList();

        tinyDB.putListObject(Const.Preference.PK_STATIONS, objects);

        configureRadio();
        
        return position;
    }

    public ArrayList<Station> getFavoriteListStations() {
        ArrayList<Object> objects = tinyDB.getListObject(Const.Preference.PK_STATIONS, Station.class);

        return convertObjectListToStationList(objects);
    }

    private ArrayList<Object> convertStationListToObjectList() {
        ArrayList<Object> objects = new ArrayList<>();

        for (Station s : mStations) {
            objects.add((Object) s);
        }

        return objects;
    }

    private ArrayList<Station> convertObjectListToStationList(ArrayList<Object> objects) {
        ArrayList<Station> stations = new ArrayList<>();

        for (Object o : objects) {
            stations.add((Station) o);
        }

        return stations;
    }

    private ArrayList<Station> getFavoriteStationsList() {
        ArrayList<Object> objects = tinyDB.getListObject(Const.Preference.PK_STATIONS, Station.class);
        ArrayList<Station> stations = new ArrayList<>();

        for (Object o : objects) {
            stations.add((Station) o);
        }

        return stations;
    }

    private void configureRadio() {
        mStations = getFavoriteStationsList();

        mRadio = new Radio("Избранное", mStations);

        Object object = (Object) mRadio;
        tinyDB.putObject(Const.Preference.PK_RADIO, object);
    }

    public Radio getRadio() {
        mRadio = (Radio) tinyDB.getObject(Const.Preference.PK_RADIO, Radio.class);
        return mRadio;
    }

    public boolean checkContainsStation(Station station){
        boolean result = false;
        for (Station s : mStations){
            result = s.getName().equals(station.getName());
            if (result)
                break;
        }
        return result;
    }
}
