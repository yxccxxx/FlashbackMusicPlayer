package com.example.jaygandhi.flashbackmusicteam34;

/**
 * Created by jaygandhi on 3/16/18.
 */

public class SystemLocation implements iLocation {
    private double latitude;
    private double longitude;

    public void setLocationStamp(double lati, double longi) {
        latitude = lati;
        longitude = longi;
    }

    public double getLocationStampLat() {
        return latitude;
    }

    public double getLocationStampLong() {
        return longitude;
    }
}
