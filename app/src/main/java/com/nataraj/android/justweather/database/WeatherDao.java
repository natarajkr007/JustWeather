package com.nataraj.android.justweather.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by nataraj-7085 on 4/7/18.
 */

@Dao
public interface WeatherDao {

    @Query("SELECT * FROM forecast ORDER BY millis")
    LiveData<List<WeatherEntry>> loadForecast();

    @Insert
    void insertWeatherEntry(WeatherEntry weatherEntry);

    @Delete
    void deleteWeatherEntry(WeatherEntry weatherEntry);

    @Query("SELECT * FROM forecast WHERE date = :date")
    LiveData<List<WeatherEntry>> loadForecastByDate(String date);

    @Query("DELETE FROM forecast")
    void nukeData();
}
