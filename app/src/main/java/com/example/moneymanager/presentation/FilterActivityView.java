package com.example.moneymanager.presentation;

import com.arellomobile.mvp.MvpView;
import com.example.moneymanager.model.AccountModel;
import com.example.moneymanager.model.CategoryModel;

import java.util.List;

public interface FilterActivityView extends MvpView {
    void showToastyMessage(String message);

    void initAccount(List<AccountModel> accounts);

    void setSelectedAccount(AccountModel account);

    void showDateTimePicker();

    void showCategories(String mode);

    void setEnabledCategory(boolean enabled);

    void setCategory(CategoryModel model);

    void setFromToDate(String from, String to);

    void clearCheck();

    void setEnabledDateLayout(boolean enabledDateLayout);
}
