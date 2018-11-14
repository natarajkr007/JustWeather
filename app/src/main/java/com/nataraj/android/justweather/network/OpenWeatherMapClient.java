package com.nataraj.android.justweather.network;

import com.nataraj.android.justweather.gson.CurrentWeather;
import com.nataraj.android.justweather.gson.ForecastWeather;
import com.nataraj.android.justweather.utilities.Config;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherMapClient {

    private static OpenWeatherMapClient instance = null;

    private Retrofit retrofit;
    private OpenWeatherMapService openWeatherMapService;

    private OpenWeatherMapClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Config.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        openWeatherMapService = retrofit.create(OpenWeatherMapService.class);
    }

    public static OpenWeatherMapClient getInstance() {
        if (instance == null) {
            synchronized (OpenWeatherMapClient.class) {
                if (instance == null) {
                    instance = new OpenWeatherMapClient();
                }
            }
        }

        return instance;
    }

    public Call<CurrentWeather> getCurrentWeather(String location, String country) {
        if (country == null) country = Config.getDefaultCountry();

        return openWeatherMapService.callWeatherApi(Config.getCurrentWeatherApiPath(), getApiParams(location, country));
    }

    public Call<ForecastWeather> getForecast(String location, String country) {
        if (country == null) country = Config.getDefaultCountry();

        return openWeatherMapService.callForecastApi(Config.getForecastApiPath(), getApiParams(location, country));
    }

    private HashMap<String, String> getApiParams(String location, String country) {
        HashMap<String, String> params = new HashMap<>();

        params.put(Config.getQueryKey(), String.format(Config.getQueryValue(), location, country));
        params.put(Config.getModeKey(), Config.getModeValue());
        params.put(Config.getApiKey(), Config.getApiKeyValue());

        return params;
    }
}