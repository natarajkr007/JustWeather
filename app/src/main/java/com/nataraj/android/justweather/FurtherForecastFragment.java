package com.nataraj.android.justweather;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nataraj.android.justweather.database.AppDatabase;
import com.nataraj.android.justweather.database.WeatherEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FurtherForecastFragment extends Fragment {

    private static final String TAG = FurtherForecastFragment.class.getSimpleName();

    private RecyclerView mForecastRecyclerView;
    private ForecastAdapter mForecastAdapter;
    private AppDatabase mDb;
    private HashMap<String, DaySummary> daySummaryEntries;
    private String[] days = new String[5];

    public FurtherForecastFragment() {
        // empty constructor required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: FurtherForecastFragment");
        daySummaryEntries = new HashMap<>();

        View rootView = inflater.inflate(R.layout.forecast_recycler_view, container, false);

        //        initiating Forecast Recycler view
        mForecastRecyclerView = rootView.findViewById(R.id.recyclerview_forecast);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        mForecastRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
        mForecastRecyclerView.addItemDecoration(decoration);
        mForecastRecyclerView.setHasFixedSize(true);
        mForecastAdapter = new ForecastAdapter(this.getActivity());
        mForecastRecyclerView.setAdapter(mForecastAdapter);

        mDb = AppDatabase.getsInstance(getActivity().getApplicationContext());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

//        mForecastAdapter.setTasks(mDb.weatherDao().loadForecast());
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                mForecastAdapter.setTasks(mDb.weatherDao().loadForecast());
//                return null;
//            }
//        }.execute();
        WeatherViewModel viewModel = ViewModelProviders.of(getActivity()).get(WeatherViewModel.class);
//        final LiveData<List<WeatherEntry>> weatherEntries = mDb.weatherDao().loadForecast();
        viewModel.getForecast().observe(getActivity(), new Observer<List<WeatherEntry>>() {
            @Override
            public void onChanged(@Nullable List<WeatherEntry> weatherEntries) {
                daySummaryEntries.clear();
                makeDayWiseWeatherForecast(weatherEntries);
                mForecastAdapter.setTasks(daySummaryEntries, days);
                mForecastAdapter.notifyDataSetChanged();
            }
        });

//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                final LiveData<List<WeatherEntry>> weatherEntries = mDb.weatherDao().loadForecast();
//                weatherEntries.observe(getActivity(), new Observer<List<WeatherEntry>>() {
//                    @Override
//                    public void onChanged(@Nullable List<WeatherEntry> weatherEntries) {
//                        mForecastAdapter.setTasks(weatherEntries);
//                    }
//                });
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mForecastAdapter.setTasks(weatherEntries);
//                        mForecastAdapter.notifyDataSetChanged();
//                    }
//                });
//            }
//        });
    }

    public void makeDayWiseWeatherForecast(List<WeatherEntry> weatherEntries) {
        int i = 0;
        for (WeatherEntry weatherEntry: weatherEntries) {
            if (!daySummaryEntries.containsKey(weatherEntry.getDate())) {
                DaySummary daySummary = new DaySummary();

                daySummary.minTemp = weatherEntry.getMinTemp();
                daySummary.minTempC = weatherEntry.getMinTempC();
                daySummary.minTempF = weatherEntry.getMinTempF();
                daySummary.maxTemp = weatherEntry.getMaxTemp();
                daySummary.maxTempC = weatherEntry.getMaxTempC();
                daySummary.maxTempF = weatherEntry.getMaxTempF();
                daySummary.weatherDescription = weatherEntry.getWeatherDescription();
                daySummary.weatherIcon = weatherEntry.getWeatherIcon();
                daySummary.windSpeed = weatherEntry.getWindSpeed();
                daySummary.windDirection = weatherEntry.getWindDirection();
                daySummary.humidity = weatherEntry.getHumidity();
                daySummary.pressure = weatherEntry.getPressure();
                daySummary.hourWeather.add(weatherEntry);

                daySummaryEntries.put(weatherEntry.getDate(), daySummary);
                days[i++] = weatherEntry.getDate();
            } else {
                DaySummary daySummary = daySummaryEntries.get(weatherEntry.getDate());
                if (daySummary.minTemp > weatherEntry.getMinTemp()) {
                    daySummary.minTemp = weatherEntry.getMinTemp();
                    daySummary.minTempC = weatherEntry.getMinTempC();
                    daySummary.minTempF = weatherEntry.getMinTempF();
                }
                if (daySummary.maxTemp < weatherEntry.getMaxTemp()) {
                    daySummary.maxTemp = weatherEntry.getMaxTemp();
                    daySummary.maxTempC = weatherEntry.getMaxTempC();
                    daySummary.maxTempF = weatherEntry.getMaxTempF();
                    daySummary.weatherDescription = weatherEntry.getWeatherDescription();
                    daySummary.weatherIcon = weatherEntry.getWeatherIcon();
                    daySummary.windSpeed = weatherEntry.getWindSpeed();
                    daySummary.windDirection = weatherEntry.getWindDirection();
                }
                daySummary.humidity = Math.max(daySummary.humidity, weatherEntry.getHumidity());
                daySummary.pressure = Math.max(daySummary.pressure, weatherEntry.getPressure());
                daySummary.hourWeather.add(weatherEntry);
            }
        }
    }

    class DaySummary {
        double minTemp;
        double maxTemp;
        String minTempC;
        String maxTempC;
        String minTempF;
        String maxTempF;
        String weatherDescription;
        String weatherIcon;
        double windSpeed;
        String windDirection;
        double humidity;
        double pressure;
        List<WeatherEntry> hourWeather = new ArrayList<>();
    }
}