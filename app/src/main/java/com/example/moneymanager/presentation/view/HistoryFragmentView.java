package com.example.moneymanager.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.example.moneymanager.model.HistoryModel;

import java.util.List;

public interface HistoryFragmentView extends MvpView {
    void showToastyMessage(String message);

    void loadTransactions(List<HistoryModel> transactions);

    void setTotalBalance(double balance);

    void setIncome(double income);

    void setOutCome(double outcome);
}
