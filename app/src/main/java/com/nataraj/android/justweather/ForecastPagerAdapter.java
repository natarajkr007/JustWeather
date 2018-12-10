package com.nataraj.android.justweather;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by nataraj-7085 on 3/7/18.
 */

public class ForecastPagerAdapter extends FragmentPagerAdapter {

    private static final int mNumTabs = 3;

    private Context mContext;

    ForecastPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TodayForecastFragment();

            case 1:
                return new TomorrowForecastFragment();

            case 2:
                return new FurtherForecastFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.today_tab_title);

            case 1:
                return mContext.getString(R.string.tomorrow_tab_title);

            case 2:
                return mContext.getString(R.string.further_tab_title);

            default:
                return null;
        }
    }
}
