package com.nataraj.android.justweather.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by nataraj-7085 on 3/7/18.
 */

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    /*
        Example url to get response
        api.openweathermap.org/data/2.5/forecast?q=London,us&mode=json&appid=<api_key>
    */

    private static final String API_URL = "https://api.openweathermap.org";
    private static final String API_PATH = "/data/2.5/forecast";
    private static final String API_KEY_PARAM = "appid";
    private static final String API_KEY = "8e72e6c6015c87b8332f61d8da0f82ab";
    private static final String FORMAT_PARAM = "mode";
    private static final String FORMAT_JSON = "json";
    private static final String QUERY_PARAM = "q";
    private static final String COUNTRY = "in";

//    TODO to be implement after shared preferences done
    public static URL getURL() {
        return null;
    }

    public static URL buildUrlWithLocationQuery(String locationQuery) {
        String finalLocationQuery = locationQuery + "," + COUNTRY;
        Uri weatherQueryUri = Uri.parse(API_URL).buildUpon()
                .appendPath(API_PATH)
                .appendQueryParameter(QUERY_PARAM, finalLocationQuery)
                .appendQueryParameter(FORMAT_PARAM, FORMAT_JSON)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        try {
            URL weatherQueryUrl = new URL(weatherQueryUri.toString());
            Log.v(TAG, "URL: " + weatherQueryUrl);
            return weatherQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseFromUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }

            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }
}
