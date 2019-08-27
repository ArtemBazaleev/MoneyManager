package com.example.moneymanager.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.example.moneymanager.model.CategoryStatModel;
import com.example.moneymanager.model.StatMonthModel;
import com.example.moneymanager.model.StatPieChartModel;
import com.hadiidbouk.charts.BarData;


import java.util.ArrayList;
import java.util.List;

public interface StatisticsFragmentView extends MvpView {
    void showDatePickerDialog();
    void updateViewPager(List<StatPieChartModel> mPieChartModels);
    void updateBarChart(ArrayList<BarData> charts, float maxVal);
    void updateCategories(List<CategoryStatModel> categoryModels);
    void updateMonthModel(List<StatMonthModel> monthModels);
    void updateAvailableYears(String[] years);
    void updateDate(String from, String to);

    void startMonthStatActivity(StatMonthModel name);
}
