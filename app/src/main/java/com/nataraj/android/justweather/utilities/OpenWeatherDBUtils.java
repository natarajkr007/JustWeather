package com.nataraj.android.justweather.utilities;

import com.nataraj.android.justweather.database.AppDatabase;
import com.nataraj.android.justweather.database.WeatherEntry;
import com.nataraj.android.justweather.gson.ForecastList;
import com.nataraj.android.justweather.gson.ForecastWeather;

import java.util.List;

/**
 * Created by nataraj-7085 on 4/7/18.
 */

public class OpenWeatherDBUtils {

    public static void insertForecastIntoDB(ForecastWeather forecastWeather, final AppDatabase mDb) {

        List<ForecastList> forecastList = forecastWeather.getList();
        for (ForecastList forecast: forecastList) {
            final long millisDate = forecast.getDate();
            final double minTemp = forecast.getMain().getMinTemp();
            final double maxTemp = forecast.getMain().getMaxTemp();
            final double pressure = forecast.getMain().getPressure();
            final double humidity = forecast.getMain().getHumidity();
            final String weatherDescription = forecast.getWeatherDescription();
            final String weatherIcon = forecast.getWeatherIcon();
            final int cloudsPercent = forecast.getClouds().getAll();
            final double windSpeed = forecast.getWind().getSpeed();
            final double windDeg = forecast.getWind().getDeg();
            final double rain3h = forecast.getRain() == null ? 0 : forecast.getRain().getVol3h();
            final double snow3h = forecast.getSnow() == null ? 0 : forecast.getSnow().getVol3h();
            final String[] dateNTime = forecast.getDateText().split(" ");

            WeatherEntry weatherEntry = new WeatherEntry(millisDate, minTemp, maxTemp, pressure, humidity, weatherDescription,
                    weatherIcon, cloudsPercent,windSpeed, windDeg, rain3h, snow3h, dateNTime[0], dateNTime[1]);
            mDb.weatherDao().insertWeatherEntry(weatherEntry);
        }
    }
}
