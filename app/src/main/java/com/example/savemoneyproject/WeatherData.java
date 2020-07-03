package com.example.savemoneyproject;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherData {

    public static String BaseUrl = "http://api.openweathermap.org/";
    private static WeatherData weatherData;
    private Retrofit retrofit;
    private WeatherService weatherService;

    public WeatherService getWeatherService() {
         retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         weatherService = retrofit.create(WeatherService.class);

         return weatherService;
    }
}
