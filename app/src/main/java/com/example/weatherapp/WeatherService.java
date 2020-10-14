package com.example.weatherapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public interface WeatherService {
    public Map<String, String> getWeather(double longitude, double latitude) throws IOException, JSONException;

}
