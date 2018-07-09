package com.nataraj.android.justweather;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nataraj.android.justweather.database.AppDatabase;
import com.nataraj.android.justweather.database.WeatherEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by nataraj-7085 on 9/7/18.
 */

public class WeatherViewModel extends AndroidViewModel {

    private LiveData<List<WeatherEntry>> forecast;
    private LiveData<List<WeatherEntry>> todayForecast;
    private LiveData<List<WeatherEntry>> tomorrowForecast;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        AppDatabase mDb = AppDatabase.getsInstance(this.getApplication());
        forecast = mDb.weatherDao().loadForecast();
        todayForecast = mDb.weatherDao().loadForecastByDate("Today");
        tomorrowForecast = mDb.weatherDao().loadForecastByDate(getTomorrowQuery());
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String queryDate = dateFormat.format(tomorrow);
        return queryDate;
    }
}
