package com.example.weatherapp;

import android.location.Address;

import java.io.IOException;

public interface GeoRecordingService {
    public Address locationToAddress(double longitude, double latitude) throws IOException;

    public Address addressToLocation(String ad) throws IOException;
}
