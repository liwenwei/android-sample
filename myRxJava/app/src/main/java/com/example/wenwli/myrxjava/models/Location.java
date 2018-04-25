package com.example.wenwli.myrxjava.models;

public class Location {

    private double longitude;//经度
    private double latitude;//纬度

    public Location(double longitude, double latitude) {

        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "(" + longitude + ", " + latitude + ')';
    }
}

