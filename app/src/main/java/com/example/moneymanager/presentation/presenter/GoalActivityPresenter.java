package com.example.moneymanager.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.moneymanager.interfaces.db.DataBaseGoalContract;
import com.example.moneymanager.interfaces.db.DbGoalTransactionInteraction;
import com.example.moneymanager.interfaces.db.DbIconInteraction;
import com.example.moneymanager.model.IconModel;
import com.example.moneymanager.model.dbModel.DbGoal;
import com.example.moneymanager.presentation.view.GoalActivityView;
import com.example.moneymanager.ui.GoalActivity;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class GoalActivityPresenter extends MvpPresenter<GoalActivityView> {
    private DataBaseGoalContract contract;
    private DbIconInteraction iconInteraction;
    private DbGoalTransactionInteraction transactionInteraction;
    private DbGoal goal;
    private String mode;
    private Integer goalID;

    public GoalActivityPresenter(DataBaseGoalContract contract,
                                 DbIconInteraction iconInteraction,
                                 DbGoalTransactionInteraction transactionInteraction) {
        this.contract = contract;
        this.iconInteraction = iconInteraction;
        this.transactionInteraction = transactionInteraction;
        goal = new DbGoal();
        goal.goalName = "";
        goal.note = "";
        goal.balance = 0.0;
        goal.isActive = true;
        goal.sumTotal = 0.0;

    }

    public void onChoseIconClicked(){
        getViewState().showBottomSheetIcon();
    }

    public void onSaveClicked() {
        if (mode.equals(GoalActivity.MODE_NEW)) {
            if (check())
                Completable.fromAction(() -> contract
                        .addTransaction(goal))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onComplete() {
                                getViewState().stopSelf();
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }
                        });
        }else {
            if (check())
                Completable.fromAction(() -> contract
                        .updateGoal(goal))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onComplete() {
                                getViewState().stopSelf();
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }
                        });

        }
    }

    private boolean check() {
        if (goal.goalName.equals("")) {
            getViewState().showMessageNoGoalName();
            return false;
        }
        if (goal.sumTotal == 0.0){
            getViewState().showMessageNoGoalTotalSum();
            return false;
        }
        return true;
    }


    public void onIconChosen(IconModel iconModel) {
        getViewState().onIconChosen(iconModel);
        goal.iconID = iconModel.getDbIcon();
    }

    public void onGoalNameChanged(String name) {
        if (name!=null)
            goal.goalName = name;
        else goal.goalName = "";
    }

    public void onGoalNoteChanged(String note) {
        if (note!=null)
            goal.note = note;
        else goal.note = "";
    }

    public void onSumChanged(String formattedString) {
        try {
            goal.sumTotal = Double.parseDouble(formattedString);
        }catch (Exception e){
            e.printStackTrace();
            getViewState().setSum("");
            goal.sumTotal = 0.0;
        }
    }

    public void onCreate() {
        if (mode.equals(GoalActivity.MODE_NEW))
            requestIcons();
        else
            requestDbIcon();
    }

    private void requestDbIcon() {
        Disposable d = contract.getGoalByID(goalID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe(requestedGoal->{
                    goal = requestedGoal;
                    initView();
                }, Throwable::printStackTrace);
    }

    private void initView() {
        getViewState().onIconChosen(new IconModel(goal.iconID));
        getViewState().setSum(String.valueOf(goal.sumTotal));
        getViewState().setName(goal.goalName);
        getViewState().setNote(goal.note);
    }

    private void requestIcons() {
        Disposable d =iconInteraction.getAllIcons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe(dbIcons -> {
                    goal.iconID = dbIcons.get(0);
                    getViewState().onIconChosen(new IconModel(dbIcons.get(0)));
                },Throwable::printStackTrace);
    }

    public void setMode(String mode, int goalID) {
        if (mode.equals(GoalActivity.MODE_CONFIGURE)) {
            this.mode = mode;
            this.goalID = goalID;
            getViewState().setEnabledDelBtn(true);
        }
        else {
            this.mode = mode;
            this.goalID = null;
        }
    }

    public void onDelBtnClicked() {
        Completable.fromAction(() -> transactionInteraction
                .deleteGoalTransactions(goal.goalId))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onComplete() {
                        Completable.fromAction(() -> contract
                                .deleteTransaction(goal))
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                    }

                                    @Override
                                    public void onComplete() {
                                        getViewState().startMainActivityAndClearStack();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }
}
