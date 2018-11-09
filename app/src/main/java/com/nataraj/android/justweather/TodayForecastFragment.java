package com.nataraj.android.justweather;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nataraj.android.justweather.ViewModel.CurrentWeatherViewModel;
import com.nataraj.android.justweather.ViewModel.ViewModelFactory;
import com.nataraj.android.justweather.ViewModel.WeatherViewModel;
import com.nataraj.android.justweather.database.AppDatabase;
import com.nataraj.android.justweather.database.WeatherEntry;
import com.nataraj.android.justweather.gson.CurrentWeather;
import com.nataraj.android.justweather.utilities.WeatherIconUtils;

import java.util.List;

/**
 * {@link TodayForecastFragment} is the UI response for showing current weather.
 */
public class TodayForecastFragment extends Fragment {

    private static final String TAG = TodayForecastFragment.class.getSimpleName();

    private WeatherEntry presentForecast;
    private AppDatabase mDb;

    private TextView dateView;
    private TextView minMaxTempView;
    private TextView nowTempView;
    private TextView weatherDescriptionView;
    private ImageView weatherIcon;
    private TextView humidityView;
    private TextView pressureView;
    private TextView windView;

    private RecyclerView mHourForecastRecyclerView;
    private TodayForecastAdapter mTodayForecastAdapter;

    private AppCompatActivity fragmentActivity;

    public TodayForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentActivity = (AppCompatActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.one_day_forecast, container, false);

        dateView = rootView.findViewById(R.id.oneday_date);
        minMaxTempView = rootView.findViewById(R.id.min_max_temp);
        nowTempView = rootView.findViewById(R.id.curr_temp);
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

        WeatherViewModel viewModel = null;
        CurrentWeatherViewModel currentWeatherViewModel = null;

        if (fragmentActivity != null) {
            viewModel = ViewModelProviders.of(fragmentActivity).get(WeatherViewModel.class);
            currentWeatherViewModel = ViewModelProviders.of(fragmentActivity).get(CurrentWeatherViewModel.class);
        }

        if (viewModel != null) {
            viewModel.getTodayForecast().observe(fragmentActivity, new Observer<List<WeatherEntry>>() {
                @Override
                public void onChanged(@Nullable List<WeatherEntry> weatherEntries) {
                    if (weatherEntries != null && weatherEntries.size() > 0) {
                        presentForecast = weatherEntries.get(0);
                    } else {
                        return;
                    }
                    dateView.setText(presentForecast.getDate());

                    String minTemp, maxTemp;

                    SharedPreferences preferences = fragmentActivity.getSharedPreferences(getString(R.string.shared_pref_name), getActivity().MODE_PRIVATE);
                    if (preferences.getString(getString(R.string.units_key), getString(R.string.celcius)).equals(getString(R.string.celcius))) {
                        minTemp = presentForecast.getMinTempC();
                        maxTemp = presentForecast.getMaxTempC();
                    } else {
                        minTemp = presentForecast.getMinTempF();
                        maxTemp = presentForecast.getMaxTempF();
                    }

                    minMaxTempView.setText(
                            "High " + maxTemp + "\u2191 \u22c5 Low " +
                                    minTemp + "\u2193"
                    );
                    nowTempView.setText(maxTemp);
                    weatherDescriptionView.setText(presentForecast.getWeatherDescription());
                    weatherIcon.setImageResource(WeatherIconUtils.getWeatherIconId(presentForecast.getWeatherIcon()));
                    humidityView.setText(String.valueOf(presentForecast.getHumidity()) + "%");
                    pressureView.setText(String.valueOf(presentForecast.getPressure()) + " hPa");
                    windView.setText(String.valueOf(presentForecast.getWindSpeed()) + "km/h" + " " + presentForecast.getWindDirection());

                    mTodayForecastAdapter.setTasks(weatherEntries);
                    mTodayForecastAdapter.notifyDataSetChanged();
                }
            });
        }

        if (currentWeatherViewModel != null) {
            currentWeatherViewModel.currentWeatherLiveData.observe(fragmentActivity, new Observer<CurrentWeather>() {
                @Override
                public void onChanged(@Nullable CurrentWeather currentWeather) {

                }
            });
        }
    }
}
