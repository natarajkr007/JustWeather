package com.nataraj.android.justweather.gson;

import com.google.gson.annotations.SerializedName;

public class Main {

    private String temp;
    private String pressure;
    private String humidity;

    @SerializedName(value = "temp_min")
    private String minTemp;

    @SerializedName(value = "temp_max")
    private String maxTemp;

    @SerializedName("sea_level")
    private double seaLevel;

    @SerializedName("grnd_level")
    private double grndLevel;

    public String getTemp() {
        return temp;
    }

    public double getSeaLevel() {
        return seaLevel;
    }

    public double getGrndLevel() {
        return grndLevel;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }
}
