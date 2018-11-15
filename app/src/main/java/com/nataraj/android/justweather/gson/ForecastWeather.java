package com.nataraj.android.justweather.gson;

import java.util.List;

@SuppressWarnings({"UnusedDeclaration"})
public class ForecastWeather {

    private String cod;
    private double message;
    private int cnt;
    private List<ForecastList> list;
    private City city;

    public String getCod() {
        return cod;
    }

    public double getMessage() {
        return message;
    }

    public int getCnt() {
        return cnt;
    }

    public List<ForecastList> getList() {
        return list;
    }

    public City getCity() {
        return city;
    }
}
