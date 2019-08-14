package com.example.moneymanager.presentation.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.moneymanager.interfaces.db.DbAccountInteraction;
import com.example.moneymanager.interfaces.db.DbCategoryInteraction;
import com.example.moneymanager.model.AccountModel;
import com.example.moneymanager.model.CategoryModel;
import com.example.moneymanager.model.dbModel.DbAccount;
import com.example.moneymanager.model.dbModel.DbCategory;
import com.example.moneymanager.model.dbModel.DbTransaction;
import com.example.moneymanager.presentation.view.EventActivityView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class EventActivityPresenter extends MvpPresenter<EventActivityView> {
    public static final int MODE_INCOME = 0;
    public static final int MODE_OUTCOME = 1;
    private String TAG = "EventActivityPresenter";

    private DbCategoryInteraction categoryInteraction;
    private DbAccountInteraction accountInteraction;
    private DbTransaction transaction;
    private int mode = MODE_OUTCOME;

    public EventActivityPresenter(DbCategoryInteraction categoryInteraction, DbAccountInteraction accountInteraction) {
        this.categoryInteraction = categoryInteraction;
        this.accountInteraction = accountInteraction;
        transaction = new DbTransaction();
        transaction.note = "";
        transaction.sum = 0.0;
    }

    public void initBottomSheet(){
        Log.d(TAG, "onCreate: called");
        requestAccounts();
        requestCategories();
    }

    private void requestAccounts() {
        Log.d(TAG, "requestAccounts: called");
        Disposable d = accountInteraction.getAllAccounts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(accounts -> {
                    List<AccountModel> models = new ArrayList<>();
                    for (DbAccount i:accounts)
                        models.add(new AccountModel(i));
                    getViewState().loadAccounts(models);
                }, throwable -> {

                });
    }

    private void requestCategories() {
        Log.d(TAG, "requestCategories: called");
        switch (mode){
            case MODE_INCOME:
                requestIncomeCategories();
                break;
            case MODE_OUTCOME:
                requestOutcomeCategories();
                break;
        }
    }

    private void requestOutcomeCategories() {
        Log.d(TAG, "requestOutcomeCategories: called");
        Disposable d = categoryInteraction.getAllOutComeCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categories -> {
                    List<CategoryModel> models = new ArrayList<>();
                    for (DbCategory i:categories)
                        models.add(new CategoryModel(i));
                    getViewState().loadCategories(models);
                }, Throwable::printStackTrace);
    }

    private void requestIncomeCategories() {
        Log.d(TAG, "requestIncomeCategories: called");
        Disposable d = categoryInteraction.getAllInComeCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categories -> {
                    List<CategoryModel> models = new ArrayList<>();
                    for (DbCategory i:categories)
                        models.add(new CategoryModel(i));
                    getViewState().loadCategories(models);
                }, Throwable::printStackTrace);
    }

    public void onCategoryClicked(CategoryModel categoryModel) {
        transaction.transactionCategoryID = categoryModel.getCategory();
        getViewState().hideBottomSheet();
        getViewState().onCategoryChosen(categoryModel);
    }

    public void onAccountClicked(AccountModel accountModel) {
        transaction.transactionAccountID = accountModel.getAccount();
        getViewState().hideBottomSheet();
        getViewState().onAccountChosen(accountModel);
        getViewState().setCategoryShown(true);
    }

    public void setIncome(Boolean isIncome){
        if (isIncome)
            transaction.isIncome = 1;
        else transaction.isIncome = 0;
    }

    public void setNote(String note){
        transaction.note = note;
    }

    public void setSum(Double sum){
        transaction.sum = sum;
    }

    public void setDate(Long date){
        transaction.date = date;
    }

    public void setImage(String url){
        transaction.image = url;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
