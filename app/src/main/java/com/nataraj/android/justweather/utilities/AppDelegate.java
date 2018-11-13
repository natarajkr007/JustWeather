package com.nataraj.android.justweather.utilities;

import android.app.Application;
import android.content.SharedPreferences;

import com.nataraj.android.justweather.R;

public class AppDelegate extends Application {

    public static AppDelegate appDelegate = null;
    private SharedPreferences prefs;

    @Override
    public void onCreate() {
        super.onCreate();

        appDelegate = this;
        initPreferences();
    }

    private void initPreferences() {
        prefs = getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE);
    }

    public String getMetric() {
        return prefs.getString(getString(R.string.units_key), getString(R.string.celcius));
    }

    public boolean isCelsius() {
        return getMetric().equals(getString(R.string.celcius));
    }
}
