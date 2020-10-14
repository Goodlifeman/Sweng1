package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;

import java.io.IOException;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private WeatherS weather;
    private LocationS location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weather = new WeatherS();
        location = new LocationS(this);
        //Allows for network operations to run synchronously on the main thread
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }

    public void weatherFromName(View view) throws IOException, JSONException {
        EditText cityField = (EditText) findViewById(R.id.cityTextField);
        String cityText = cityField.getText().toString();
        Address city = location.getDeviceLocationName(cityText);
        weatherIntent(city);
    }

    public void weatherFromDeviceLocation(View view) throws IOException, JSONException {
        //TODO
    }

    private void weatherIntent(Address city) throws IOException, JSONException {
        Intent intent = new Intent(this, WeatherDisplayActivity.class);
        Map<String, String> weatherData = weather.getWeather(city.getLongitude(),city.getLatitude());
        intent.putExtra("weatherType",weatherData.get("weather"));
        intent.putExtra("temperature", weatherData.get("temperature"));
        intent.putExtra("icon",weatherData.get("icon"));
        intent.putExtra("nameCountry",city.getCountryName());
        intent.putExtra("nameAdminArea",city.getLocality());
        startActivity(intent);
    }

}