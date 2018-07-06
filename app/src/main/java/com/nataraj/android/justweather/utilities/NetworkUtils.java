package com.nataraj.android.justweather.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private static final String API_PATH = "data/2.5/forecast";
    private static final String API_KEY_PARAM = "appid";
    private static final String API_KEY = "8e72e6c6015c87b8332f61d8da0f82ab";
    private static final String FORMAT_PARAM = "mode";
    private static final String FORMAT_JSON = "json";
    private static final String QUERY_PARAM = "q";
    private static final String COUNTRY = "in";

    private static final String IMG_URL = "http://openweathermap.org/img/w";

//    TODO to be implement with user prefered location after shared preferences are done
    public static URL getURL(Context context, String location) {
        String tempLocation = location;
        return buildUrlWithLocationQuery(tempLocation);
    }

    public static URL buildUrlWithLocationQuery(String locationQuery) {
        String finalLocationQuery = locationQuery + "," + COUNTRY;
        Uri weatherQueryUri = Uri.parse(API_URL).buildUpon()
                .appendEncodedPath(API_PATH)
                .appendQueryParameter(QUERY_PARAM, finalLocationQuery)
                .appendQueryParameter(FORMAT_PARAM, FORMAT_JSON)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        try {
            URL weatherQueryUrl = new URL(weatherQueryUri.toString());
            Log.d(TAG, "URL: " + weatherQueryUrl);
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

//    to get the image icons from server
    public static Bitmap getWeatherIcon(String iconId) {
        String extraPath = iconId + ".png";
        Uri weatherIconUri = Uri.parse(IMG_URL).buildUpon()
                .appendEncodedPath(extraPath)
                .build();
        URL weatherIconUrl;
        try {
            weatherIconUrl = new URL(weatherIconUri.toString());
            return fetchedWeatherIcon(weatherIconUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap fetchedWeatherIcon(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Bitmap myBitmap = BitmapFactory.decodeStream(in);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }
}
