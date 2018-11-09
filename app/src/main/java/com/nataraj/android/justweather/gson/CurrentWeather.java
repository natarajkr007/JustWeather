package com.nataraj.android.justweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeather {

    private Coord coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    private Rain rain;
    private Snow snow;

    public Coord getCoord() {
        return coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }

    public Main getMain() {
        return main;
    }

    public int getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Rain getRain() {
        return rain;
    }

    public Snow getSnow() {
        return snow;
    }

    public long getDate() {
        return date;
    }

    public Sys getSys() {
        return sys;
    }

    public long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public int getCod() {
        return cod;
    }

    @SerializedName("dt")
    private long date;

    private Sys sys;
    private long id;

    @SerializedName("name")
    private String city;

    private int cod;
}
