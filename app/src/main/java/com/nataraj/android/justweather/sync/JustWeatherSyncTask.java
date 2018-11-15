package com.nataraj.android.justweather.sync;

import com.nataraj.android.justweather.database.AppDatabase;
import com.nataraj.android.justweather.gson.ForecastWeather;
import com.nataraj.android.justweather.network.OpenWeatherMapClient;
import com.nataraj.android.justweather.utilities.OpenWeatherDBUtils;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by nataraj-7085 on 4/7/18.
 */

public class JustWeatherSyncTask {

    synchronized public static boolean syncForecast(final AppDatabase mDb, String location, String country) {

        try {
            Response<ForecastWeather> response = OpenWeatherMapClient.getInstance().getForecast(location, country).execute();
            if (response.isSuccessful() && response.body() != null) {
                OpenWeatherDBUtils.insertForecastIntoDB(response.body(), mDb);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}