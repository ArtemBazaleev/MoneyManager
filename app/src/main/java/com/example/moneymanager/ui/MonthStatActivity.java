package com.example.moneymanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.moneymanager.App;
import com.example.moneymanager.R;
import com.example.moneymanager.adapters.HistoryAdapter;
import com.example.moneymanager.model.HistoryModel;
import com.example.moneymanager.model.dbModel.DbTransaction;
import com.example.moneymanager.utils.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MonthStatActivity extends AppCompatActivity {
    @BindView(R.id.recycler_history_stat) RecyclerView recyclerView;
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String MODE = "mode";
    public static final String MODEINCOME = "Income";
    public static final String MODEOUTCOME = "Outcome";

    private int year = 2019;
    private int month = 1;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_stat);
        ButterKnife.bind(this);
        if (getIntent().getExtras()!= null) {
            year = getIntent().getExtras().getInt(YEAR);
            month = getIntent().getExtras().getInt(MONTH);
            mode = getIntent().getExtras().getString(MODE);
        }

        init();
    }

    private void init() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, month);

        Calendar calendarFrom = Calendar.getInstance();
        calendarFrom.set(Calendar.YEAR, year);
        calendarFrom.set(Calendar.MONTH, month);
        calendarFrom.set(Calendar.DAY_OF_MONTH, 1);
        calendarFrom.set(Calendar.HOUR_OF_DAY, 0);
        calendarFrom.set(Calendar.MINUTE, 0);
        calendarFrom.set(Calendar.SECOND, 0);

        Calendar calendarTo = Calendar.getInstance();
        calendarTo.set(Calendar.YEAR, year);
        calendarTo.set(Calendar.MONTH, month);
        calendarTo.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendarTo.set(Calendar.HOUR_OF_DAY, 23);
        calendarTo.set(Calendar.MINUTE, 59);
        calendarTo.set(Calendar.SECOND, 59);
        Log.d("MonthStat", "month = " + month + " Year = "+ year);
        Log.d("MonthStat", "From: " + Utility.formatDate(calendarFrom.getTime().getTime()) + " \nTo: " + Utility.formatDate(calendarTo.getTime().getTime()));


        App app = (App) getApplication();
        int isincome;
        if (mode.equals(MODEINCOME))
            isincome = 1;
        else isincome = 0;
        Disposable d = app.getDatabase().dataBaseTransactionContract()
                .getDataWithRange(calendarFrom.getTime().getTime(), calendarTo.getTime().getTime(), isincome)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(transactions -> initRecycler(updateTransactions(transactions)),
                        Throwable::printStackTrace);

    }

    private void initRecycler(List<HistoryModel> models){
        HistoryAdapter adapter = new HistoryAdapter(this, models, this::onHistoryItemClicked);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void onHistoryItemClicked() {

    }


    private List<HistoryModel> updateTransactions(List<DbTransaction> transaction) {
        double sum = 0.0;
        List<HistoryModel> historyModels = new ArrayList<>();
        List<HistoryModel> titles = new LinkedList<>();
        if (transaction.size()==0) {
            return historyModels;
        }
        else if (transaction.size()==1){
            HistoryModel title = new HistoryModel(HistoryModel.TYPE_TOTAL);
            if (transaction.get(0).isIncome==1)
                title.setSum(title.getSum() + transaction.get(0).sum);
            else title.setSum(title.getSum() - transaction.get(0).sum);
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
        return historyModels;
    }
}
