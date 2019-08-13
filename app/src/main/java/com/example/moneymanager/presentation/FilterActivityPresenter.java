package com.example.moneymanager.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.moneymanager.interfaces.db.DbAccountInteraction;
import com.example.moneymanager.interfaces.db.DbAccountInteraction_Impl;
import com.example.moneymanager.interfaces.db.DbCategoryInteraction;
import com.example.moneymanager.model.AccountModel;
import com.example.moneymanager.model.CategoryModel;
import com.example.moneymanager.model.FilterModel;
import com.example.moneymanager.model.dbModel.DbCategory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class FilterActivityPresenter extends MvpPresenter<FilterActivityView> {
    private FilterModel filter;
    private DbAccountInteraction accountInteraction;
    private DbCategoryInteraction categoryInteraction;

    public FilterActivityPresenter(DbAccountInteraction accountInteraction, DbCategoryInteraction categoryInteraction) {
        this.accountInteraction = accountInteraction;
        this.categoryInteraction = categoryInteraction;
        filter = new FilterModel();
    }

    public void onCategoryClicked(CategoryModel category){
        filter.setCategory(category);
        getViewState().setSelectedCategory(category);
    }

    public void onAccountClicked(AccountModel account){
        filter.setAccount(account);
        getViewState().setSelectedAccount(account);
    }

    public void onCreate(){
        loadAccount();
        loadCategories();
    }

    private void loadCategories() {
      Disposable d = categoryInteraction.getAllCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dbCategories -> {
                    List<CategoryModel> models = new LinkedList<>();
                    for (DbCategory i: dbCategories)
                        models.add(new CategoryModel(i));
                    getViewState().initCategory(models);
                }, throwable -> {

                });

    }

    private void loadAccount() {
        List<AccountModel> models = new ArrayList<>();
        for (int i=0; i<10; i++)
            models.add(new AccountModel());
        getViewState().initAccount(models);
    }

    public void onSelectDate() {
        getViewState().showDateTimePicker();
    }
}
