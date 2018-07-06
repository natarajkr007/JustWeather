package com.nataraj.android.justweather;

import android.content.Context;
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
        final WeatherEntry hourWeatherEntry = mHourWeatherEntries.get(position);
        holder.hourTempView.setText(Double.toString(hourWeatherEntry.getMaxTemp()) + "\u00b0\u004b");
        holder.hourTimeView.setText(hourWeatherEntry.getTime());
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
