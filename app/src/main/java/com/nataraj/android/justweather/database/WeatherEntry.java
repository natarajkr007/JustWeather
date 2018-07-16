package com.nataraj.android.justweather.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd,EEEE,d,MMM");
        Date todayDate = new Date();
        String formattedToday = dateFormat.format(todayDate);
        String[] splitDate = formattedToday.split(",");
        if (splitDate[0].equals(date)) {
            return "Today";
        } else {
//            String displayDate = splitDate[1] + ", " + splitDate[2] + " " + splitDate[3];
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

    public String getMinTempC() {
        double temp = minTemp - 273.15;
        String res = String.format("%.1f", temp) + "\u00b0\u0043";
        return res;
    }

    public String getMaxTempC() {
        double temp = maxTemp - 273.15;
        String res = String.format("%.1f", temp) + "\u00b0\u0043";
        return res;
    }

    public String getMinTempF() {
        double temp = ((9 * minTemp) / 5) - 459.67;
        String res = String.format("%.1f", temp) + "\u00b0\u0046";
        return res;
    }

    public String getMaxTempF() {
        double temp = ((9 * maxTemp) / 5) - 459.67;
        String res = String.format("%.1f", temp) + "\u00b0\u0046";
        return res;
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

    public String getWindDirection() {
        String direction = "Unknown";
        if (windDeg >= 337.5 || windDeg < 22.5) {
            direction = "N";
        } else if (windDeg >= 22.5 && windDeg < 67.5) {
            direction = "NE";
        } else if (windDeg >= 67.5 && windDeg < 112.5) {
            direction = "E";
        } else if (windDeg >= 112.5 && windDeg < 157.5) {
            direction = "SE";
        } else if (windDeg >= 157.5 && windDeg < 202.5) {
            direction = "S";
        } else if (windDeg >= 202.5 && windDeg < 247.5) {
            direction = "SW";
        } else if (windDeg >= 247.5 && windDeg < 292.5) {
            direction = "W";
        } else if (windDeg >= 292.5 && windDeg < 337.5) {
            direction = "NW";
        }
        return direction;
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
