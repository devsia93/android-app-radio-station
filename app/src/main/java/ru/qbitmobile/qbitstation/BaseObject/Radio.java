package ru.qbitmobile.qbitstation.BaseObject;

import java.util.ArrayList;

public class Radio {

    private String genre;
    private ArrayList<Station> stations/* = new ArrayList<Station>()*/;

    public Radio(String genre, ArrayList<Station> stations){
        this.genre = genre;
        this.stations = stations;
    }

    public void setName(String genre) {
        this.genre = genre;
    }

    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }

    public String getGenre() {
        return genre;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public String[] getStationNames(){
        String[] result = new String[stations.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = stations.get(i).getName();
        }

        return result;
    }

}
