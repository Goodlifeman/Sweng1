package com.example.weatherapp;

import android.location.Address;

import java.io.IOException;

public interface LocationService {
    //gets the current Device Location, returns true if it succeeded
    public Address getDeviceLocationCoord() throws IOException;

    public Address getDeviceLocationName(String name) throws IOException;
}
