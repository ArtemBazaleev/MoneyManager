package com.example.moneymanager.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.moneymanager.interfaces.db.DataBaseTransactionContract;
import com.example.moneymanager.model.FilterModel;
import com.example.moneymanager.model.HistoryModel;
import com.example.moneymanager.model.dbModel.DbTransaction;
import com.example.moneymanager.presentation.view.HistoryFragmentView;
import com.example.moneymanager.utils.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class HistoryFragmentPresenter extends MvpPresenter<HistoryFragmentView> {
    private DataBaseTransactionContract transactionContract;
    private FilterModel filterModel;

    public HistoryFragmentPresenter(DataBaseTransactionContract transactionContract) {
        this.transactionContract = transactionContract;
    }

    public void onCreate(){
        requestCurrentTransactions();
    }
    private void requestCurrentTransactions(){
        Calendar from = Calendar.getInstance();
        from.set(from.get(Calendar.YEAR), from.get(Calendar.MONTH), 1, 0, 0);
        Disposable d = transactionContract.getDataWithRange(from.getTimeInMillis(), Calendar.getInstance().getTimeInMillis())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(dbTransactions -> {
                    Disposable d1 = Flowable.just(updateTransactions(dbTransactions))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(getViewState()::loadTransactions, Throwable::printStackTrace);
                }, Throwable::printStackTrace);
    }

    private void requestAllTransactions() {
        Disposable d = transactionContract.getAllTransactions()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(dbTransactions -> {
                   Disposable d1 = Flowable.just(updateTransactions(dbTransactions))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(getViewState()::loadTransactions, Throwable::printStackTrace);
                }, Throwable::printStackTrace);
    }

    private List<HistoryModel> updateTransactions(List<DbTransaction> transaction) {
        double sum = 0.0;
        List<HistoryModel> historyModels = new ArrayList<>();
        List<HistoryModel> titles = new LinkedList<>();
        if (transaction.size()==0)
            return historyModels;
        else if (transaction.size()==1){
            HistoryModel title = new HistoryModel(HistoryModel.TYPE_TOTAL);
            title.setSum(transaction.get(0).sum);
            title.setDate(Utility.formatDateForHistoryTitle(transaction.get(0).date));
            historyModels.add(title);
            historyModels.add(new HistoryModel(transaction.get(0)));
            return historyModels;
        }
        Collections.sort(transaction, (t, t1) -> Long.compare(t.date, t1.date));
        Collections.reverse(transaction);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(transaction.get(0).date));
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        HistoryModel title1 = new HistoryModel(HistoryModel.TYPE_TOTAL);
        title1.setDate(Utility.formatDateForHistoryTitle(transaction.get(0).date));
        if (transaction.get(0).isIncome==1)
            title1.setSum(title1.getSum() + transaction.get(0).sum);
        else title1.setSum(title1.getSum() - transaction.get(0).sum);
        historyModels.add(title1);
        titles.add(title1);
        historyModels.add(new HistoryModel(transaction.get(0)));
        for (int i=1; i<transaction.size(); i++) {
            calendar.setTime(new Date(transaction.get(i).date));
            if (day == calendar.get(Calendar.DAY_OF_MONTH)
                    && year == calendar.get(Calendar.YEAR)
                    && month== calendar.get(Calendar.MONTH)){
                historyModels.add(new HistoryModel(transaction.get(i)));
                if (transaction.get(i).isIncome==1)
                    titles.get(titles.size()-1).setSum(titles.get(titles.size()-1).getSum() + transaction.get(i).sum);
                else
                    titles.get(titles.size()-1).setSum(titles.get(titles.size()-1).getSum() - transaction.get(i).sum);
            }
            else{
                HistoryModel title = new HistoryModel(HistoryModel.TYPE_TOTAL);
                title.setDate(Utility.formatDateForHistoryTitle(transaction.get(i).date));
                historyModels.add(title);
                titles.add(title);
                if (transaction.get(i).isIncome==1)
                    titles.get(titles.size()-1).setSum(titles.get(titles.size()-1).getSum() + transaction.get(i).sum);
                else
                    titles.get(titles.size()-1).setSum(titles.get(titles.size()-1).getSum() - transaction.get(i).sum);
                historyModels.add(new HistoryModel(transaction.get(i)));
                day = calendar.get(Calendar.DAY_OF_MONTH);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
            }
        }
        sum = 0;
        for (HistoryModel i:titles) {
            sum+=i.getSum();
        }
        getViewState().setTotalBalance(sum);
        return historyModels;
    }
}
