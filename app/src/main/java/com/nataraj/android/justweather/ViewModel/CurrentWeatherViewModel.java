package com.nataraj.android.justweather.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.nataraj.android.justweather.gson.CurrentWeather;
import com.nataraj.android.justweather.network.OpenWeatherMapClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentWeatherViewModel extends ViewModel {

    public MutableLiveData<CurrentWeather> currentWeatherLiveData = new MutableLiveData<>();

    CurrentWeatherViewModel(String location, String country) {
        loadCurrentWeather(location, country);
    }

    private void loadCurrentWeather(String location, String country) {
        OpenWeatherMapClient.getInstance().getCurrentWeather(location, country).enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(@NonNull Call<CurrentWeather> call, @NonNull Response<CurrentWeather> response) {
                if (response.isSuccessful()) {
                    currentWeatherLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CurrentWeather> call, @NonNull Throwable t) {

            }
        });
    }
}
