package com.nataraj.android.justweather.gson;

import com.nataraj.android.justweather.utilities.ConverterUtil;

public class Wind {

    private double speed;
    private double deg;

    public String getStats() {
        return Double.toString(speed) + " km/h " + ConverterUtil.getWindDirection(deg);
    }
}
