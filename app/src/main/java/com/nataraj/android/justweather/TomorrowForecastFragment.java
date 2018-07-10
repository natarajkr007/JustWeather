package com.nataraj.android.justweather;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nataraj.android.justweather.database.AppDatabase;
import com.nataraj.android.justweather.database.WeatherEntry;
import com.nataraj.android.justweather.utilities.WeatherIconUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TomorrowForecastFragment extends Fragment {

    private static final String TAG = TomorrowForecastFragment.class.getSimpleName();

//    private LiveData<List<WeatherEntry>> weatherEntries;
    private WeatherEntry tomorrowForecast;
    private AppDatabase mDb;

    private TextView dateView;
    private TextView minMaxTempView;
    private TextView tempView;
    private TextView weatherDescriptionView;
    private ImageView weatherIcon;
    private TextView humidityView;
    private TextView pressureView;
    private TextView windView;

    private RecyclerView mHourForecastRecyclerView;
    private TodayForecastAdapter mTodayForecastAdapter;

    public TomorrowForecastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: TodayForecastFragment");
        View rootView = inflater.inflate(R.layout.one_day_forecast, container, false);

        dateView = rootView.findViewById(R.id.oneday_date);
        minMaxTempView = rootView.findViewById(R.id.min_max_temp);
        tempView = rootView.findViewById(R.id.curr_temp);
        weatherDescriptionView = rootView.findViewById(R.id.curr_weather_description);
        weatherIcon = rootView.findViewById(R.id.weather_icon);
        humidityView = rootView.findViewById(R.id.tv_humidity_val);
        pressureView = rootView.findViewById(R.id.tv_pressure_val);
        windView = rootView.findViewById(R.id.tv_wind_val);

        mHourForecastRecyclerView = rootView.findViewById(R.id.hour_forecast_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mHourForecastRecyclerView.setLayoutManager(linearLayoutManager);
        mHourForecastRecyclerView.setHasFixedSize(true);
        mTodayForecastAdapter = new TodayForecastAdapter(this.getActivity());
        mHourForecastRecyclerView.setAdapter(mTodayForecastAdapter);

        mDb = AppDatabase.getsInstance(getActivity().getApplicationContext());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
//        Date today = new Date();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(today);
//        cal.add(Calendar.DATE, 1);
//        final Date tomorrow = cal.getTime();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String queryDate = dateFormat.format(tomorrow);
//        weatherEntries = mDb.weatherDao().loadForecastByDate(queryDate);
        WeatherViewModel viewModel = ViewModelProviders.of(getActivity()).get(WeatherViewModel.class);
        viewModel.getTomorrowForecast().observe(getActivity(), new Observer<List<WeatherEntry>>() {
            @Override
            public void onChanged(@Nullable List<WeatherEntry> weatherEntries) {
                if (weatherEntries.size() > 0) {
                    tomorrowForecast = weatherEntries.get(0);
                } else {
                    return;
                }
                dateView.setText("Tomorrow");

                String minTemp, maxTemp;

                SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.shared_pref_name), getActivity().MODE_PRIVATE);
                if (preferences.getString(getString(R.string.units_key), getString(R.string.celcius)).equals(getString(R.string.celcius))) {
                    minTemp = tomorrowForecast.getMinTempC();
                    maxTemp = tomorrowForecast.getMaxTempC();
                } else {
                    minTemp = tomorrowForecast.getMinTempF();
                    maxTemp = tomorrowForecast.getMaxTempF();
                }

                minMaxTempView.setText(
                        "High " + maxTemp + "\u2191 \u22c5 Low " +
                                minTemp + "\u2193"
                );
                tempView.setText(maxTemp);
                weatherDescriptionView.setText(tomorrowForecast.getWeatherDescription());
                weatherIcon.setImageResource(WeatherIconUtils.getWeatherIconId(tomorrowForecast.getWeatherIcon()));
                humidityView.setText(String.valueOf(tomorrowForecast.getHumidity()) + "%");
                pressureView.setText(String.valueOf(tomorrowForecast.getPressure()) + " hPa");
                windView.setText(String.valueOf(tomorrowForecast.getWindSpeed()) + "km/h" + " " + tomorrowForecast.getWindDirection());

                mTodayForecastAdapter.setTasks(weatherEntries);
                mTodayForecastAdapter.notifyDataSetChanged();
            }
        });
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                Date today = new Date();
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(today);
//                cal.add(Calendar.DATE, 1);
//                final Date tomorrow = cal.getTime();
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                String queryDate = dateFormat.format(tomorrow);
//                weatherEntries = mDb.weatherDao().loadForecastByDate(queryDate);
//                weatherEntries.observe(getActivity(), new Observer<List<WeatherEntry>>() {
//                    @Override
//                    public void onChanged(@Nullable List<WeatherEntry> weatherEntries) {
//                        if (weatherEntries.size() > 0) {
//                            tomorrowForecast = weatherEntries.get(0);
//                        } else {
//                            return;
//                        }
//                        dateView.setText("Tomorrow");
//
//                        String minTemp, maxTemp;
//
//                        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.shared_pref_name), getActivity().MODE_PRIVATE);
//                        if (preferences.getString(getString(R.string.units_key), getString(R.string.celcius)).equals(getString(R.string.celcius))) {
//                            minTemp = tomorrowForecast.getMinTempC();
//                            maxTemp = tomorrowForecast.getMaxTempC();
//                        } else {
//                            minTemp = tomorrowForecast.getMinTempF();
//                            maxTemp = tomorrowForecast.getMaxTempF();
//                        }
//
//                        minMaxTempView.setText(
//                                "High " + maxTemp + "\u2191 \u22c5 Low " +
//                                        minTemp + "\u2193"
//                        );
//                        tempView.setText(maxTemp);
//                        weatherDescriptionView.setText(tomorrowForecast.getWeatherDescription());
//                        weatherIcon.setImageResource(WeatherIconUtils.getWeatherIconId(tomorrowForecast.getWeatherIcon()));
//
//                        mTodayForecastAdapter.setTasks(weatherEntries);
//                        mTodayForecastAdapter.notifyDataSetChanged();
//                    }
//                });
//                if (weatherEntries.size() > 0) {
//                    tomorrowForecast = weatherEntries.get(0);
//                } else {
//                    return;
//                }
//
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        dateView.setText("Tomorrow");
//
//                        String minTemp, maxTemp;
//
//                        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.shared_pref_name), getActivity().MODE_PRIVATE);
//                        if (preferences.getString(getString(R.string.units_key), getString(R.string.celcius)).equals(getString(R.string.celcius))) {
//                            minTemp = tomorrowForecast.getMinTempC();
//                            maxTemp = tomorrowForecast.getMaxTempC();
//                        } else {
//                            minTemp = tomorrowForecast.getMinTempF();
//                            maxTemp = tomorrowForecast.getMaxTempF();
//                        }
//
//                        minMaxTempView.setText(
//                                "High " + maxTemp + "\u2191 \u22c5 Low " +
//                                        minTemp + "\u2193"
//                        );
//                        tempView.setText(maxTemp);
//                        weatherDescriptionView.setText(tomorrowForecast.getWeatherDescription());
//                        weatherIcon.setImageResource(WeatherIconUtils.getWeatherIconId(tomorrowForecast.getWeatherIcon()));
//
//                        mTodayForecastAdapter.setTasks(weatherEntries);
//                        mTodayForecastAdapter.notifyDataSetChanged();
//                    }
//                });
//            }
//        });
    }
}
