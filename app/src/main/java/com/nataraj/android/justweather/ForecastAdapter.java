package com.nataraj.android.justweather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nataraj.android.justweather.database.WeatherEntry;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by nataraj-7085 on 4/7/18.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.WeatherViewHolder> {

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
        holder.dateView.setText(weatherEntry.getDate());
        holder.weatherDescriptionView.setText(weatherEntry.getWeatherDescription());
        holder.maxTempView.setText(Double.toString(weatherEntry.getMaxTemp()));
        holder.minTempView.setText(Double.toString(weatherEntry.getMinTemp()));
//        TODO set image resource based on icon code
//        holder.weatherDescriptionIconView.setImageResource();
    }

    @Override
    public int getItemCount() {
        if (mWeatherEntries == null) {
            return 0;
        }

        return mWeatherEntries.size();
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
