package com.example.moneymanager.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.example.moneymanager.model.IconModel;

public interface CategoryAddActivityView extends MvpView {
    void onIconChosen(IconModel icon);
    void onNameChosen(String name);
    void changeTitle(String title);
    void showBottomSheetAccount();
    void showBottomSheetCategory();
    void showBottomSheetIcon();

    void setVisibleCategory(boolean b);

    void stopSelf();

    void showErrorEmptyName();
}
