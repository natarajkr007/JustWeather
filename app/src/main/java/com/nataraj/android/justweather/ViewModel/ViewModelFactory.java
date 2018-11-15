package com.nataraj.android.justweather.ViewModel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.nataraj.android.justweather.database.AppDatabase;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private String location;
    private String country;
    private Application application;

    public ViewModelFactory(String location, String country) {
        this.location = location;
        this.country = country;
    }

    public ViewModelFactory(Application application, String location, String country) {
        this.application = application;
        this.location = location;
        this.country = country;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CurrentWeatherViewModel.class)) {
            return (T) new CurrentWeatherViewModel(location, country);
        } else if (modelClass.isAssignableFrom(ForecastViewModel.class)) {
            return (T) new ForecastViewModel(application, location, country);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
