package com.example.savemoneyproject;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class WeatherResponse {

    @SerializedName("main")
    private Main main;

    @SerializedName("sys")
    private Sys sys;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }
}

class Main {

    @SerializedName("temp")
    String temp;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}

class Sys{

    @SerializedName("country")
    String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
