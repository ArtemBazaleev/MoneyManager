package com.example.moneymanager.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.example.moneymanager.model.HistoryGoalModel;

import java.util.List;

public interface GoalViewActivityView extends MvpView {
    void loadGoalTransactions(List<HistoryGoalModel> goalModels);
    void setBalance(Double balance);
    void setTotalBalance(Double totalBalance);
    void setProgress(float progress);
    void setTitle(String title);
    void setSum(String s);
    void showNoValueMessage();
    void startGoalActivityToConfigure(int goalId);
}
