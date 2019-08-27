package com.example.moneymanager.presentation.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.moneymanager.adapters.CategoryAdapter;
import com.example.moneymanager.interfaces.db.DataBaseTransactionContract;
import com.example.moneymanager.interfaces.db.DbAccountInteraction;
import com.example.moneymanager.interfaces.db.DbCategoryInteraction;
import com.example.moneymanager.model.AccountModel;
import com.example.moneymanager.model.CategoryModel;
import com.example.moneymanager.model.dbModel.DbAccount;
import com.example.moneymanager.model.dbModel.DbCategory;
import com.example.moneymanager.model.dbModel.DbTransaction;
import com.example.moneymanager.presentation.view.EventActivityView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class EventActivityPresenter extends MvpPresenter<EventActivityView> implements
        CategoryAdapter.IOnItemClicked {
    private static final int MODE_INCOME = 0;
    private static final int MODE_OUTCOME = 1;
    private String TAG = "EventActivityPresenter";

    private DbCategoryInteraction categoryInteraction;
    private DbAccountInteraction accountInteraction;
    private DbTransaction transaction;
    private DataBaseTransactionContract transactionContract;
    private int mode = MODE_OUTCOME;

    public EventActivityPresenter(DbCategoryInteraction categoryInteraction,
                                  DbAccountInteraction accountInteraction,
                                  DataBaseTransactionContract transactionContract) {
        this.categoryInteraction = categoryInteraction;
        this.accountInteraction = accountInteraction;
        this.transactionContract = transactionContract;
    }

    public void initBottomSheet(){
        Log.d(TAG, "onCreate: called");
        transaction = new DbTransaction();
        transaction.note = "";
        transaction.image = "";
        transaction.isIncome = 0;
        transaction.sum = 0.0;
        transaction.date =  new Date(System.currentTimeMillis()).getTime();
        getViewState().setDateFragment(transaction.date);
        requestAccounts();
        requestCategories();
    }



    private void requestAccounts() {
        Log.d(TAG, "requestAccounts: called");
        Disposable d = accountInteraction.getAllAccounts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe(accounts -> {
                    List<AccountModel> models = new ArrayList<>();
                    for (DbAccount i:accounts)
                        models.add(new AccountModel(i));
                    getViewState().loadAccounts(models);
                    getViewState().onAccountChosen(models.get(0));
                    transaction.transactionAccountID = models.get(0).getAccount();
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

    private void requestOutcomeCategories() {  // TODO: 2019-08-15 bug when first inited...
        Log.d(TAG, "requestOutcomeCategories: called");
        Disposable d = categoryInteraction.getAllOutComeCategories()
                .subscribeOn(Schedulers.io())
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categories -> {
                    List<CategoryModel> models = new ArrayList<>();
                    for (DbCategory i:categories)
                        models.add(new CategoryModel(i));
                    getViewState().loadCategories(models);
                    getViewState().onCategoryChosen(models.get(0),false);
                    transaction.transactionCategoryID = models.get(0).getCategory();
                }, Throwable::printStackTrace);
    }

    private void requestIncomeCategories() {
        Log.d(TAG, "requestIncomeCategories: called");
        Disposable d = categoryInteraction.getAllInComeCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe(categories -> {
                    Collections.sort(categories, (category, t1) -> category.position - t1.position);
                    List<CategoryModel> models = new ArrayList<>();
                    for (DbCategory i:categories)
                        models.add(new CategoryModel(i));
                    getViewState().loadCategories(models);
                    getViewState().onCategoryChosen(models.get(0),false);
                    transaction.transactionCategoryID = models.get(0).getCategory();
                }, Throwable::printStackTrace);
    }

    @Override
    public void onCategoryClicked(CategoryModel categoryModel) {
        transaction.transactionCategoryID = categoryModel.getCategory();
        getViewState().hideBottomSheet();
        getViewState().onCategoryChosen(categoryModel,true);
    }
    @Override
    public void onAddNewCategoryClicked(){
        getViewState().startAddNewCategoryActivity(transaction.isIncome);
    }

    @Override
    public void onPositionChanged(DbCategory oldPosition, DbCategory newPosition) {
        int temp = oldPosition.position;
        oldPosition.position = newPosition.position;
        newPosition.position = temp;
        List<DbCategory> update = new LinkedList<>();
        update.add(oldPosition);
        update.add(newPosition);
        Completable.fromAction(() -> categoryInteraction
                .updateCategory(update))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: called!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                }); 
    }

    public void onAccountClicked(AccountModel accountModel) {
        transaction.transactionAccountID = accountModel.getAccount();
        getViewState().hideBottomSheet();
        getViewState().onAccountChosen(accountModel);
        getViewState().setCategoryShown(true);
    }

    public void setIncome(Boolean isIncome){
        if (isIncome) {
            transaction.isIncome = 1;
            requestIncomeCategories();
        }
        else {
            transaction.isIncome = 0;
            requestOutcomeCategories();
        }

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

    public void onNoteChanged(String note) {
        transaction.note = note;
    }

    public void onDateChosen(Long date) {
        transaction.date = date;
        getViewState().setDateFragment(date);
    }

    public void onSumChanged(double sum) {
        transaction.sum = sum;
    }

    public void onSaveClicked() {
        Log.d(TAG, "onSaveClicked: " + transaction.toString());
        Completable.fromAction(() -> transactionContract
                .addTransaction(transaction))
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

    public void onCreate() {
    }
}
