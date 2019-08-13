package com.example.moneymanager.presentation;

import com.arellomobile.mvp.MvpView;
import com.example.moneymanager.model.AccountModel;
import com.example.moneymanager.model.CategoryModel;

import java.util.List;

public interface FilterActivityView extends MvpView {
    void showToastyMessage(String message);

    void initCategory(List<CategoryModel> categories);

    void initAccount(List<AccountModel> accounts);

    void setSelectedCategory(CategoryModel category);

    void setSelectedAccount(AccountModel account);

    void showDateTimePicker();
}
