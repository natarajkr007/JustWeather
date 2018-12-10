package com.nataraj.android.justweather.utilities;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;

import com.nataraj.android.justweather.R;
import com.nataraj.android.justweather.services.DataFetchWorker;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class AppDelegate extends Application {

    public static AppDelegate appDelegate = null;

    private static final String PREFS_CITY_KEY = "city";
    private static final String DEFAULT_CITY = "Kuppam";

    private SharedPreferences prefs;

    @Override
    public void onCreate() {
        super.onCreate();

        appDelegate = this;
        initPreferences();
        createNotificationChannel();
        initWorker();
    }

    private void initPreferences() {
        prefs = getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE);
    }

    private void initWorker() {
        PeriodicWorkRequest.Builder dataFetchWorkBuilder = new PeriodicWorkRequest.Builder(DataFetchWorker.class, 3, TimeUnit.HOURS);

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build();

        WorkManager.getInstance().enqueueUniquePeriodicWork("3_hour_weather_fetch_work",
                ExistingPeriodicWorkPolicy.KEEP,
                dataFetchWorkBuilder.setConstraints(constraints).build());
    }

    public String getCityFromPreference() {
        return prefs.getString(PREFS_CITY_KEY, DEFAULT_CITY);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NotificationUtil.NOTIFICATION_3H_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public String getMetric() {
        return prefs.getString(getString(R.string.units_key), getString(R.string.celcius));
    }

    public boolean isCelsius() {
        return getMetric().equals(getString(R.string.celcius));
    }
}
