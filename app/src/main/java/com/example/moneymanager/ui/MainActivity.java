package com.example.moneymanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import com.example.moneymanager.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.navigation) BottomNavigationViewEx bnv;
    @BindView(R.id.floating_button) FloatingActionButton addBtn;
    Fragment historyFragment;
    Fragment goalFragment;
    Fragment statisticsFragment;
    Fragment currentFragment;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bnv.enableShiftingMode(false);
        bnv.enableAnimation(false);
        bnv.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm = getSupportFragmentManager();
        addBtn.setOnClickListener(l-> startActivity(new Intent(this, EventActivity.class)));

        initFragments();
    }

    private void initFragments() {
        historyFragment = new HistoryFragment();
        goalFragment = new GoalFragment();
        statisticsFragment = new StatisticsFragment();
        currentFragment = historyFragment;

        fm.beginTransaction()
                .add(R.id.fragment_container, historyFragment,"1")
                .commit();
        fm.beginTransaction()
                .add(R.id.fragment_container, goalFragment, "2")
                .hide(goalFragment)
                .commit();
        fm.beginTransaction()
                .add(R.id.fragment_container, statisticsFragment, "3")
                .hide(statisticsFragment)
                .commit();
        setColoredBNV(0);

    }

    private void setColoredBNV(int position){
        for (int i = 0; i<5; i++){
            if (i == position){
                bnv.setIconTintList(position, ColorStateList.valueOf(getResources().getColor(R.color.colorGradientStart)));
                bnv.setTextTintList(position,ColorStateList.valueOf(getResources().getColor(R.color.colorGradientStart)));
            }
            else{
                bnv.setIconTintList(i,ColorStateList.valueOf(getResources().getColor(R.color.black_overlay_bnv)));
                bnv.setTextTintList(i,ColorStateList.valueOf(getResources().getColor(R.color.black_overlay_bnv)));
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_events:
                fm.beginTransaction()
                        .hide(currentFragment)
                        .show(historyFragment)
                        .commit();
                currentFragment = historyFragment;
                setColoredBNV(0);
                return true;
            case R.id.navigation_goals:
                fm.beginTransaction()
                        .hide(currentFragment)
                        .show(goalFragment)
                        .commit();
                currentFragment = goalFragment;
                setColoredBNV(1);
                return true;
            case R.id.navigation_main:
                //setColoredBNV(2);
                return false;
            case R.id.navigation_statistics:
                fm.beginTransaction()
                        .hide(currentFragment)
                        .show(statisticsFragment)
                        .commit();
                currentFragment = statisticsFragment;
                setColoredBNV(3);
                return true;
            case R.id.navigation_more:
                setColoredBNV(4);
                return true;
        }
        return false;
    };
}
