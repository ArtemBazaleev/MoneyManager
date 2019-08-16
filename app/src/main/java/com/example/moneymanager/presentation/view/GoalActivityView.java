package com.example.moneymanager.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.example.moneymanager.model.IconModel;


public interface GoalActivityView extends MvpView {
    void showBottomSheetIcon();
    void onIconChosen(IconModel icon);
    void setSum(String sum);

    void stopSelf();

    void showMessageNoGoalName();

    void showMessageNoGoalTotalSum();
}
