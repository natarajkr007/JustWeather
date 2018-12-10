package com.nataraj.android.justweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings({"UnusedDeclaration"})
public class ForecastList {

    @SerializedName("dt")
    private long date;

    private Main main;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private Rain rain;
    private Snow snow;

    @SerializedName("dt_txt")
    private String dateText;

    public long getDate() {
        return date * 1000;
    }

    public Main getMain() {
        return main;
    }

    public String getWeatherDescription() {
        return weather.get(0).getDescription();
    }

    public String getWeatherIcon() {
        return weather.get(0).getIcon();
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
