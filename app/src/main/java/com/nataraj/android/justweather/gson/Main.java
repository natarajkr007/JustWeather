package com.nataraj.android.justweather.gson;

import com.google.gson.annotations.SerializedName;

public class Main {

    private double temp;
    private double pressure;
    private double humidity;

    @SerializedName(value = "temp_min")
    private double minTemp;

    @SerializedName(value = "temp_max")
    private double maxTemp;

    @SerializedName("sea_level")
    private double seaLevel;

    @SerializedName("grnd_level")
    private double grndLevel;

    public double getTemp() {
        return temp;
    }

    public double getSeaLevel() {
        return seaLevel;
    }

    public double getGrndLevel() {
        return grndLevel;
    }

    public String getPressure() {
        return Double.toString(pressure) + "hPa";
    }

    public String getHumidity() {
        return Double.toString(humidity) + "%";
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }
}
