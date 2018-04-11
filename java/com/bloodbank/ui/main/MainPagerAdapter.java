package com.bloodbank.ui.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bloodbank.R;
import com.bloodbank.ui.register.RegisterFragment;
import com.bloodbank.ui.search.SearchFragment;
import com.bloodbank.ui.stats.StatisticsFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private StatisticsFragment statisticsFragment;
    private RegisterFragment registerFragment;
    private SearchFragment searchFragment;

    public MainPagerAdapter(Context mContext, FragmentManager fm) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                statisticsFragment = new StatisticsFragment();
                statisticsFragment.setPagerAdapter(this);
                return statisticsFragment;
            case 1:
                registerFragment = new RegisterFragment();
                registerFragment.setPagerAdapter(this);
                return registerFragment;
            case 2:
                searchFragment = new SearchFragment();
                searchFragment.setPagerAdapter(this);
                return searchFragment;
        }
        return null;
    }

    @Nullable
    public StatisticsFragment getStatisticsFragment() {
        return statisticsFragment;
    }

    @Nullable
    public RegisterFragment getRegisterFragment() {
        return registerFragment;
    }

    @Nullable
    public SearchFragment getSearchFragment() {
        return searchFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.main_page_title_0);
            case 1:
                return mContext.getString(R.string.main_page_title_1);
            case 2:
                return mContext.getString(R.string.main_page_title_2);
        }
        return null;
    }
}
