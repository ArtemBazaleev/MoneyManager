package com.example.moneymanager.presentation;

import com.aminography.primecalendar.PrimeCalendar;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.moneymanager.interfaces.db.DbAccountInteraction;
import com.example.moneymanager.interfaces.db.DbCategoryInteraction;
import com.example.moneymanager.model.AccountModel;
import com.example.moneymanager.model.CategoryModel;
import com.example.moneymanager.model.FilterModel;
import com.example.moneymanager.model.dbModel.DbAccount;
import com.example.moneymanager.ui.BottomSheetCategoryFragment;

import java.util.ArrayList;
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
        getViewState().setEnabledCategory(true);
        getViewState().setCategory(category);
    }

    public void onAccountClicked(AccountModel account){
        filter.setAccount(account);
        getViewState().setSelectedAccount(account);
    }

    public void onCreate(){
        loadAccount();
    }

    private void loadAccount() {
        Disposable d = accountInteraction.getAllAccounts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(accounts -> {
                    List<AccountModel> models = new ArrayList<>();
                    for (DbAccount i:accounts)
                        models.add(new AccountModel(i));
                    getViewState().initAccount(models);

                }, throwable -> {

                });
    }

    public void onSelectDate() {
        getViewState().showDateTimePicker();
    }

    public void showCategoryClicked() {
        if (filter.getIncome() == null)
            getViewState().showCategories(BottomSheetCategoryFragment.MODE_ALL);
        else if (filter.getIncome())
            getViewState().showCategories(BottomSheetCategoryFragment.MODE_INCOME);
        else getViewState().showCategories(BottomSheetCategoryFragment.MODE_OUTCOME);

    }

    public void onDelCategoryClicked() {
        filter.setCategory(null);
        getViewState().setEnabledCategory(false);
    }

    public void onRangeDaysPicked(PrimeCalendar startDay, PrimeCalendar endDay) {
        filter.setFromDate(startDay.getTimeInMillis());
        filter.setToDate(endDay.getTimeInMillis());
        getViewState().setFromToDate(
                startDay.getShortDateString().replace("/", "."),
                endDay.getShortDateString().replace("/", ".")
        );
        getViewState().setEnabledDateLayout(true);
    }

    public void onIncome() {
        filter.setIncome(true);
        getViewState().setEnabledCategory(false);
    }

    public void onOutCome() {
        filter.setIncome(false);
        getViewState().setEnabledCategory(false);
    }

    public void clearFilter() {
        filter = new FilterModel();
        getViewState().setEnabledCategory(false);
        getViewState().setSelectedAccount(new AccountModel()); //очистка выделенного элемента
        getViewState().clearCheck();
        getViewState().setEnabledDateLayout(false);
    }

    public void onDelDateClicked() {
        filter.setFromDate(null);
        filter.setToDate(null);
        getViewState().setEnabledDateLayout(false);
    }

    public void onIncomeAndOutCome() {
        getViewState().clearCheck();
        filter.setIncome(null);
        getViewState().setEnabledCategory(false);
    }
}
