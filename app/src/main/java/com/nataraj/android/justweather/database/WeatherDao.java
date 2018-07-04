package com.nataraj.android.justweather.database;

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
    List<WeatherEntry> loadForecast();

    @Insert
    void insertWeatherEntry(WeatherEntry weatherEntry);

    @Delete
    void deleteWeatherEntry(WeatherEntry weatherEntry);

    @Query("SELECT * FROM forecast WHERE millis = :millis")
    List<WeatherEntry> loadForecastByMillis(long millis);
}
