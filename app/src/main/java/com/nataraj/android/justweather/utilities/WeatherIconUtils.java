package com.nataraj.android.justweather.utilities;

import com.nataraj.android.justweather.R;

/**
 * Created by nataraj-7085 on 6/7/18.
 */

public class WeatherIconUtils {

    private static final String SUN = "01d";
    private static final String SUN_CLOUD = "02d";
    private static final String CLOUD = "03d";
    private static final String BROKEN_CLOUDS = "04d";
    private static final String RAIN = "09d";
    private static final String DAY_RAIN = "10d";
    private static final String THUNDER = "11d";
    private static final String SNOW = "13d";
    private static final String MIST = "50d";

    private static final String MOON = "01n";
    private static final String MOON_CLOUD = "02n";
    private static final String NIGHT_CLOUD = "03n";
    private static final String NIGHT_BROKEN_CLOUDS = "04n";
    private static final String NIGHT_RAIN = "09n";
    private static final String MOON_RAIN = "10n";
    private static final String NIGHT_THUNDER = "11n";
    private static final String NIGHT_SNOW = "13n";
    private static final String NIGHT_MIST = "50n";

    public static int getWeatherIconId(String owmId) {

        if (owmId.equals(SUN)) {
            return R.drawable.ic_sun;
        } else if (owmId.equals(MOON)) {
            return R.drawable.ic_moon;
        } else if (owmId.equals(SUN_CLOUD)) {
            return R.drawable.ic_cloud_sun;
        } else if (owmId.equals(MOON_CLOUD)) {
            return R.drawable.ic_clouds_night;
        } else if (owmId.equals(CLOUD) || owmId.equals(NIGHT_CLOUD)) {
            return R.drawable.ic_clouds;
        } else if (owmId.equals(BROKEN_CLOUDS) || owmId.equals(NIGHT_BROKEN_CLOUDS)) {
            return R.drawable.ic_rain_cloud;
        } else if (owmId.equals(RAIN) || owmId.equals(NIGHT_RAIN)) {
            return R.drawable.ic_rainy;
        } else if (owmId.equals(DAY_RAIN)) {
            return R.drawable.ic_sun_rain;
        } else if (owmId.equals(MOON_RAIN)) {
            return R.drawable.ic_night_rain;
        } else if (owmId.equals(THUNDER) || owmId.equals(NIGHT_THUNDER)) {
            return R.drawable.ic_thunder;
        } else if (owmId.equals(SNOW) || owmId.equals(NIGHT_SNOW)) {
            return R.drawable.ic_snowing;
        } else {
            return R.drawable.ic_haze;
        }

    }
}
