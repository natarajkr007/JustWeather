package com.nataraj.android.justweather.gson;

public class City {

    private double id;
    private String name;
    private Coord coord;
    private String country;
    private double population;

    public double getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coord getCoord() {
        return coord;
    }

    public String getCountry() {
        return country;
    }

    public double getPopulation() {
        return population;
    }
}