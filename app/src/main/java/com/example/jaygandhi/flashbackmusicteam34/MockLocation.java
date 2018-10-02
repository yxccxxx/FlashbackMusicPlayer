package com.example.jaygandhi.flashbackmusicteam34;

import java.io.Serializable;

/**
 * Created by jaygandhi on 3/16/18.
 */

public class MockLocation implements iLocation, Serializable {
    public static double latitude;
    public static double longitude;

    public MockLocation(double lati, double longi) {
        this.latitude = lati;
        this.longitude = longi;
    }

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
