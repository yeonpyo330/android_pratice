package com.example.savemoneyproject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("data/2.5/weather?&appid=0ee1e3eadd152b696e4e6773ea9b3c8c&units=metric")
    Call<WeatherResponse> getCurrentWeatherData(@Query("q") String name);
}
