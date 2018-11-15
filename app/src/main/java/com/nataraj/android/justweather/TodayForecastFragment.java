package com.nataraj.android.justweather;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nataraj.android.justweather.ViewModel.CurrentWeatherViewModel;
import com.nataraj.android.justweather.ViewModel.ForecastViewModel;
import com.nataraj.android.justweather.database.WeatherEntry;
import com.nataraj.android.justweather.gson.CurrentWeather;
import com.nataraj.android.justweather.utilities.ConverterUtil;
import com.nataraj.android.justweather.utilities.WeatherIconUtils;

import java.util.List;

/**
 * {@link TodayForecastFragment} is the UI response for showing current weather.
 */
public class TodayForecastFragment extends Fragment {

    private WeatherEntry presentForecast;

    private TextView dateView;
    private TextView minMaxTempView;
    private TextView nowTempView;
    private TextView weatherDescriptionView;
    private ImageView weatherIcon;
    private TextView humidityView;
    private TextView pressureView;
    private TextView windView;
    private TextView gistView;

    private TodayForecastAdapter mTodayForecastAdapter;
    private CurrentWeatherViewModel currentWeatherViewModel;
    private ForecastViewModel forecastViewModel;

    private AppCompatActivity fragmentActivity;

    public TodayForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentActivity = (AppCompatActivity) getActivity();
        if (fragmentActivity != null) {
            forecastViewModel = ViewModelProviders.of(fragmentActivity).get(ForecastViewModel.class);
            currentWeatherViewModel = ViewModelProviders.of(fragmentActivity).get(CurrentWeatherViewModel.class);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.current_weather_layout, container, false);

        dateView = rootView.findViewById(R.id.oneday_date);
        minMaxTempView = rootView.findViewById(R.id.min_max_temp);
        nowTempView = rootView.findViewById(R.id.curr_temp);
        weatherDescriptionView = rootView.findViewById(R.id.curr_weather_description);
        weatherIcon = rootView.findViewById(R.id.weather_icon);
        humidityView = rootView.findViewById(R.id.tv_humidity_val);
        pressureView = rootView.findViewById(R.id.tv_pressure_val);
        windView = rootView.findViewById(R.id.tv_wind_val);
        gistView = rootView.findViewById(R.id.current_weather_gist);

        RecyclerView mHourForecastRecyclerView = rootView.findViewById(R.id.hour_forecast_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragmentActivity, LinearLayoutManager.HORIZONTAL, false);
        mHourForecastRecyclerView.setLayoutManager(linearLayoutManager);
        mHourForecastRecyclerView.setHasFixedSize(true);
        mTodayForecastAdapter = new TodayForecastAdapter(this.getActivity());
        mHourForecastRecyclerView.setAdapter(mTodayForecastAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (currentWeatherViewModel != null) {
            currentWeatherViewModel.getCurrentWeatherLiveData().observe(fragmentActivity, new Observer<CurrentWeather>() {
                @Override
                public void onChanged(@Nullable CurrentWeather currentWeather) {
                    if (currentWeather != null) {
                        dateView.setText(
                                currentWeather.checkIfToday()
                                        ? String.format(getString(R.string.today_date_format), (String) DateFormat.format("h:mm a", currentWeather.getDate()))
                                        : (String) DateFormat.format("d-MM-yyyy, h:mm a", currentWeather.getDate())
                        );

                        nowTempView.setText(ConverterUtil.getTemp(currentWeather.getMain().getTemp()));
                        weatherDescriptionView.setText(currentWeather.getWeatherDescription());
                        setGistView(currentWeather);
                        weatherIcon.setImageResource(WeatherIconUtils.getWeatherIconId(currentWeather.getWeatherIcon()));
                        humidityView.setText(currentWeather.getMain().getStringHumidity());
                        pressureView.setText(currentWeather.getMain().getStringPressure());
                        windView.setText(currentWeather.getWind().getStats());
                    }
                }
            });
        }

        if (forecastViewModel != null) {
            forecastViewModel.getTodayForecast().observe(fragmentActivity, new Observer<List<WeatherEntry>>() {
                @Override
                public void onChanged(@Nullable List<WeatherEntry> weatherEntries) {
                    if (weatherEntries != null && weatherEntries.size() > 0) {
                        presentForecast = weatherEntries.get(0);
                    } else {
                        return;
                    }

                    String minTemp = ConverterUtil.getTemp(presentForecast.getMinTemp());
                    String maxTemp = ConverterUtil.getTemp(presentForecast.getMaxTemp());

                    minMaxTempView.setText(String.format(getString(R.string.min_max_place_holder), maxTemp, minTemp));

                    mTodayForecastAdapter.setTasks(weatherEntries);
                    mTodayForecastAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void setGistView(CurrentWeather currentWeather) {
        if (currentWeather.getMain().getMinTemp() == currentWeather.getMain().getMaxTemp()) {
            gistView.setVisibility(View.GONE);
        } else {
            gistView.setVisibility(View.VISIBLE);
            gistView.setText(String.format(getString(R.string.current_weather_gist),
                    ConverterUtil.getTemp(currentWeather.getMain().getMaxTemp()),
                    ConverterUtil.getTemp(currentWeather.getMain().getMinTemp()))
            );
        }
    }
}
