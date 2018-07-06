package com.nataraj.android.justweather;


import android.os.Bundle;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayForecastFragment extends Fragment {

    private static final String TAG = TodayForecastFragment.class.getSimpleName();

    private List<WeatherEntry> weatherEntries;
    private WeatherEntry presentForecast;
    private AppDatabase mDb;

    private TextView dateView;
    private TextView minMaxTempView;
    private TextView nowTempView;
    private TextView weatherDescriptionView;
    private ImageView weatherIcon;

    private RecyclerView mHourForecastRecyclerView;
    private TodayForecastAdapter mTodayForecastAdapter;

    public TodayForecastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: TodayForecastFragment");
        View rootView = inflater.inflate(R.layout.one_day_forecast, container, false);

        dateView = rootView.findViewById(R.id.oneday_date);
        minMaxTempView = rootView.findViewById(R.id.min_max_temp);
        nowTempView = rootView.findViewById(R.id.curr_temp);
        weatherDescriptionView = rootView.findViewById(R.id.curr_weather_description);
        weatherIcon = rootView.findViewById(R.id.weather_icon);

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

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                weatherEntries = mDb.weatherDao().loadForecastByDate("Today");
                presentForecast = weatherEntries.get(0);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dateView.setText(presentForecast.getDate());
                        minMaxTempView.setText(
                                "High " + Double.toString(presentForecast.getMaxTemp()) + "\u00b0\u2191 \u22c5 Low " +
                                        Double.toString(presentForecast.getMinTemp()) + "\u00b0\u2193"
                        );
                        nowTempView.setText(Double.toString(presentForecast.getMaxTemp()) + "\u00b0\u004b");
                        weatherDescriptionView.setText(presentForecast.getWeatherDescription());
//                        TODO put weather icon based on weather desc.
//                        weatherIcon.setImageResource();

                        mTodayForecastAdapter.setTasks(weatherEntries);
                        mTodayForecastAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
