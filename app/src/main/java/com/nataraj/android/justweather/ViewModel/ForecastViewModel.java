package com.nataraj.android.justweather.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.nataraj.android.justweather.AppExecutors;
import com.nataraj.android.justweather.database.AppDatabase;
import com.nataraj.android.justweather.database.WeatherEntry;
import com.nataraj.android.justweather.sync.JustWeatherSyncTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by nataraj-7085 on 9/7/18.
 */

public class ForecastViewModel extends AndroidViewModel {

    private LiveData<List<WeatherEntry>> forecast;
    private LiveData<List<WeatherEntry>> todayForecast;
    private LiveData<List<WeatherEntry>> tomorrowForecast;
    private MutableLiveData<Boolean> status = new MutableLiveData<>();
    private String city;
    private String country;
    private AppDatabase mDb;

    ForecastViewModel(@NonNull Application application, String city, String country) {
        super(application);

        this.city = city;
        this.country = country;

        mDb = AppDatabase.getsInstance(this.getApplication());
        forecast = mDb.weatherDao().loadForecast();
        todayForecast = mDb.weatherDao().loadForecastByDate("Today");
        tomorrowForecast = mDb.weatherDao().loadForecastByDate(getTomorrowQuery());

        fetchData();
    }

    private void fetchData() {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.weatherDao().nukeData(); // Deleting the old data
                status.postValue(JustWeatherSyncTask.syncForecast(mDb, city, country));
            }
        });
    }

    public boolean fetchData(String city, String country) {
        return JustWeatherSyncTask.syncForecast(mDb, city, country);
    }

    public MutableLiveData<Boolean> getStatus() {
        return status;
    }

    public LiveData<List<WeatherEntry>> getForecast() {
        return forecast;
    }

    public LiveData<List<WeatherEntry>> getTodayForecast() {
        return todayForecast;
    }

    public LiveData<List<WeatherEntry>> getTomorrowForecast() {
        return tomorrowForecast;
    }

    private String getTomorrowQuery() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, 1);
        final Date tomorrow = cal.getTime();

        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(tomorrow);
    }
}
