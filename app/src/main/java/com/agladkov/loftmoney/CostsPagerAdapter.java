package com.agladkov.loftmoney;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class CostsPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mItems;

    public CostsPagerAdapter(List<Fragment> items, @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.mItems = items;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Расходы";
            case 1:
                return "Доходы";
            default:
                return "Баланс";
        }
    }
}
