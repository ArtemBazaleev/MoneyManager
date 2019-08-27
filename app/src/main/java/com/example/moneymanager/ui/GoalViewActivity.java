package com.example.moneymanager.ui;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.moneymanager.App;
import com.example.moneymanager.R;
import com.example.moneymanager.adapters.HistoryGoalAdapter;
import com.example.moneymanager.model.HistoryGoalModel;
import com.example.moneymanager.presentation.presenter.GoalViewActivityPresenter;
import com.example.moneymanager.presentation.view.GoalViewActivityView;
import com.example.moneymanager.utils.Utility;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoalViewActivity extends MvpAppCompatActivity implements GoalViewActivityView {
    public static final String GOAL_ID = "goalID";

    @BindView(R.id.recycler_history_goal) RecyclerView recyclerView;
    @BindView(R.id.textView47) TextView balanceTxt;
    @BindView(R.id.textView48) TextView totalBalance;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.textView32) TextView title;
    @BindView(R.id.editText5) EditText sum;
    @BindView(R.id.button) ImageButton addBtn;
    @BindView(R.id.button4) ImageView configure;

    @InjectPresenter
    GoalViewActivityPresenter presenter;

    @ProvidePresenter
    GoalViewActivityPresenter providePresenter(){
        App app = (App) getApplication();
        return new GoalViewActivityPresenter(
                app.getDatabase().dataBaseGoalContract(),
                app.getDatabase().dbGoalTransactionInteraction()
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_view);
        ButterKnife.bind(this);
        if (getIntent().getExtras()!=null)
            presenter.setGoalID(getIntent().getExtras().getInt(GOAL_ID));
        presenter.onCreate();
        init();
    }

    private void init(){
        sum.addTextChangedListener(sumListener);
        addBtn.setOnClickListener(v-> presenter.onAddBtnClicked());
        configure.setOnClickListener(v->presenter.onSettingClicked());
    }

    @Override
    public void loadGoalTransactions(List<HistoryGoalModel> goalModels) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new HistoryGoalAdapter(goalModels, this));
    }

    @Override
    public void setBalance(Double balance) {
        balanceTxt.setText(String.format("\u20BD %s", Utility.formatDouble(balance)));
    }

    @Override
    public void setTotalBalance(Double totalBalance) {
        this.totalBalance.setText(String.format("\u20BD %s", Utility.formatDouble(totalBalance)));
    }

    @Override
    public void setProgress(float progress) {
        progressBar.setProgress((int) progress);
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setSum(String s) {
        sum.removeTextChangedListener(sumListener);
        sum.setText(s);
        sum.addTextChangedListener(sumListener);
    }

    @Override
    public void showNoValueMessage() {
        Toast.makeText(this, "Введите сумму", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startGoalActivityToConfigure(int goalId) {
        Intent i = new Intent(this, GoalActivity.class);
        i.putExtra(GoalActivity.MODE, GoalActivity.MODE_CONFIGURE);
        i.putExtra(GoalActivity.GOALID, goalId);
        startActivity(i);
    }

    private TextWatcher sumListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            presenter.onSumChanged(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
