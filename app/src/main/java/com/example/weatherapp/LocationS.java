package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.LocationManager;
import android.location.Location;

import java.io.IOException;
import java.security.Provider;

public class LocationS implements LocationService {
    private Context cont;
    private LocationManager managerLoc;
    private GeoS geoEncoder;



    public LocationS(Context context) {
        cont = context;
        managerLoc = (LocationManager) cont.getSystemService(Context.LOCATION_SERVICE);
        geoEncoder = new GeoS(cont);
    }

    @SuppressLint("MissingPermission")
    @Override
    public Address getDeviceLocationCoord() throws IOException {
        Location loc = null;
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(false);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        String best = managerLoc.getBestProvider(criteria, false);
        loc = managerLoc.getLastKnownLocation(best);
        return geoEncoder.locationToAddress(loc.getLongitude(), loc.getLatitude());
    }

    @Override
    public Address getDeviceLocationName(String name) throws IOException {
       return geoEncoder.addressToLocation(name);
    }
}
