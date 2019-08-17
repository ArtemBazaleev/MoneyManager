package com.example.moneymanager.presentation.presenter;

import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primecalendar.common.CalendarFactory;
import com.aminography.primecalendar.common.CalendarType;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.moneymanager.interfaces.db.DbAccountInteraction;
import com.example.moneymanager.interfaces.db.DbCategoryInteraction;
import com.example.moneymanager.model.AccountModel;
import com.example.moneymanager.model.CategoryModel;
import com.example.moneymanager.model.FilterModel;
import com.example.moneymanager.model.dbModel.DbAccount;
import com.example.moneymanager.presentation.view.FilterActivityView;
import com.example.moneymanager.ui.BottomSheetCategoryFragment;

import java.util.ArrayList;
import java.util.Calendar;
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

    public void onCreate(FilterModel filterHistory){
        filter = filterHistory;
        if (filter.isEmpty())
            loadAccount();
        else
            loadAccountAndUpdateUi();

    }

    private void loadAccountAndUpdateUi() {
        Disposable d = accountInteraction.getAllAccounts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(accounts -> {
                    List<AccountModel> models = new ArrayList<>();
                    for (DbAccount i:accounts)
                        models.add(new AccountModel(i));
                    getViewState().initAccount(models);
                    updateUI();
                }, Throwable::printStackTrace);
    }

    private void updateUI() {
        if (filter.getAccount()!=null)
            getViewState().setSelectedAccount(filter.getAccount());
        if (filter.getCategory()!=null) {
            getViewState().setCategory(filter.getCategory());
            getViewState().setEnabledCategory(true);
        }
        if (filter.getFromDate()!=null){
            PrimeCalendar startDay = CalendarFactory.newInstance(CalendarType.CIVIL);
            startDay.setTimeInMillis(filter.getFromDate());
            PrimeCalendar endDay = CalendarFactory.newInstance(CalendarType.CIVIL);
            endDay.setTimeInMillis(filter.getToDate());
            getViewState().setFromToDate(
                    startDay.getShortDateString().replace("/", "."),
                    endDay.getShortDateString().replace("/", ".")
            );
            getViewState().setEnabledDateLayout(true);
        }
        if (filter.getIncome()!=null){
            if (filter.getIncome())
                getViewState().setIncomeSelected();
            else getViewState().setOutcomeSelected();
        }

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
        getViewState().setFromToDate(
                startDay.getShortDateString().replace("/", "."),
                endDay.getShortDateString().replace("/", ".")
        );

        Calendar startDayCalendar = Calendar.getInstance();
        startDayCalendar.set(Calendar.YEAR,startDay.getYear());
        startDayCalendar.set(Calendar.MONTH,startDay.getMonth());
        startDayCalendar.set(Calendar.DAY_OF_MONTH, startDay.getDayOfMonth());
        startDayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startDayCalendar.set(Calendar.MINUTE, 0);
        startDayCalendar.set(Calendar.SECOND,0);
        Calendar endDayCalendar = Calendar.getInstance();
        endDayCalendar.set(Calendar.YEAR, endDay.getYear());
        endDayCalendar.set(Calendar.MONTH, endDay.getMonth());
        endDayCalendar.set(Calendar.DAY_OF_MONTH, endDay.getDayOfMonth());
        endDayCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endDayCalendar.set(Calendar.MINUTE, 59);
        endDayCalendar.set(Calendar.SECOND,59);


        filter.setFromDate(startDayCalendar.getTime().getTime());
        filter.setToDate(endDayCalendar.getTime().getTime());

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

    public void onSaveClicked() {
        getViewState().stopSelf(filter);
    }
}
