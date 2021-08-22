package com.nataraj.android.justweather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nataraj.android.justweather.ViewModel.ForecastViewModel;
import com.nataraj.android.justweather.database.WeatherEntry;
import com.nataraj.android.justweather.utilities.ConverterUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FurtherForecastFragment extends Fragment {

    private ForecastAdapter mForecastAdapter;
    private HashMap<String, DaySummary> daySummaryEntries;
    private String[] days = new String[6];

    private AppCompatActivity fragmentActivity;

    public FurtherForecastFragment() {
        // empty constructor required
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentActivity = (AppCompatActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        daySummaryEntries = new HashMap<>();

        View rootView = inflater.inflate(R.layout.forecast_recycler_view, container, false);

        //        initiating Forecast Recycler view
        RecyclerView mForecastRecyclerView = rootView.findViewById(R.id.recyclerview_forecast);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        mForecastRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(fragmentActivity, DividerItemDecoration.VERTICAL);
        mForecastRecyclerView.addItemDecoration(decoration);
        mForecastRecyclerView.setHasFixedSize(true);
        mForecastAdapter = new ForecastAdapter(this.getActivity());
        mForecastRecyclerView.setAdapter(mForecastAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        ForecastViewModel viewModel = ViewModelProviders.of(fragmentActivity).get(ForecastViewModel.class);
        viewModel.getForecast().observe(fragmentActivity, new Observer<List<WeatherEntry>>() {
            @Override
            public void onChanged(@Nullable List<WeatherEntry> weatherEntries) {
                daySummaryEntries.clear();
                makeDayWiseWeatherForecast(weatherEntries);
                mForecastAdapter.setTasks(daySummaryEntries, days);
                mForecastAdapter.notifyDataSetChanged();
            }
        });
    }

    public void makeDayWiseWeatherForecast(List<WeatherEntry> weatherEntries) {
        int i = 0;
        for (WeatherEntry weatherEntry: weatherEntries) {
            if (!daySummaryEntries.containsKey(weatherEntry.getDate())) {
                DaySummary daySummary = new DaySummary();

                daySummary.minTemp = weatherEntry.getMinTemp();
                daySummary.maxTemp = weatherEntry.getMaxTemp();
                daySummary.minStrTemp = ConverterUtil.getTemp(weatherEntry.getMinTemp());
                daySummary.maxStrTemp = ConverterUtil.getTemp(weatherEntry.getMaxTemp());
                daySummary.weatherDescription = weatherEntry.getWeatherDescription();
                daySummary.weatherIcon = weatherEntry.getWeatherIcon();
                daySummary.windSpeed = weatherEntry.getWindSpeed();
                daySummary.windDirection = ConverterUtil.getWindDirection(weatherEntry.getWindDeg());
                daySummary.humidity = weatherEntry.getHumidity();
                daySummary.pressure = weatherEntry.getPressure();
                daySummary.hourWeather.add(weatherEntry);

                daySummaryEntries.put(weatherEntry.getDate(), daySummary);
                days[i++] = weatherEntry.getDate();
            } else {
                DaySummary daySummary = daySummaryEntries.get(weatherEntry.getDate());
                if (daySummary != null) {
                    if (daySummary.minTemp > weatherEntry.getMinTemp()) {
                        daySummary.minTemp = weatherEntry.getMinTemp();
                        daySummary.minStrTemp = ConverterUtil.getTemp(weatherEntry.getMinTemp());
                    }
                    if (daySummary.maxTemp < weatherEntry.getMaxTemp()) {
                        daySummary.maxTemp = weatherEntry.getMaxTemp();
                        daySummary.maxStrTemp = ConverterUtil.getTemp(weatherEntry.getMaxTemp());
                        daySummary.weatherDescription = weatherEntry.getWeatherDescription();
                        daySummary.weatherIcon = weatherEntry.getWeatherIcon();
                        daySummary.windSpeed = weatherEntry.getWindSpeed();
                        daySummary.windDirection = ConverterUtil.getWindDirection(weatherEntry.getWindDeg());
                    }
                    daySummary.humidity = Math.max(daySummary.humidity, weatherEntry.getHumidity());
                    daySummary.pressure = Math.max(daySummary.pressure, weatherEntry.getPressure());
                    daySummary.hourWeather.add(weatherEntry);
                }
            }
        }
    }

    class DaySummary {
        double minTemp;
        double maxTemp;
        String minStrTemp;
        String maxStrTemp;
        String weatherDescription;
        String weatherIcon;
        double windSpeed;
        String windDirection;
        double humidity;
        double pressure;
        List<WeatherEntry> hourWeather = new ArrayList<>();

        String getHumidityString() {
            return Double.toString(humidity) + "%";
        }

        String getPressureString() {
            return Double.toString(pressure) + " hPa";
        }

        String getWindStats() {
            return Double.toString(windSpeed) + " km/h " + windDirection;
        }
    }
}