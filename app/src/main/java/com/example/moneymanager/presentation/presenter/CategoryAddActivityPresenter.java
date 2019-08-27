package com.example.moneymanager.presentation.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.moneymanager.interfaces.db.DataBaseTransactionContract;
import com.example.moneymanager.interfaces.db.DbAccountInteraction;
import com.example.moneymanager.interfaces.db.DbCategoryInteraction;
import com.example.moneymanager.interfaces.db.DbIconInteraction;
import com.example.moneymanager.model.CategoryModel;
import com.example.moneymanager.model.IconModel;
import com.example.moneymanager.model.dbModel.DbAccount;
import com.example.moneymanager.model.dbModel.DbCategory;
import com.example.moneymanager.model.dbModel.DbIcon;
import com.example.moneymanager.model.dbModel.DbTransaction;
import com.example.moneymanager.presentation.view.CategoryAddActivityView;
import com.example.moneymanager.ui.CategoryAddActivity;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class CategoryAddActivityPresenter extends MvpPresenter<CategoryAddActivityView> {
    private DbAccountInteraction accountInteraction;
    private DbCategoryInteraction categoryInteraction;
    private DbIconInteraction iconInteraction;
    private DataBaseTransactionContract transactionContract;
    private int mode;
    private DbCategory category;
    private DbAccount account;
    private int isincome;
    private boolean isChosen = false;

    private String TAG = "AddDataPresenter";

    private DbIcon startIcon;

    public CategoryAddActivityPresenter(DbAccountInteraction accountInteraction,
                                        DbCategoryInteraction categoryInteraction,
                                        DbIconInteraction iconInteraction,
                                        DataBaseTransactionContract transactionContract) {
        Log.d(TAG, "CategoryAddActivityPresenter: called!");
        this.accountInteraction = accountInteraction;
        this.categoryInteraction = categoryInteraction;
        this.iconInteraction = iconInteraction;
        this.transactionContract = transactionContract;
        category = new DbCategory();
        category.categoryName = "";
        account = new DbAccount();
        account.accountName = "";
        Disposable d = iconInteraction.getAllIcons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dbIcons -> {
                        startIcon = dbIcons.get(0);
                        getViewState().onIconChosen(new IconModel(startIcon));
                        if (mode == CategoryAddActivity.MODE_CATEGORY)
                            category.drawableIcon = startIcon;
                        else account.drawableIcon = startIcon;
                    }, Throwable::printStackTrace);
    }

    public void setMode(int mode) {
        Log.d(TAG, "setMode: called!");
        this.mode = mode;
    }

    public void onSaveClicked() {
        Log.d(TAG, "onSaveClicked: called!");
        if (isChosen){
            if (mode == CategoryAddActivity.MODE_CATEGORY)
                updateCategory();
            else{

            }
        }
        else {
            if (mode == CategoryAddActivity.MODE_CATEGORY){
                if (category.categoryName.equals("")) {
                    getViewState().showErrorEmptyName();
                    return;
                }
                Completable.fromAction(() -> categoryInteraction
                        .addCategory(category))
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
    }

    private void updateCategory() {
        Log.d(TAG, "updateCategory: called!");
        Completable.fromAction(() -> categoryInteraction
                .updateCategory(category))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete1: called!");
                        Disposable d = transactionContract.getAllTransactions()
                                .subscribeOn(Schedulers.io())
                                .take(1)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(transactions -> {
                                    Log.d(TAG, "onComplete2: called!");
                                    List<DbTransaction> updateList = new LinkedList<>();
                                    for (DbTransaction i:transactions) {
                                        if (i.transactionCategoryID.categoryId == category.categoryId) {
                                            i.transactionCategoryID = category;
                                            updateList.add(i);
                                        }
                                    }
                                    updateTransactions(updateList);
                                },Throwable::printStackTrace);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    private void updateTransactions(List<DbTransaction> transactions){
        Log.d(TAG, "updateTransactions: ");
        Completable.fromAction(() -> transactionContract
                .updateTransactions(transactions))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete3: called!");
                        getViewState().stopSelf();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    public void onChoseDataClicked() {
        if (mode == CategoryAddActivity.MODE_CATEGORY){
            getViewState().showBottomSheetCategory();
        }
        else getViewState().showBottomSheetAccount();
    }

    public void onDelClicked() {
        category = new DbCategory();
        account = new DbAccount();
        account.accountName = "";
        category.categoryName = "";
        category.isIncome = isincome;
        getViewState().onNameChosen("");
        getViewState().onIconChosen(new IconModel(startIcon));
        getViewState().setVisibleCategory(false);
        isChosen = false;
    }

    public void onNameChanged(String name) {
        if (mode == CategoryAddActivity.MODE_CATEGORY){
            category.categoryName = name;
        }
        else account.accountName = name;
    }

    public void onChoseIconClicked() {
        getViewState().showBottomSheetIcon();
    }

    public void onCategoryChosen(CategoryModel categoryModel) {
        category = categoryModel.getCategory();
        getViewState().onIconChosen(new IconModel(category.drawableIcon));
        getViewState().onNameChosen(category.categoryName);
        getViewState().setVisibleCategory(true);
        isChosen = true;
    }

    public void onImageChosen(IconModel iconModel) {
        getViewState().onIconChosen(iconModel);
        if (mode == CategoryAddActivity.MODE_CATEGORY){
            category.drawableIcon = iconModel.getDbIcon();
        }
        else account.drawableIcon = iconModel.getDbIcon();
    }

    public void setIsIncome(int isincome) {

        this.isincome = isincome == CategoryAddActivity.OUTCOME ? 0 : 1;
        Log.d(TAG, "setIsIncome: called with value:"+ isincome);
        category.isIncome = this.isincome;
    }
}
