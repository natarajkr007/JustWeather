package com.nataraj.android.justweather.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

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

    @Query("SELECT * FROM forecast WHERE date = :date")
    LiveData<List<WeatherEntry>> loadForecastByDate(String date);

    @Query("DELETE FROM forecast")
    void nukeData();
}
