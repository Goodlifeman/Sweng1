package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WeatherDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_display);

        Intent intent = getIntent();
        String weatherType =  intent.getStringExtra("weatherType");
        String temperature = intent.getStringExtra("temperature");
        String icon = intent.getStringExtra("icon");
        String countryName = intent.getStringExtra("nameCountry");
        String adminAreaName = intent.getStringExtra("nameAdminArea");

        TextView countryView = (TextView) findViewById(R.id.countryView);
        TextView cityView = (TextView) findViewById(R.id.cityView);
        TextView weatherView = (TextView) findViewById(R.id.weatherTextView);
        TextView temperatureView = (TextView) findViewById(R.id.temperatureTextView);

        weatherView.setText(weatherType);
        temperatureView.setText(temperature+" C");
        countryView.setText(countryName);
        cityView.setText(adminAreaName);

    }



}