package com.nataraj.android.justweather;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nataraj.android.justweather.ViewModel.ForecastViewModel;
import com.nataraj.android.justweather.database.WeatherEntry;
import com.nataraj.android.justweather.utilities.ConverterUtil;
import com.nataraj.android.justweather.utilities.WeatherIconUtils;

import java.util.List;

/**
 * {@link TomorrowForecastFragment} is responsible for UI of next day forecast.
 */
public class TomorrowForecastFragment extends Fragment {

    private WeatherEntry tomorrowForecast;
    private AppCompatActivity fragmentActivity;

    private TextView dateView;
    private TextView minMaxTempView;
    private TextView tempView;
    private TextView weatherDescriptionView;
    private ImageView weatherIcon;
    private TextView humidityView;
    private TextView pressureView;
    private TextView windView;

    private TodayForecastAdapter mTodayForecastAdapter;

    public TomorrowForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentActivity = (AppCompatActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.one_day_forecast, container, false);

        dateView = rootView.findViewById(R.id.oneday_date);
        minMaxTempView = rootView.findViewById(R.id.min_max_temp);
        tempView = rootView.findViewById(R.id.curr_temp);
        weatherDescriptionView = rootView.findViewById(R.id.curr_weather_description);
        weatherIcon = rootView.findViewById(R.id.weather_icon);
        humidityView = rootView.findViewById(R.id.tv_humidity_val);
        pressureView = rootView.findViewById(R.id.tv_pressure_val);
        windView = rootView.findViewById(R.id.tv_wind_val);

        RecyclerView mHourForecastRecyclerView = rootView.findViewById(R.id.hour_forecast_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mHourForecastRecyclerView.setLayoutManager(linearLayoutManager);
        mHourForecastRecyclerView.setHasFixedSize(true);
        mTodayForecastAdapter = new TodayForecastAdapter(this.getActivity());
        mHourForecastRecyclerView.setAdapter(mTodayForecastAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        ForecastViewModel viewModel = ViewModelProviders.of(fragmentActivity).get(ForecastViewModel.class);
        viewModel.getTomorrowForecast().observe(fragmentActivity, new Observer<List<WeatherEntry>>() {
            @Override
            public void onChanged(@Nullable List<WeatherEntry> weatherEntries) {
                if (weatherEntries != null && weatherEntries.size() > 0) {
                    tomorrowForecast = weatherEntries.get(0);
                } else {
                    return;
                }
                dateView.setText(getString(R.string.tomorrow_tab_title));

                String minTemp = ConverterUtil.getTemp(tomorrowForecast.getMinTemp());
                String maxTemp = ConverterUtil.getTemp(tomorrowForecast.getMaxTemp());

                minMaxTempView.setText(String.format(getString(R.string.min_max_place_holder), maxTemp, minTemp));
                tempView.setText(maxTemp);
                weatherDescriptionView.setText(tomorrowForecast.getWeatherDescription());
                weatherIcon.setImageResource(WeatherIconUtils.getWeatherIconId(tomorrowForecast.getWeatherIcon()));
                humidityView.setText(tomorrowForecast.getStringHumidity());
                pressureView.setText(tomorrowForecast.getStringPressure());
                windView.setText(tomorrowForecast.getWindStats());

                mTodayForecastAdapter.setTasks(weatherEntries);
                mTodayForecastAdapter.notifyDataSetChanged();
            }
        });
    }
}
