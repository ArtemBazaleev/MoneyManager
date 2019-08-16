package com.example.moneymanager.presentation.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.moneymanager.interfaces.db.DataBaseGoalContract;
import com.example.moneymanager.interfaces.db.DbGoalTransactionInteraction;
import com.example.moneymanager.model.GoalModel;
import com.example.moneymanager.model.dbModel.DbGoal;
import com.example.moneymanager.model.dbModel.DbGoalTransaction;
import com.example.moneymanager.presentation.view.GoalFragmentView;


import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class GoalFragmentPresenter extends MvpPresenter<GoalFragmentView> {
    private DataBaseGoalContract goalContract;
    private DbGoalTransactionInteraction transactionInteraction;

    public GoalFragmentPresenter(DataBaseGoalContract goalContract , DbGoalTransactionInteraction t) {
        this.goalContract = goalContract;
        transactionInteraction = t;
    }

    public void onNewGoalClicked() {
        getViewState().startGoalActivity();
    }

    public void onGoalClicked(GoalModel goalModel) {
        getViewState().startGoalViewActivity(goalModel.getGoal().goalId);
    }

    public void init() {
        Disposable d = goalContract.getAllGoals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dbGoals -> {
                    List<GoalModel> models = new LinkedList<>();
                    double sum = 0.0;
                    for (DbGoal i:dbGoals) {
                        sum+=i.balance;
                        models.add(new GoalModel(i));
                    }
                    getViewState().loadGoals(models);
                    getViewState().setTotalBalance(sum);
                },Throwable::printStackTrace);

        Calendar calendarToday = Calendar.getInstance();
        calendarToday.set(Calendar.HOUR_OF_DAY, 0);
        calendarToday.set(Calendar.MINUTE, 0);
        calendarToday.set(Calendar.SECOND, 0);
        requestDay(calendarToday.getTime().getTime());

        Calendar calendarMonth = Calendar.getInstance();
        calendarMonth.set(Calendar.HOUR_OF_DAY, 0);
        calendarMonth.set(Calendar.MINUTE, 0);
        calendarMonth.set(Calendar.SECOND, 0);
        calendarMonth.set(Calendar.DAY_OF_MONTH, 1);
        requestMonth(calendarMonth.getTime().getTime());

        Calendar calendarWeek = Calendar.getInstance();
        calendarWeek.set(Calendar.HOUR_OF_DAY, 0);
        calendarWeek.set(Calendar.MINUTE, 0);
        calendarWeek.set(Calendar.SECOND, 0);
        calendarWeek.set(Calendar.DAY_OF_WEEK, 2);
        requestWeek(calendarWeek.getTime().getTime());

    }

    private void requestDay(Long date){
        Disposable d = transactionInteraction.getTimeTransaction(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dbGoalTransactions -> {
                            double sum = 0.0;
                            for (DbGoalTransaction i:dbGoalTransactions)
                                sum+=i.sum;
                            getViewState().setToday(sum);
                        },
                        Throwable::printStackTrace);
    }

    private void requestWeek(Long date){
        Disposable d = transactionInteraction.getTimeTransaction(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dbGoalTransactions -> {
                            double sum = 0.0;
                            for (DbGoalTransaction i:dbGoalTransactions)
                                sum+=i.sum;
                            getViewState().setWeek(sum);
                        },
                        Throwable::printStackTrace);
    }

    private void requestMonth(Long date){
        Disposable d = transactionInteraction.getTimeTransaction(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dbGoalTransactions -> {
                            double sum = 0.0;
                            for (DbGoalTransaction i:dbGoalTransactions)
                                sum+=i.sum;
                            getViewState().setMonth(sum);
                        },
                        Throwable::printStackTrace);
    }


}
