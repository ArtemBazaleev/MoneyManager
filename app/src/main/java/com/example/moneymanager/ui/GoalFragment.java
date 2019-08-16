package com.example.moneymanager.ui;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.moneymanager.App;
import com.example.moneymanager.R;
import com.example.moneymanager.adapters.GoalAdapter;
import com.example.moneymanager.custom.CustomCard;
import com.example.moneymanager.model.GoalModel;
import com.example.moneymanager.presentation.presenter.GoalFragmentPresenter;
import com.example.moneymanager.presentation.view.GoalFragmentView;
import com.example.moneymanager.utils.Utility;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GoalFragment extends MvpAppCompatFragment implements GoalFragmentView {
    @BindView(R.id.recycler_goals) RecyclerView recyclerView;
    @BindView(R.id.new_goal_btn) Button newGoalbtn;
    @BindView(R.id.customCard) CustomCard customCard;

    @InjectPresenter
    GoalFragmentPresenter presenter;

    @ProvidePresenter
    GoalFragmentPresenter providePresenter(){
        App app = (App) Objects.requireNonNull(getActivity()).getApplication();
        return new GoalFragmentPresenter(
                app.getDatabase().dataBaseGoalContract(),
                app.getDatabase().dbGoalTransactionInteraction()
        );
    }

    public GoalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_goal, container, false);
        init(v);
        return v;
    }

    private void init(View v) {
        ButterKnife.bind(this, v);
        newGoalbtn.setOnClickListener(l-> presenter.onNewGoalClicked());
        presenter.init();
    }



    //Mvp methods
    @Override
    public void loadGoals(List<GoalModel> models) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new GoalAdapter(models, getContext(), presenter::onGoalClicked));
    }

    @Override
    public void setTotalBalance(Double totalBalance) {
        customCard.setTotal("\u20BD " + Utility.formatDouble(totalBalance));
    }

    @Override
    public void setToday(Double today) {
        customCard.setDayBalance("\u20BD " + Utility.formatDouble(today));
    }

    @Override
    public void setWeek(Double week) {
        customCard.setWeekBalance("\u20BD " + Utility.formatDouble(week));
    }

    @Override
    public void setMonth(Double month) {
        customCard.setMonthBalance("\u20BD " + Utility.formatDouble(month));
    }

    @Override
    public void startGoalActivity() {
        Intent i = new Intent(getContext(), GoalActivity.class);
        startActivity(i);
    }

    @Override
    public void startGoalViewActivity(int goalId) {

    }
}
