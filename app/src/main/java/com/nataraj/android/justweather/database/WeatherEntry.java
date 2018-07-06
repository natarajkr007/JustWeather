package com.nataraj.android.justweather.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nataraj-7085 on 4/7/18.
 */

// TODO handle uniqueness on millies
@Entity(tableName = "forecast")
public class WeatherEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private long millis;

    @ColumnInfo(name = "min_temp")
    private double minTemp;

    @ColumnInfo(name = "max_temp")
    private double maxTemp;

    private double pressure;
    private double humidity;

    @ColumnInfo(name = "weather_description")
    private String weatherDescription;

    @ColumnInfo(name = "weather_icon")
    private String weatherIcon;

    @ColumnInfo(name = "clouds_percent")
    private int cloudsPercent;

    @ColumnInfo(name = "wind_speed")
    private double windSpeed;

    @ColumnInfo(name = "wind_deg")
    private double windDeg;

    @ColumnInfo(name = "rain_3h")
    private double rain3h;

    @ColumnInfo(name = "snow_3h")
    private double snow3h;

    private String date;

    private String time;

    public WeatherEntry(long millis, double minTemp, double maxTemp, double pressure, double humidity, String weatherDescription,
                        String weatherIcon, int cloudsPercent, double windSpeed, double windDeg, double rain3h, double snow3h,
                        String date, String time) {
        this.millis = millis;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.weatherDescription = weatherDescription;
        this.weatherIcon = weatherIcon;
        this.cloudsPercent = cloudsPercent;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.rain3h = rain3h;
        this.snow3h = snow3h;
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd,EEEE,d,MMM");
        Date todayDate = new Date();
        String formattedToday = dateFormat.format(todayDate);
        String[] splitDate = formattedToday.split(",");
        if (splitDate[0].equals(date)) {
            String displayDate = "Today";
            return displayDate;
        } else {
//            String displayDate = splitDate[1] + ", " + splitDate[2] + " " + splitDate[3];
            String displayDate = date;
            return displayDate;
        }
    }

    public String getTime() {
        return time;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getMinTempC() {
        return minTemp - 273.15;
    }

    public double getMaxTempC() {
        return maxTemp - 273.15;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public int getCloudsPercent() {
        return cloudsPercent;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDeg() {
        return windDeg;
    }

    public double getRain3h() {
        return rain3h;
    }

    public double getSnow3h() {
        return snow3h;
    }

    public int getId() {
        return id;
    }

    public long getMillis() {
        return millis;
    }

    public void setId(int id) {
        this.id = id;
    }
}
