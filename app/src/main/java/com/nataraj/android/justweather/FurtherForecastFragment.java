package com.nataraj.android.justweather;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nataraj.android.justweather.database.AppDatabase;
import com.nataraj.android.justweather.database.WeatherEntry;

import java.util.List;

public class FurtherForecastFragment extends Fragment {

    private static final String TAG = FurtherForecastFragment.class.getSimpleName();

    private RecyclerView mForecastRecyclerView;
    private ForecastAdapter mForecastAdapter;
    private AppDatabase mDb;

    public FurtherForecastFragment() {
        // empty constructor required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: FurtherForecastFragment");

        View rootView = inflater.inflate(R.layout.forecast_recycler_view, container, false);

        //        initiating Forecast Recycler view
        mForecastRecyclerView = rootView.findViewById(R.id.recyclerview_forecast);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        mForecastRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
        mForecastRecyclerView.addItemDecoration(decoration);
        mForecastRecyclerView.setHasFixedSize(true);
        mForecastAdapter = new ForecastAdapter(this.getActivity());
        mForecastRecyclerView.setAdapter(mForecastAdapter);

        mDb = AppDatabase.getsInstance(getActivity().getApplicationContext());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

//        mForecastAdapter.setTasks(mDb.weatherDao().loadForecast());
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                mForecastAdapter.setTasks(mDb.weatherDao().loadForecast());
//                return null;
//            }
//        }.execute();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<WeatherEntry> weatherEntries = mDb.weatherDao().loadForecast();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mForecastAdapter.setTasks(weatherEntries);
                        mForecastAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
