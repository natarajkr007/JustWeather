package com.nataraj.android.justweather;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FurtherForecastFragment extends Fragment {

    private RecyclerView mForecastRecyclerView;
    private ForecastAdapter mForecastAdapter;

    public FurtherForecastFragment() {
        // empty constructor required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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

        return rootView;
    }
}
