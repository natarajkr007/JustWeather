package com.nataraj.android.justweather.gson;

import com.nataraj.android.justweather.utilities.ConverterUtil;

@SuppressWarnings({"UnusedDeclaration"})
public class Wind {

    private double speed;
    private double deg;

    public double getDeg() {
        return deg;
    }

    public double getSpeed() {
        return speed;
    }

    public String getStats() {
        return Double.toString(speed) + " km/h " + ConverterUtil.getWindDirection(deg);
    }
}
