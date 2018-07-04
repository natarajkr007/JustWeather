package com.nataraj.android.justweather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nataraj.android.justweather.database.AppDatabase;
import com.nataraj.android.justweather.sync.JustWeatherSyncTask;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setting up the navigation drawer
        mDrawerLayout = findViewById(R.id.main_navigation_drawer);

//        instantiating db instance
        mDb = AppDatabase.getsInstance(getApplicationContext());

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setCheckable(false);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );

//        setting up the custom toolbar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

//        setting up the nav bar toggle button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

//        ViewPager setup for horizontal swiping tabs
        ViewPager viewPager = findViewById(R.id.weather_view_pager);
        ForecastPagerAdapter forecastPagerAdapter =
                new ForecastPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(forecastPagerAdapter);

//        tab layout setup
        TabLayout tabLayout = findViewById(R.id.forecast_tabs);
        tabLayout.setupWithViewPager(viewPager);

//        test: to check the data
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                Context context = getApplicationContext();
                JustWeatherSyncTask.syncWeather(context, mDb);
                return null;
            }
        }.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.main_search:
                onSearchRequested();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_view, menu);
        return true;
    }
}
