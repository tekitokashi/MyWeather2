package com.example.dinar.myweather;

import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherDataLoader {
    private static final String MY_WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String KEY = "x-api-key";
    private static final String RESPONSE = "cod";
    private static final String NEW_LINE = "\n";
    private final static int ALL_GOOD = 200;

    static JSONObject getJSONData(Context context, String city) {
        try {
            URL url = new URL(String.format(MY_WEATHER_API_URL, city));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.addRequestProperty(KEY, MY_WEATHER_API_URL);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder rawData = new StringBuilder(1024);
            String tempVariable;
            while((tempVariable = reader.readLine()) != null) {
                rawData.append(tempVariable).append(NEW_LINE);
            }

            reader.close();

            JSONObject jsonObject = new JSONObject(rawData.toString());
            if(jsonObject.getInt(RESPONSE) != ALL_GOOD) {
                return null;
            } else {
                return jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
