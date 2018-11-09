package com.nataraj.android.justweather.network;

import com.nataraj.android.justweather.gson.CurrentWeather;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface OpenWeatherMapService {

    @GET("{path}")
    Call<CurrentWeather> getForecast(@Path("path") String relativePath, @QueryMap HashMap<String, String> params);
}
