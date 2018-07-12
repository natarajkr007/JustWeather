package com.nataraj.android.justweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nataraj.android.justweather.database.WeatherEntry;
import com.nataraj.android.justweather.utilities.WeatherIconUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by nataraj-7085 on 4/7/18.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.WeatherViewHolder> {
    
    private static final String TAG = ForecastAdapter.class.getSimpleName();

    private HashMap<String, FurtherForecastFragment.DaySummary> mDaySummaryEntries;
    private String[] mDays;
    private Context mContext;
    private int mExpandedPosition = -1;

    public ForecastAdapter(Context context) {
        mContext = context;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.forecast_list_item, parent, false);

        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WeatherViewHolder holder, final int position) {

        final boolean isExpanded = position == mExpandedPosition;

        FurtherForecastFragment.DaySummary daySummary = mDaySummaryEntries.get(mDays[position]);

        String minTemp, maxTemp;
        SharedPreferences preferences = mContext.getSharedPreferences(mContext.getString(R.string.shared_pref_name), mContext.MODE_PRIVATE);
        if (preferences.getString(mContext.getString(R.string.units_key), mContext.getString(R.string.celcius)).equals(mContext.getString(R.string.celcius))) {
            minTemp = daySummary.minTempC;
            maxTemp = daySummary.maxTempC;
        } else {
            minTemp = daySummary.minTempF;
            maxTemp = daySummary.maxTempF;
        }

        holder.dateView.setText(mDays[position]);
        holder.weatherDescriptionView.setText(daySummary.weatherDescription);
        holder.maxTempView.setText(maxTemp);
        holder.minTempView.setText(minTemp);
        holder.weatherDescriptionIconView.setImageResource(WeatherIconUtils.getWeatherIconId(daySummary.weatherIcon));
        holder.humidityView.setText(String.valueOf(daySummary.humidity) + "%");
        holder.pressureView.setText(String.valueOf(daySummary.pressure) + " hPa");
        holder.windView.setText(String.valueOf(daySummary.windSpeed) + "km/h " + daySummary.windDirection);
        holder.extraDetails.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.hourRecyclerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExpandedPosition = isExpanded ? -1 : position;
                notifyItemChanged(position);
            }
        });

        TodayForecastAdapter todayForecastAdapter = new TodayForecastAdapter(mContext);
        holder.hourRecyclerView.setAdapter(todayForecastAdapter);
        holder.hourRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        holder.hourRecyclerView.setLayoutManager(linearLayoutManager);
        todayForecastAdapter.setTasks(daySummary.hourWeather);
    }

    @Override
    public int getItemCount() {
        if (mDaySummaryEntries == null) {
            return 0;
        }

        return mDaySummaryEntries.size();
    }

    public void setTasks(HashMap<String, FurtherForecastFragment.DaySummary> daySummaryEntries, String[] days) {
        mDaySummaryEntries = daySummaryEntries;
        mDays = days;
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {

        TextView dateView;
        TextView weatherDescriptionView;
        TextView maxTempView;
        TextView minTempView;
        ImageView weatherDescriptionIconView;
        TextView humidityView;
        TextView pressureView;
        TextView windView;

        View extraDetails;
        RecyclerView hourRecyclerView;

        public WeatherViewHolder(View itemView) {
            super(itemView);

            dateView = itemView.findViewById(R.id.date);
            weatherDescriptionView = itemView.findViewById(R.id.weather_description);
            maxTempView = itemView.findViewById(R.id.max_temp);
            minTempView = itemView.findViewById(R.id.min_temp);
            weatherDescriptionIconView = itemView.findViewById(R.id.weather_description_icon);

            humidityView = itemView.findViewById(R.id.tv_humidity_val);
            pressureView = itemView.findViewById(R.id.tv_pressure_val);
            windView = itemView.findViewById(R.id.tv_wind_val);

            extraDetails = itemView.findViewById(R.id.forecast_list_rem_detail);
            hourRecyclerView = itemView.findViewById(R.id.rv_hour_entry);
        }
    }
}