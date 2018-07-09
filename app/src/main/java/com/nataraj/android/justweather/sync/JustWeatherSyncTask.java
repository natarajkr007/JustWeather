package com.nataraj.android.justweather.sync;

import android.content.Context;
import android.util.Log;

import com.nataraj.android.justweather.database.AppDatabase;
import com.nataraj.android.justweather.utilities.NetworkUtils;
import com.nataraj.android.justweather.utilities.OpenWeatherJsonUtils;

import java.net.URL;

/**
 * Created by nataraj-7085 on 4/7/18.
 */

public class JustWeatherSyncTask {

    private static final String TAG = JustWeatherSyncTask.class.getSimpleName();

    synchronized public static boolean syncWeather(Context context, AppDatabase mDb, String location) {
        try {
            URL weatherRequestUrl = NetworkUtils.getURL(context, location);

            String jsonWeatherResponse = NetworkUtils.getResponseFromUrl(weatherRequestUrl);
            Log.d(TAG, "Asked to store in DB");
            return OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context, jsonWeatherResponse, mDb);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}