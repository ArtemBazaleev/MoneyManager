package com.example.moneymanager.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.moneymanager.ui.StatisticsFragment;

public class StatisticsContainerAdapter extends FragmentStatePagerAdapter {


    private int tabsCount;
    public StatisticsContainerAdapter(FragmentManager fm, int count) {
        super(fm);
        tabsCount = count;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return StatisticsFragment.newInstance(StatisticsFragment.MODE_OUTCOME);
            case 1:
                return StatisticsFragment.newInstance(StatisticsFragment.MODE_INCOME);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabsCount;
    }

}
