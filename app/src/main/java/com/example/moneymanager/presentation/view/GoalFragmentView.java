package com.example.moneymanager.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.example.moneymanager.model.GoalModel;

import java.util.List;

public interface GoalFragmentView extends MvpView {

    void loadGoals(List<GoalModel> models);
    void setTotalBalance(Double totalBalance);
    void setToday(Double today);
    void setWeek(Double week);
    void setMonth(Double month);
    void startGoalActivity();
    void startGoalViewActivity(int goalId);
}
