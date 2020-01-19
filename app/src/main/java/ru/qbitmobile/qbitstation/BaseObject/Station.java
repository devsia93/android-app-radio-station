package ru.qbitmobile.qbitstation.BaseObject;

public class Station {
    private String mName;
    private String mStream;
    private String mImage;

    public Station(String name, String stream, String image){
        this.mName = name;
        this.mStream = stream;
        this.mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setStream(String stream) {
        this.mStream = stream;
    }

    public void setImage(String image) {
        this.mImage = image;
    }

    public String getImage() {
        return mImage;
    }

    public String getStream() {
        return mStream;
    }
}
