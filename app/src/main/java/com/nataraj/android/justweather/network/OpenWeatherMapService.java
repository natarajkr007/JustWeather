package com.nataraj.android.justweather.network;

import com.nataraj.android.justweather.gson.CurrentWeather;
import com.nataraj.android.justweather.gson.ForecastWeather;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface OpenWeatherMapService {

    @GET("{path}")
    Call<CurrentWeather> callWeatherApi(@Path("path") String relativePath, @QueryMap HashMap<String, String> params);

    @GET("{path}")
    Call<ForecastWeather> callForecastApi(@Path("path") String relativePath, @QueryMap HashMap<String, String> params);
}
