package com.nataraj.android.justweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastList {

    @SerializedName("dt")
    private long date;

    private Main main;
    private List<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private Rain rain;
    private Snow snow;

    @SerializedName("dt_txt")
    private String dateText;

    public long getDate() {
        return date;
    }

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public Rain getRain() {
        return rain;
    }

    public Snow getSnow() {
        return snow;
    }

    public String getDateText() {
        return dateText;
    }
}
