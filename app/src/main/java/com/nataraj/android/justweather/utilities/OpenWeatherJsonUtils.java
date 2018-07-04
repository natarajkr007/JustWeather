package com.nataraj.android.justweather.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.nataraj.android.justweather.database.AppDatabase;
import com.nataraj.android.justweather.database.WeatherEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by nataraj-7085 on 4/7/18.
 */

public class OpenWeatherJsonUtils {

    private static final String TAG = OpenWeatherJsonUtils.class.getSimpleName();

//    Network code to cross check
    private static final String OWM_RESPONSE_CODE = "cod";

    private static final String OWM_RESPONSE_COUNT = "cnt";

//    to get weather details
    private static final String OWM_LIST = "list";
    private static final String OWM_LIST_DT = "dt";
    private static final String OWM_LIST_MAIN = "main";
    private static final String OWM_LIST_MAIN_TEMP_MIN = "temp_min";
    private static final String OWM_LIST_MAIN_TEMP_MAX = "temp_max";
    private static final String OWM_LIST_MAIN_PRESSURE = "pressure";
    private static final String OWM_LIST_MAIN_SEA_LEVEL = "sea_level";
    private static final String OWM_LIST_MAIN_GRND_LEVEL = "grnd_level";
    private static final String OWM_LIST_MAIN_HUMIDITY = "humidity";
    private static final String OWM_LIST_WEATHER = "weather";
    private static final String OWM_LIST_WEATHER_DESCRIPTION = "description";
    private static final String OWM_LIST_WEATHER_ICON = "icon";
    private static final String OWM_LIST_CLOUDS = "clouds";
    private static final String OWM_LIST_CLOUDS_ALL = "all";
    private static final String OWM_LIST_WIND = "wind";
    private static final String OWM_LIST_WIND_SPEED = "speed";
    private static final String OWM_LIST_WIND_DEG = "deg";
    private static final String OWM_LIST_RAIN = "rain";
    private static final String OWM_LIST_RAIN_3H = "3h";
    private static final String OWM_LIST_SNOW = "snow";
    private static final String OWM_LIST_SNOW_3H = "3h";
    private static final String OWM_LIST_DT_TXT = "dt_txt";

    //    to get city details
    private static final String OWM_CITY = "city";
    private static final String OWM_CITY_ID = "id";
    private static final String OWM_CITY_NAME = "name";
    private static final String OWM_CITY_COORD = "coord";
    private static final String OWM_CITY_COORD_LAT = "lat";
    private static final String OWM_CITY_COORD_LON = "lon";
    private static final String OWM_CITY_COUNTRY = "country";
    private static final String OWM_CITY_POPULATION = "population";

    public static void getWeatherContentValuesFromJson(Context context, String forecastJsonStr, AppDatabase mDb)
        throws JSONException {

        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        if (forecastJson.has(OWM_RESPONSE_CODE)) {
            int errorCode = forecastJson.getInt(OWM_RESPONSE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;

                case HttpURLConnection.HTTP_NOT_FOUND:
                    return;
                default:
                    return;
            }
        }

        JSONArray jsonWeatherList = forecastJson.getJSONArray(OWM_LIST);

        JSONObject jsonCity = forecastJson.getJSONObject(OWM_CITY);
        String city = jsonCity.getString(OWM_CITY_NAME);
        String country = jsonCity.getString(OWM_CITY_COUNTRY);
        String population = jsonCity.getString(OWM_CITY_POPULATION);
        int cityId = jsonCity.getInt(OWM_CITY_ID);

        JSONObject jsonCityCoord = jsonCity.getJSONObject(OWM_CITY_COORD);
        double cityLatitude = jsonCityCoord.getDouble(OWM_CITY_COORD_LAT);
        double cityLongitude = jsonCityCoord.getDouble(OWM_CITY_COORD_LON);

        for (int i = 0; i < jsonWeatherList.length(); i++) {
            long dateTimeMillis;
            double maxTemp;
            double minTemp;
            double pressure;
            double humidity;
            String weatherDescription;
            String weatherIcon;
            int cloudsPrecent;
            double windSpeed;
            double windDeg;
            double rain3h = 0;
            double snow3h = 0;
            String dateTxt;
            JSONObject hourRainForecast = null;
            JSONObject hourSnowForecast = null;

            Log.d(TAG, Integer.toString(i));
            JSONObject hourForecast = jsonWeatherList.getJSONObject(i);
            JSONObject hourMainForecast = hourForecast.getJSONObject(OWM_LIST_MAIN);
            JSONArray hourWeatherForecast = hourForecast.getJSONArray(OWM_LIST_WEATHER);
            JSONObject weather = hourWeatherForecast.getJSONObject(0);
            JSONObject hourCloudsForecast = hourForecast.getJSONObject(OWM_LIST_CLOUDS);
            JSONObject hourWindForecast = hourForecast.getJSONObject(OWM_LIST_WIND);
            if (hourForecast.has(OWM_LIST_RAIN)) {
                hourRainForecast = hourForecast.getJSONObject(OWM_LIST_RAIN);
            }
            if (hourForecast.has(OWM_LIST_SNOW)) {
                hourSnowForecast = hourForecast.getJSONObject(OWM_LIST_SNOW);
            }

            dateTimeMillis = hourForecast.getLong(OWM_LIST_DT) * 1000;
            maxTemp = hourMainForecast.getDouble(OWM_LIST_MAIN_TEMP_MAX);
            minTemp = hourMainForecast.getDouble(OWM_LIST_MAIN_TEMP_MIN);
            pressure = hourMainForecast.getDouble(OWM_LIST_MAIN_PRESSURE);
            humidity = hourMainForecast.getDouble(OWM_LIST_MAIN_HUMIDITY);
            weatherDescription = weather.getString(OWM_LIST_WEATHER_DESCRIPTION);
            weatherIcon = weather.getString(OWM_LIST_WEATHER_ICON);
            cloudsPrecent = hourCloudsForecast.getInt(OWM_LIST_CLOUDS_ALL);
            windSpeed = hourWindForecast.getDouble(OWM_LIST_WIND_SPEED);
            windDeg = hourWindForecast.getDouble(OWM_LIST_WIND_DEG);
            if (hourRainForecast != null && hourRainForecast.has(OWM_LIST_RAIN_3H)) {
                rain3h = hourRainForecast.getDouble(OWM_LIST_RAIN_3H);
            }
            if (hourSnowForecast != null && hourSnowForecast.has(OWM_LIST_SNOW_3H)) {
                snow3h = hourSnowForecast.getDouble(OWM_LIST_SNOW_3H);
            }
            dateTxt = hourForecast.getString(OWM_LIST_DT_TXT);

            WeatherEntry weatherEntry = new WeatherEntry(dateTimeMillis, minTemp, maxTemp, pressure, humidity, weatherDescription,
                    weatherIcon, cloudsPrecent,windSpeed, windDeg, rain3h, snow3h, dateTxt);
            mDb.weatherDao().insertWeatherEntry(weatherEntry);
            Log.d(TAG, "Data Entry made");
        }
    }
}
