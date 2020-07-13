package com.agladkov.loftmoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mViewPager = findViewById(R.id.vpCosts);
        mTabLayout = findViewById(R.id.tlCosts);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(CostsFragment.getInstance());
        fragmentList.add(CostsFragment.getInstance());
        fragmentList.add(DiagramFragment.getInstance());
        CostsPagerAdapter costsPagerAdapter = new CostsPagerAdapter(fragmentList, getSupportFragmentManager(), 0);
        mViewPager.setAdapter(costsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}