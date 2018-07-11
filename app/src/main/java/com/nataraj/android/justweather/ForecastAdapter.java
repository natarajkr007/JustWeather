package com.nataraj.android.justweather;

import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.List;

/**
 * Created by nataraj-7085 on 4/7/18.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.WeatherViewHolder> {
    
    private static final String TAG = ForecastAdapter.class.getSimpleName();

    private List<WeatherEntry> mWeatherEntries;
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
    public void onBindViewHolder(WeatherViewHolder holder, final int position) {

        final boolean isExpanded = position == mExpandedPosition;

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

        holder.dateView.setText(weatherEntry.getDate() + " " + weatherEntry.getDecodedTime());
        holder.weatherDescriptionView.setText(weatherEntry.getWeatherDescription());
        holder.maxTempView.setText(maxTemp);
        holder.minTempView.setText(minTemp);
        holder.weatherDescriptionIconView.setImageResource(WeatherIconUtils.getWeatherIconId(weatherEntry.getWeatherIcon()));
        holder.humidityView.setText(String.valueOf(weatherEntry.getHumidity()) + "%");
        holder.pressureView.setText(String.valueOf(weatherEntry.getPressure()) + " hPa");
        holder.windView.setText(String.valueOf(weatherEntry.getWindSpeed()) + "km/h " + weatherEntry.getWindDirection());
        holder.extraDetails.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExpandedPosition = isExpanded ? -1 : position;
                notifyItemChanged(position);
            }
        });
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
        TextView humidityView;
        TextView pressureView;
        TextView windView;

        View extraDetails;

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
        }
    }
}
