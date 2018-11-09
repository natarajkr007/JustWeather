package com.nataraj.android.justweather.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private String location;
    private String country;

    public ViewModelFactory(String location, String country) {
        this.location = location;
        this.country = country;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CurrentWeatherViewModel.class)) {
            return (T) new CurrentWeatherViewModel(location, country);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
