package com.nataraj.android.justweather.utilities;

public final class Config {

    private static final String BASE_URL = "https://api.openweathermap.org/";
    private static final String FORECAST_API_PATH = "data/2.5/forecast";
    private static final String CURR_WEATHER_API_PATH = "data/2.5/weather";
    private static final String DEFAULT_COUNTRY = "in";
    private static final String QUERY_KEY = "q";
    private static final String QUERY_VALUE = "%1$s,%2$s";
    private static final String MODE_KEY = "mode";
    private static final String MODE_VALUE = "json";
    private static final String API_KEY = "appid";
    private static final String API_KEY_VALUE = "8e72e6c6015c87b8332f61d8da0f82ab";


    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getForecastApiPath() {
        return FORECAST_API_PATH;
    }

    public static String getCurrentWeatherApiPath() {
        return CURR_WEATHER_API_PATH;
    }

    public static String getQueryKey() {
        return QUERY_KEY;
    }

    public static String getQueryValue() {
        return QUERY_VALUE;
    }

    public static String getModeKey() {
        return MODE_KEY;
    }

    public static String getModeValue() {
        return MODE_VALUE;
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getApiKeyValue() {
        return API_KEY_VALUE;
    }

    public static String getDefaultCountry() {
        return DEFAULT_COUNTRY;
    }
}
