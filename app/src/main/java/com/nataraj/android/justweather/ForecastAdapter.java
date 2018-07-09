package com.nataraj.android.justweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nataraj.android.justweather.database.WeatherEntry;
import com.nataraj.android.justweather.utilities.WeatherIconUtils;

import java.util.List;

/**
 * Created by nataraj-7085 on 4/7/18.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.WeatherViewHolder> {
    
    private static final String TAG = ForecastAdapter.class.getSimpleName();

    private List<WeatherEntry> mWeatherEntries;
    private Context mContext;

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
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        WeatherEntry weatherEntry = mWeatherEntries.get(position);

        String minTemp, maxTemp;
        SharedPreferences preferences = mContext.getSharedPreferences(mContext.getString(R.string.shared_pref_name), mContext.MODE_PRIVATE);
        if (preferences.getString(mContext.getString(R.string.units_key), mContext.getString(R.string.celcius)).equals(mContext.getString(R.string.celcius))) {
            minTemp = weatherEntry.getMinTempC();
            maxTemp = weatherEntry.getMaxTempC();
        } else {
            minTemp = weatherEntry.getMinTempF();
            maxTemp = weatherEntry.getMaxTempF();
        }

        holder.dateView.setText(weatherEntry.getDate());
        holder.weatherDescriptionView.setText(weatherEntry.getWeatherDescription());
        holder.maxTempView.setText(maxTemp);
        holder.minTempView.setText(minTemp);
        holder.weatherDescriptionIconView.setImageResource(WeatherIconUtils.getWeatherIconId(weatherEntry.getWeatherIcon()));
    }

    @Override
    public int getItemCount() {
        if (mWeatherEntries == null) {
            return 0;
        }

        return mWeatherEntries.size();
    }

    public void setTasks(List<WeatherEntry> weatherEntries) {
        mWeatherEntries = weatherEntries;
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {

        TextView dateView;
        TextView weatherDescriptionView;
        TextView maxTempView;
        TextView minTempView;
        ImageView weatherDescriptionIconView;

        public WeatherViewHolder(View itemView) {
            super(itemView);

            dateView = itemView.findViewById(R.id.date);
            weatherDescriptionView = itemView.findViewById(R.id.weather_description);
            maxTempView = itemView.findViewById(R.id.max_temp);
            minTempView = itemView.findViewById(R.id.min_temp);
            weatherDescriptionIconView = itemView.findViewById(R.id.weather_description_icon);
        }
    }
}
