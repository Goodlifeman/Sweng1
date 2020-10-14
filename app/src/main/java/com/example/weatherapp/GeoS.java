package com.example.weatherapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;

public class GeoS implements GeoRecordingService {
    private Context context;
    private Geocoder geoC;

    public GeoS(Context cont){
        context = cont;
        geoC = new Geocoder(context);
    }


    @Override
    public Address locationToAddress(double longitude, double latitude) throws IOException {
        return geoC.getFromLocation(latitude, longitude, 1).get(0);
    }

    @Override
    public Address addressToLocation(String ad) throws IOException {
        return geoC.getFromLocationName(ad, 1).get(0);
    }
}
