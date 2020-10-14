package com.example.weatherapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class WeatherS implements  WeatherService {
    private final String API_KEY;

    public WeatherS(){
        API_KEY = Integer.toString(R.string.weather_key);
    }

    private String getDailyWeather(JSONObject json) throws JSONException {
        return json.getJSONArray("daily").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main");
    }

    private String getDailyTemperature(JSONObject json) throws JSONException {
        return Double.toString(json.getJSONArray("daily").getJSONObject(0).getJSONObject("temp").getDouble("day"));
    }

    private String getDailyIcon(JSONObject json) throws JSONException {
        return json.getJSONArray("daily").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("icon");
    }

    private String readStream(InputStream stream, int maxReadSize)
            throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        char[] rawBuffer = new char[maxReadSize];
        int readSize;
        StringBuffer buffer = new StringBuffer();
        while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
            if (readSize > maxReadSize) {
                readSize = maxReadSize;
            }
            buffer.append(rawBuffer, 0, readSize);
            maxReadSize -= readSize;
        }
        return buffer.toString();
    }

    private String weatherRequest(double longitude, double latitude, String keyAPI) throws IOException {
        String queryUrl = "https://api.openweathermap.org/data/2.5/onecall?lat="+latitude+"&lon="+longitude+"&exclude=current,minutely,hourly,alerts&units=metric&appid="+keyAPI;
        URL url = new URL(queryUrl);

        InputStream stream = null;
        HttpsURLConnection connection = null;
        String result = null;

        connection = (HttpsURLConnection) url.openConnection();
        connection.setReadTimeout(3000);
        connection.setConnectTimeout(3000);
        connection.setRequestMethod("GET");

        // Already true by default but setting just in case; needs to be true since this request
        // is carrying an input (response) body.
        connection.setDoInput(true);

        int responseCode = connection.getResponseCode();

        if (responseCode != HttpsURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + responseCode);
        }

        stream = connection.getInputStream();
        if (stream != null) {
            // Converts Stream to String with max length of 500.
            result = readStream(stream, 10000);
        }
        stream.close();
        connection.disconnect();
        return result;
    }


    @Override
    public Map<String, String> getWeather(double longitude, double latitude) throws IOException, JSONException {
        Map<String,String> ret = new HashMap();
        String weatherData = weatherRequest(longitude, latitude, API_KEY);
        JSONObject json = (JSONObject) new JSONTokener(weatherData).nextValue();
        ret.put("temperature",getDailyTemperature(json));
        ret.put("weather", getDailyWeather(json));
        ret.put("icon", getDailyIcon(json));
        return ret;
    }
}
