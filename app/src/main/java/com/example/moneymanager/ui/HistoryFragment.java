package com.example.moneymanager.ui;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.moneymanager.App;
import com.example.moneymanager.R;
import com.example.moneymanager.adapters.HistoryAdapter;
import com.example.moneymanager.model.HistoryModel;
import com.example.moneymanager.presentation.presenter.HistoryFragmentPresenter;
import com.example.moneymanager.presentation.view.HistoryFragmentView;
import com.example.moneymanager.utils.Utility;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryFragment extends MvpAppCompatFragment implements HistoryFragmentView {

    @BindView(R.id.recycler_history) RecyclerView recyclerHistory;
    @BindView(R.id.filter_btn) ImageView filterBtn;
    @BindView(R.id.textView24) TextView totalBalance;
    @BindView(R.id.textView26) TextView income;
    @BindView(R.id.history_outcome) TextView outcome;

    @InjectPresenter
    HistoryFragmentPresenter presenter;

    @ProvidePresenter
    HistoryFragmentPresenter providePresenter(){
        App app = (App) Objects.requireNonNull(getActivity()).getApplicationContext();
        return new HistoryFragmentPresenter(app.getDatabase().dataBaseTransactionContract());
    }

    public HistoryFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, v);
        init();
        presenter.onCreate();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        App app = (App) Objects.requireNonNull(getActivity()).getApplication();
        presenter.onStart(app.getFilterHistory());
    }

    private void init() {
        filterBtn.setOnClickListener(l-> startActivity(new Intent(getContext(), FilterActivity.class)));
    }

    private void onHistoryItemClicked() {

    }

    @Override
    public void showToastyMessage(String message) {

    }

    @Override
    public void loadTransactions(List<HistoryModel> transactions) {
        HistoryAdapter adapter = new HistoryAdapter(getContext(), transactions, this::onHistoryItemClicked);
        recyclerHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerHistory.setAdapter(adapter);
    }

    @Override
    public void setTotalBalance(double balance) {
        totalBalance.setText(String.format("\u20BD %s", Utility.formatDouble(balance)));
    }

    @Override
    public void setIncome(double income) {
        this.income.setText(String.format("\u20BD %s", Utility.formatDouble(income)));
    }

    @Override
    public void setOutCome(double outcome) {
        this.outcome.setText(String.format("\u20BD %s", Utility.formatDouble(outcome)));
    }
}
