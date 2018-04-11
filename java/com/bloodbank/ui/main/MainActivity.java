package com.bloodbank.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bloodbank.R;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabLayout = findViewById(R.id.mainTabLayout);
        mViewPager = findViewById(R.id.mainViewPager);

        mViewPager.setAdapter(new MainPagerAdapter(this, getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
