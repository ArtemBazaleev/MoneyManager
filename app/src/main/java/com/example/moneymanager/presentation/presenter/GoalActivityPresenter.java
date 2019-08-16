package com.example.moneymanager.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.moneymanager.interfaces.db.DataBaseGoalContract;
import com.example.moneymanager.interfaces.db.DbIconInteraction;
import com.example.moneymanager.model.IconModel;
import com.example.moneymanager.model.dbModel.DbGoal;
import com.example.moneymanager.presentation.view.GoalActivityView;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class GoalActivityPresenter extends MvpPresenter<GoalActivityView> {
    private DataBaseGoalContract contract;
    private DbIconInteraction iconInteraction;
    private DbGoal goal;

    public GoalActivityPresenter(DataBaseGoalContract contract, DbIconInteraction iconInteraction) {
        this.contract = contract;
        this.iconInteraction = iconInteraction;
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
        if(check())
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
        requestIcons();
    }

    private void requestIcons() {
        Disposable d =iconInteraction.getAllIcons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dbIcons -> {
                    getViewState().onIconChosen(new IconModel(dbIcons.get(0)));
                },Throwable::printStackTrace);
    }
}
