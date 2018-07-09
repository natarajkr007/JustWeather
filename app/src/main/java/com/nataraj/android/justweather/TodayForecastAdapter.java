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
import com.nataraj.android.justweather.utilities.NetworkUtils;
import com.nataraj.android.justweather.utilities.WeatherIconUtils;

import java.util.List;

/**
 * Created by nataraj-7085 on 5/7/18.
 */

public class TodayForecastAdapter extends RecyclerView.Adapter<TodayForecastAdapter.WeatherViewHolder> {

    private static final String TAG = ForecastAdapter.class.getSimpleName();

    private List<WeatherEntry> mHourWeatherEntries;
    private Context mContext;

    public TodayForecastAdapter(Context context) {
        mContext = context;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.hour_forecast_list_item, parent, false);

        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        String minTemp, maxTemp;

        final WeatherEntry hourWeatherEntry = mHourWeatherEntries.get(position);

        SharedPreferences preferences = mContext.getSharedPreferences(mContext.getString(R.string.shared_pref_name), mContext.MODE_PRIVATE);
        if (preferences.getString(mContext.getString(R.string.units_key), mContext.getString(R.string.celcius)).equals(mContext.getString(R.string.celcius))) {
            minTemp = hourWeatherEntry.getMinTempC();
            maxTemp = hourWeatherEntry.getMaxTempC();
        } else {
            minTemp = hourWeatherEntry.getMinTempF();
            maxTemp = hourWeatherEntry.getMaxTempF();
        }

        holder.hourTempView.setText(maxTemp);
        holder.hourTimeView.setText(hourWeatherEntry.getDecodedTime());
        holder.hourWeatherIcon.setImageResource(WeatherIconUtils.getWeatherIconId(hourWeatherEntry.getWeatherIcon()));
    }

    @Override
    public int getItemCount() {
        if (mHourWeatherEntries == null) {
            return 0;
        }
        return mHourWeatherEntries.size();
    }

    public void setTasks(List<WeatherEntry> hourWeatherEntries) {
        mHourWeatherEntries = hourWeatherEntries;
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {

        TextView hourTempView;
        ImageView hourWeatherIcon;
        TextView hourTimeView;

        public WeatherViewHolder(View itemView) {
            super(itemView);

            hourTempView = itemView.findViewById(R.id.hour_temp);
            hourWeatherIcon = itemView.findViewById(R.id.hour_weather_icon);
            hourTimeView = itemView.findViewById(R.id.hour_time);
        }
    }
}
