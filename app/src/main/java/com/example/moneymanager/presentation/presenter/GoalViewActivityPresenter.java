package com.example.moneymanager.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.moneymanager.interfaces.db.DataBaseGoalContract;
import com.example.moneymanager.interfaces.db.DbGoalTransactionInteraction;
import com.example.moneymanager.model.HistoryGoalModel;
import com.example.moneymanager.model.dbModel.DbGoal;
import com.example.moneymanager.model.dbModel.DbGoalTransaction;
import com.example.moneymanager.presentation.view.GoalViewActivityView;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class GoalViewActivityPresenter extends MvpPresenter<GoalViewActivityView> {
    private DataBaseGoalContract goalContract;
    private DbGoalTransactionInteraction goalTransactions;
    private int goalID;
    private DbGoal goal;
    private double sum;

    public GoalViewActivityPresenter(DataBaseGoalContract goalContract, DbGoalTransactionInteraction goalTransactions) {
        this.goalContract = goalContract;
        this.goalTransactions = goalTransactions;
    }

    public void setGoalID(int goalID) {
        this.goalID = goalID;
    }

    public void onCreate() {
        requestGoal();
        requestTransactions();
    }

    private void requestGoal() {
        Disposable d = goalContract.getGoalByID(goalID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(goalModel -> {
                    goal = goalModel;
                    initView();
                },Throwable::printStackTrace);
    }

    private void initView() {
        getViewState().setBalance(goal.balance);
        getViewState().setProgress((float) (goal.balance/ goal.sumTotal) * 100);
        getViewState().setTitle(goal.goalName);
        getViewState().setTotalBalance(goal.sumTotal);
    }

    private void requestTransactions() {
        Disposable d = goalTransactions.getTransactionForGoalID(goalID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dbGoalTransactions -> {
                    List<HistoryGoalModel > goalModels = new LinkedList<>();
                    for (DbGoalTransaction i:dbGoalTransactions)
                        goalModels.add(new HistoryGoalModel(i));
                    if (goalModels.size() != 0)
                        getViewState().loadGoalTransactions(goalModels);
                }, Throwable::printStackTrace);
    }

    public void onSumChanged(String sum) {
        try {
            this.sum = Double.parseDouble(sum);
        }catch (Exception e){
            e.printStackTrace();
            getViewState().setSum("");
            this.sum = 0.0;
        }
    }

    public void onAddBtnClicked() {
        if (sum == 0.0)
            getViewState().showNoValueMessage();
        else {
            DbGoalTransaction goalTransaction = new DbGoalTransaction();
            goalTransaction.goalID = goal;
            goalTransaction.sum = sum;
            goalTransaction.goalTransactionDate = Calendar.getInstance().getTime().getTime();

            Completable.fromAction(() -> goalTransactions
                    .addGoalTransaction(goalTransaction))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onComplete() {
                            updateGoal();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }
                    });

        }
    }

    private void updateGoal() {
        goal.balance += sum ;
        sum = 0.0;
        getViewState().setSum("");
        Completable.fromAction(() -> goalContract
                .updateGoal(goal))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        onCreate();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }
}
