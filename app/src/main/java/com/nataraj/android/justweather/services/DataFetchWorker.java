package com.nataraj.android.justweather.services;

import android.content.Context;
import android.support.annotation.NonNull;

import com.nataraj.android.justweather.gson.CurrentWeather;
import com.nataraj.android.justweather.network.OpenWeatherMapClient;
import com.nataraj.android.justweather.utilities.AppDelegate;
import com.nataraj.android.justweather.utilities.ConverterUtil;
import com.nataraj.android.justweather.utilities.NotificationUtil;
import com.nataraj.android.justweather.utilities.WeatherIconUtils;

import java.io.IOException;

import androidx.work.Worker;
import androidx.work.WorkerParameters;
import retrofit2.Call;
import retrofit2.Response;

public class DataFetchWorker extends Worker {

//    private Result result;

    public DataFetchWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Call<CurrentWeather> currentWeatherCall = OpenWeatherMapClient
                .getInstance()
                .getCurrentWeather(AppDelegate.appDelegate.getCityFromPreference(), null);

        try {
            Response<CurrentWeather> currentWeatherResponse = currentWeatherCall.execute();
            if (currentWeatherResponse.isSuccessful()) {
                CurrentWeather currentWeather = currentWeatherResponse.body();

                if (currentWeather != null) {
                    NotificationUtil.showNotification(getApplicationContext(),
                            currentWeather.getCity(),
                            ConverterUtil.getTemp(currentWeather.getMain().getTemp()),
                            WeatherIconUtils.getWeatherIconId(currentWeather.getWeatherIcon()));

                    return Result.SUCCESS;
                }
            }

            return Result.FAILURE;
        } catch (IOException e) {
            e.printStackTrace();
            return Result.RETRY;
        }
    }
}
