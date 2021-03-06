package com.nataraj.android.justweather;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nataraj.android.justweather.ViewModel.CurrentWeatherViewModel;
import com.nataraj.android.justweather.ViewModel.ForecastViewModel;
import com.nataraj.android.justweather.ViewModel.ViewModelFactory;
import com.nataraj.android.justweather.database.AppDatabase;
import com.nataraj.android.justweather.utilities.AppDelegate;
import com.nataraj.android.justweather.utilities.Config;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DrawerLayout mDrawerLayout;
    private AppDatabase mDb;

    private TextView cityTextView;
    private ViewPager mViewPager;
    private ProgressBar mProgressBar;
    private TextView mWarnTextView;

    private SharedPreferences prefs;
    private SharedPreferences.Editor prefEdit;

    private CurrentWeatherViewModel currentWeatherViewModel;
    private ForecastViewModel forecastViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatActivity reference = this;
//        setting up the swipe to refresh
        mSwipeRefreshLayout = findViewById(R.id.main_swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
//        setting up the navigation drawer
        mDrawerLayout = findViewById(R.id.main_navigation_drawer);

//        instantiating db instance
        mDb = AppDatabase.getsInstance(getApplicationContext());

        prefs = getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE);
        if (!prefs.contains(getString(R.string.city_name))) {
            setPrefs(getString(R.string.def_city));
        }
        final String init_city = prefs.getString(getString(R.string.city_name), getString(R.string.def_city));

        cityTextView = findViewById(R.id.tv_city_name);
        cityTextView.setText(init_city);
        mProgressBar = findViewById(R.id.progress_bar);
        mViewPager = findViewById(R.id.weather_view_pager);
        mWarnTextView = findViewById(R.id.tv_warn);

        ViewModelFactory currentWeatherViewModelFactory = new ViewModelFactory(init_city, Config.getDefaultCountry());
        currentWeatherViewModel = ViewModelProviders.of(reference, currentWeatherViewModelFactory).get(CurrentWeatherViewModel.class);

        ViewModelFactory forecastViewModelFactory = new ViewModelFactory(getApplication(), init_city, Config.getDefaultCountry());
        forecastViewModel = ViewModelProviders.of(reference, forecastViewModelFactory).get(ForecastViewModel.class);

        forecastViewModel.getStatus().observe(reference, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean res) {
                if (res != null) {
                    setViewPager();
                    if (res) {
                        mProgressBar.setVisibility(View.GONE);
                        mViewPager.setVisibility(View.VISIBLE);
                    } else {
                        mProgressBar.setVisibility(View.GONE);
                        mWarnTextView.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "City not found", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Please, change the city", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        if (AppDelegate.appDelegate.isCelsius()) {
            navigationView.setCheckedItem(R.id.pref_celcius);
        } else {
            navigationView.setCheckedItem(R.id.pref_farenheit);
        }
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (item.getItemId() == R.id.pref_celcius) {
                            setPrefUnit(getString(R.string.celcius));
                        } else {
                            setPrefUnit(getString(R.string.farenheit));
                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );

//        setting up the custom toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

//        setting up the nav bar toggle button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        }
    }

    private void setViewPager() {
//        ViewPager setup for horizontal swiping tabs
        ForecastPagerAdapter forecastPagerAdapter =
                new ForecastPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(forecastPagerAdapter);

//        tab layout setup
        final TabLayout tabLayout = findViewById(R.id.forecast_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled( int position, float v, int i1 ) {
            }

            @Override
            public void onPageSelected( int position ) {
            }

            @Override
            public void onPageScrollStateChanged( int state ) {
                enableDisableSwipeRefresh( state == ViewPager.SCROLL_STATE_IDLE );
            }
        });
    }

    private void enableDisableSwipeRefresh(boolean enable) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnabled(enable);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setPrefs(String cityName) {
        prefEdit = getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE)
                .edit();
        prefEdit.putString(getString(R.string.city_name), cityName);
        prefEdit.apply();
    }

    private void setPrefUnit(String prefUnit) {
        prefEdit = getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE)
                .edit();
        prefEdit.putString(getString(R.string.units_key), prefUnit);
        prefEdit.apply();
        setViewPager();
    }

    public void setCity(final String location) {
        mProgressBar.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
        deleteData();
        cityTextView.setText(location);
        setPrefs(location);

        currentWeatherViewModel.loadCurrentWeather(location, null);
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                final Context context = getApplicationContext();
                final boolean res = forecastViewModel.fetchData(location, null);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (res) {
//                            setViewPager(); used ViewModel to update changed data
                            mProgressBar.setVisibility(View.GONE);
                            mViewPager.setVisibility(View.VISIBLE);
                        } else {
                            mProgressBar.setVisibility(View.GONE);
                            mWarnTextView.setVisibility(View.VISIBLE);
                            Toast.makeText(context, "City not found", Toast.LENGTH_SHORT).show();
                            Toast.makeText(context, "Please, change the city", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_view, menu);

        final MenuItem menuItem = menu.findItem(R.id.main_search);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setCity(query);
                menuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    protected void deleteData() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.weatherDao().nukeData();
            }
        });
    }

    @Override
    public void onRefresh() {
        prefs = getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE);
        String city = prefs.getString(getString(R.string.city_name), getString(R.string.def_city));
        mSwipeRefreshLayout.setRefreshing(false);
        setCity(city);
    }
}
