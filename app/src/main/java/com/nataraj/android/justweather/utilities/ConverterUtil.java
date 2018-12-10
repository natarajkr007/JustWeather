package com.nataraj.android.justweather.utilities;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale")
public final class ConverterUtil {

    private static final double CONVERT_CONST = 273.15;

    public static String getTemp(double temp) {
        if (AppDelegate.appDelegate.isCelsius()) {
            return getCTemp(temp);
        } else {
            return getFTemp(temp);
        }
    }

    private static String getCTemp(double temp) {
        return String.format("%.1f", temp - CONVERT_CONST) + "\u00b0 C";
    }

    private static String getFTemp(double temp) {
        return String.format("%.1f", (temp - CONVERT_CONST) * (9 / 5) + 32) + "\u00b0 F";
    }

    public static String getWindDirection(double windDeg) {
        String direction;
        if (windDeg >= 337.5 || windDeg < 22.5) {
            direction = "N";
        } else if (windDeg < 67.5) {
            direction = "NE";
        } else if (windDeg < 112.5) {
            direction = "E";
        } else if (windDeg < 157.5) {
            direction = "SE";
        } else if (windDeg < 202.5) {
            direction = "S";
        } else if (windDeg < 247.5) {
            direction = "SW";
        } else if (windDeg < 292.5) {
            direction = "W";
        } else {
            direction = "NW";
        }
        return direction;
    }
}
