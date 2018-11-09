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

    @SerializedName("dt")
    private long date;

    private Sys sys;
    private long id;

    @SerializedName("name")
    private String city;

    private int cod;
}
