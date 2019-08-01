package com.nataraj.android.justweather.database;

import android.annotation.SuppressLint;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.nataraj.android.justweather.utilities.ConverterUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nataraj-7085 on 4/7/18.
 */

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
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd,EEEE,d,MMM");
        Date todayDate = new Date();
        String formattedToday = dateFormat.format(todayDate);
        String[] splitDate = formattedToday.split(",");
        if (splitDate[0].equals(date)) {
            return "Today";
        } else {
            return date;
        }
    }

    public String getTime() {
        return time;
    }

    public String getDecodedTime() {
        String hour = time.split(":")[0];
        String suffix;
        int decodTime;
        int decodHour = Integer.parseInt(hour);
        if(decodHour > 12) {
            decodTime = decodHour - 12;
            suffix = "PM";
        } else if (decodHour == 12) {
            decodTime = decodHour;
            suffix = "PM";
        } else if (decodHour == 0) {
            decodTime = 12;
            suffix = "AM";
        } else {
            decodTime = decodHour;
            suffix = "AM";
        }
        return String.valueOf(decodTime) + " " + suffix;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public String getStringPressure() {
        return Double.toString(pressure) + " hPa";
    }

    public String getStringHumidity() {
        return Double.toString(humidity) + "%";
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    int getCloudsPercent() {
        return cloudsPercent;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getWindStats() {
        return Double.toString(getWindSpeed()) + " km/h " + ConverterUtil.getWindDirection(getWindDeg());
    }

    public double getWindDeg() {
        return windDeg;
    }

    double getRain3h() {
        return rain3h;
    }

    double getSnow3h() {
        return snow3h;
    }

    public int getId() {
        return id;
    }

    long getMillis() {
        return millis;
    }

    public void setId(int id) {
        this.id = id;
    }
}
