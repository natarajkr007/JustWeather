package com.nataraj.android.justweather.gson;

@SuppressWarnings({"UnusedDeclaration"})
public class Sys {

    private int type;
    private int id;
    private double message;
    private String country;
    private String sunrise;
    private String sunset;

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public double getMessage() {
        return message;
    }

    public String getCountry() {
        return country;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }
}
