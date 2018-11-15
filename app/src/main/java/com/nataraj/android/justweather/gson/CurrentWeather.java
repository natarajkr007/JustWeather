package com.nataraj.android.justweather.gson;

import android.text.format.DateFormat;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

@SuppressWarnings({"UnusedDeclaration"})
public class CurrentWeather {

    private static final String DATE_CHECK_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT = "h:mm a";

    private Coord coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    private Rain rain;
    private Snow snow;

    @SerializedName("dt")
    private long date;

    private Sys sys;
    private long id;

    @SerializedName("name")
    private String city;

    private int cod;

    public Coord getCoord() {
        return coord;
    }

    private List<Weather> getWeather() {
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
        return date * 1000;
    }

    public boolean checkIfToday() {
        String todayDate = (String) DateFormat.format(DATE_CHECK_FORMAT, new Date());
        String currentWeatherDate = (String) DateFormat.format(DATE_CHECK_FORMAT, new Date(getDate()));

        return todayDate.equals(currentWeatherDate);

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

    public String getWeatherDescription() {
        return getWeather().get(0).getDescription();
    }

    public String getWeatherIcon() {
        return getWeather().get(0).getIcon();
    }
}
