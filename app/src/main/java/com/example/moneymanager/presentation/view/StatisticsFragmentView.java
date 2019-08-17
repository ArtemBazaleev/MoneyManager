package com.example.moneymanager.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.example.moneymanager.model.CategoryModel;
import com.example.moneymanager.model.StatMonthModel;
import com.github.mikephil.charting.charts.BarChart;

import java.util.List;

public interface StatisticsFragmentView extends MvpView {
    void showDatePickerDialog();
    void updateViewPager(List<CategoryModel> income, List<CategoryModel> outcome);
    void updateBarChart(List<BarChart> charts);
    void updateCategories(List<CategoryModel> categoryModels);
    void updateMonthModel(List<StatMonthModel> monthModels);
    void updateAvailableYears(String[] years);
}
