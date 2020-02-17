package ru.qbitmobile.qbitstation.BaseObject;

import java.io.Serializable;

public class Station implements Serializable {
    private String name;
    private String stream;
    private String image;

    public Station(String name, String stream, String image){
        this.name = name;
        this.stream = stream;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public String getStream() {
        return stream;
    }

}
