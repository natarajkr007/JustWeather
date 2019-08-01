package com.nataraj.android.justweather;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nataraj.android.justweather.database.WeatherEntry;
import com.nataraj.android.justweather.utilities.ConverterUtil;
import com.nataraj.android.justweather.utilities.WeatherIconUtils;

import java.util.List;

/**
 * Created by nataraj-7085 on 5/7/18.
 */

public class TodayForecastAdapter extends RecyclerView.Adapter<TodayForecastAdapter.WeatherViewHolder> {

    private static final String TAG = ForecastAdapter.class.getSimpleName();

    private List<WeatherEntry> mHourWeatherEntries;
    private Context mContext;

    TodayForecastAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.hour_forecast_list_item, parent, false);

        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        final WeatherEntry hourWeatherEntry = mHourWeatherEntries.get(position);

        String maxTemp = ConverterUtil.getTemp(hourWeatherEntry.getMaxTemp());

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

    void setTasks(List<WeatherEntry> hourWeatherEntries) {
        mHourWeatherEntries = hourWeatherEntries;
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView hourTempView;
        ImageView hourWeatherIcon;
        TextView hourTimeView;

        WeatherViewHolder(View itemView) {
            super(itemView);

            hourTempView = itemView.findViewById(R.id.hour_temp);
            hourWeatherIcon = itemView.findViewById(R.id.hour_weather_icon);
            hourTimeView = itemView.findViewById(R.id.hour_time);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: clicked");
            view.setVisibility(View.GONE);
        }
    }
}
