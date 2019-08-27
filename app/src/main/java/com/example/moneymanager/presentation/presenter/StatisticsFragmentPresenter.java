package com.example.moneymanager.presentation.presenter;


import android.util.Log;

import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primecalendar.common.CalendarFactory;
import com.aminography.primecalendar.common.CalendarType;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.moneymanager.interfaces.db.DataBaseTransactionContract;
import com.example.moneymanager.model.CategoryStatModel;
import com.example.moneymanager.model.StatInf;
import com.example.moneymanager.model.StatMonthModel;
import com.example.moneymanager.model.StatPieChartModel;
import com.example.moneymanager.model.dbModel.DbTransaction;
import com.example.moneymanager.presentation.view.StatisticsFragmentView;
import com.example.moneymanager.ui.StatisticsFragment;
import com.example.moneymanager.utils.Utility;
import com.hadiidbouk.charts.BarData;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class StatisticsFragmentPresenter extends MvpPresenter<StatisticsFragmentView> {
    private DataBaseTransactionContract transactionContract;
    private static final String TAG = "StatisticsPresenter";
    private Long mFromDate;
    private Long mToDate;
    private Integer currentYear;
    private String mode;
    private String[] mYearsArr;
    private String[] mYearsArrIncome;
    private DateFormat formatter = new SimpleDateFormat("MMMM", Locale.getDefault());


    public StatisticsFragmentPresenter(DataBaseTransactionContract transactionContract) {
        this.transactionContract = transactionContract;
    }

    public void onDateChosen(PrimeCalendar startDay, PrimeCalendar endDay) {
        getViewState().updateDate(
                startDay.getShortDateString().replace("/", "."),
                endDay.getShortDateString().replace("/", ".")
        );

        Calendar to = Calendar.getInstance();
        to.set(Calendar.YEAR, endDay.getYear());
        to.set(Calendar.MONTH, endDay.getMonth());
        to.set(Calendar.DAY_OF_MONTH, endDay.getDayOfMonth());
        to.set(Calendar.HOUR_OF_DAY, 23);
        to.set(Calendar.MINUTE, 59);
        to.set(Calendar.SECOND, 59);

        Calendar from = Calendar.getInstance();
        from.set(Calendar.YEAR, startDay.getYear());
        from.set(Calendar.MONTH, startDay.getMonth());
        from.set(Calendar.DAY_OF_MONTH, startDay.getDayOfMonth());
        from.set(Calendar.HOUR_OF_DAY, 0);
        from.set(Calendar.MINUTE, 0);
        from.set(Calendar.SECOND, 0);

        Log.d(TAG, "onDateChosen: mode = " + mode);
        Log.d(TAG, "from: " + Utility.formatDate(from.getTime().getTime()) + "\nto: " + Utility.formatDate(to.getTime().getTime()));
        mFromDate = from.getTime().getTime();
        mToDate = to.getTime().getTime();
        initTransactions(mFromDate, mToDate);

    }

    private void initTransactions(Long fromDate, Long toDate){
        Disposable d =transactionContract.getDataWithRange(fromDate, toDate)
                .subscribeOn(Schedulers.io())
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::init, Throwable::printStackTrace);
    }

    public void onIncome() {
    }

    public void onOutcome() {
    }

    public void onStart(){
        calculateYears();
    }

    private void calculateYears() {
        Disposable d = transactionContract.getAllTransactions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe(dbTransactions -> Completable.fromAction(() -> updateYears(dbTransactions))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) { }
                            @Override
                            public void onComplete() { //todo null pointer check
                                if (mode.equals(StatisticsFragment.MODE_INCOME)) {
                                    if (mYearsArrIncome == null)
                                        return;
                                    getViewState().updateAvailableYears(mYearsArrIncome);
                                }
                                else {
                                    if (mYearsArr == null)
                                        return;
                                    getViewState().updateAvailableYears(mYearsArr);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }
                        }), Throwable::printStackTrace);
    }

    private void updateYears(List<DbTransaction> dbTransactions) {
        List<String> res = new LinkedList<>();
        List<String> resIncome = new LinkedList<>();
        Calendar calendar = Calendar.getInstance();
        for (DbTransaction i:dbTransactions) {
            calendar.setTime(new Date(i.date));
            if (i.isIncome==0) {
                if (!res.contains(String.valueOf(calendar.get(Calendar.YEAR))))
                    res.add(String.valueOf(calendar.get(Calendar.YEAR)));
            }else {
                if (!resIncome.contains(String.valueOf(calendar.get(Calendar.YEAR))))
                    resIncome.add(String.valueOf(calendar.get(Calendar.YEAR)));
            }
        }
        if (res.size()!=0) {
            mYearsArr = new String[res.size()];
            for (int i = 0; i < mYearsArr.length; i++) mYearsArr[i] = res.get(i);
        }
        if (resIncome.size()!=0) {
            mYearsArrIncome = new String[resIncome.size()];
            for (int i = 0; i < mYearsArrIncome.length; i++) mYearsArrIncome[i] = res.get(i);
        }

    }

    private void initBarChart() {
        Log.d(TAG, "initBarChart: called");
        Calendar from = Calendar.getInstance();
        from.set(Calendar.YEAR, currentYear);
        from.set(Calendar.MONTH, 0);
        from.set(Calendar.DAY_OF_MONTH, 0);
        from.set(Calendar.HOUR_OF_DAY, 0);
        from.set(Calendar.MINUTE, 0);
        from.set(Calendar.SECOND, 0);

        Calendar to = Calendar.getInstance();
        to.set(Calendar.YEAR, currentYear);
        to.set(Calendar.MONTH, Calendar.DECEMBER);
        to.set(Calendar.DAY_OF_MONTH, 31);
        to.set(Calendar.HOUR, 23);
        to.set(Calendar.MINUTE, 59);
        to.set(Calendar.SECOND, 59);
        initTransactionsForBarChart(from.getTime().getTime(), to.getTime().getTime());
    }

    private void initTransactionsForBarChart(Long from, Long to){
        Log.d(TAG, "initTransactionsForBarChart: called");
        Disposable d = transactionContract.getDataWithRange(from,to)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe(this::updateBarChart,Throwable::printStackTrace);
    }

    private void updateBarChart(List<DbTransaction> transactions) {
        Log.d(TAG, "updateBarChart: called");

        LinkedHashMap<Integer,BarData>  barDataSetIncome = new LinkedHashMap<>();
        LinkedHashMap<Integer,BarData>  barDataSetOutcome = new LinkedHashMap<>();
        List<StatMonthModel> incomeStatMonth = new LinkedList<>();
        List<StatMonthModel> outcomeStatMonth = new LinkedList<>();

        for (int i = 1; i<13; i++){
            barDataSetIncome.put(i, new BarData(getMonth(i), 0.0f, ""));
            barDataSetOutcome.put(i, new BarData(getMonth(i), 0.0f, ""));
            incomeStatMonth.add(new StatMonthModel(getMonth(i), 0.0f, StatMonthModel.TYPE_INCOME, i-1));
            outcomeStatMonth.add(new StatMonthModel(getMonth(i), 0.0f, StatMonthModel.TYPE_OUTCOME, i-1));
        }

        for (DbTransaction i: transactions) {
            Calendar iDate = Calendar.getInstance();
            iDate.setTime(new Date(i.date));
            int month = iDate.get(Calendar.MONTH) + 1;
            if (i.isIncome == 1) {
                if (!barDataSetIncome.containsKey(month)) {
                    barDataSetIncome.put(month, new BarData(getMonth(month), (float) i.sum));
                } else {
                    Objects.requireNonNull(barDataSetIncome.get(month)).setBarValue((float) (Objects.requireNonNull(barDataSetIncome.get(month)).getBarValue() + i.sum));
                }
            }else{
                if (!barDataSetOutcome.containsKey(month)) {
                    barDataSetOutcome.put(month, new BarData(getMonth(month), (float) i.sum));

                } else {
                    Objects.requireNonNull(barDataSetOutcome.get(month)).setBarValue((float) (Objects.requireNonNull(barDataSetOutcome.get(month)).getBarValue() + i.sum));
                }
            }
        }

        ArrayList<BarData> resIncome = new ArrayList<>(barDataSetIncome.values());
        ArrayList<BarData> resOutcome = new ArrayList<>(barDataSetOutcome.values());
        float maxIncome = 0;

        for (int i1 = 0; i1 < resIncome.size(); i1++) {
            BarData i = resIncome.get(i1);
            if (i.getBarValue() > maxIncome)
                maxIncome = i.getBarValue();
            //incomeStatMonth.add(new StatMonthModel(i.getBarTitle(), i.getBarValue(), StatMonthModel.TYPE_INCOME));
            incomeStatMonth.get(i1).setValue(i.getBarValue());
        }

        float maxOutcome = 0;
        for (int i1 = 0; i1 < resOutcome.size(); i1++) {
            BarData i = resOutcome.get(i1);
            if (i.getBarValue() > maxOutcome)
                maxOutcome = i.getBarValue();
            //outcomeStatMonth.add(new StatMonthModel(i.getBarTitle(), i.getBarValue(), StatMonthModel.TYPE_OUTCOME));
            outcomeStatMonth.get(i1).setValue(i.getBarValue());
        }
        if (mode.equals(StatisticsFragment.MODE_INCOME)) {
            getViewState().updateBarChart(resIncome, maxIncome);
            getViewState().updateMonthModel(incomeStatMonth);
        }
        else {
            getViewState().updateBarChart(resOutcome, maxOutcome);
            getViewState().updateMonthModel(outcomeStatMonth);
        }

    }

    private String getMonth(int month) {
//        return new DateFormatSymbols().getMonths()[month-1].substring(0,3)+".";
        return DateTime.now().withMonthOfYear(month).toString("MMM").substring(0,3)+".";
    }

    private void init(List<DbTransaction> transactions){

        LinkedHashMap<String, StatInf> hashMapIncome = new LinkedHashMap<>();
        LinkedHashMap<String, StatInf> hashMapOutcome = new LinkedHashMap<>();

        for (DbTransaction i:transactions) {
            if (i.isIncome==1) {
                if (!hashMapIncome.containsKey(i.transactionCategoryID.categoryName)) {
                    StatInf inf = new StatInf();
                    inf.sum = i.sum;
                    inf.color = i.transactionCategoryID.drawableIcon.iconColorHex;
                    inf.name = i.transactionCategoryID.categoryName;
                    inf.icon = i.transactionCategoryID.drawableIcon.drawableIcon;
                    hashMapIncome.put(i.transactionCategoryID.categoryName, inf);
                }
                else {
                    Objects.requireNonNull(hashMapIncome.get(i.transactionCategoryID.categoryName)).sum += i.sum;
                }
            }else {
                if (!hashMapOutcome.containsKey(i.transactionCategoryID.categoryName)) {
                    StatInf inf = new StatInf();
                    inf.sum = i.sum;
                    inf.color = i.transactionCategoryID.drawableIcon.iconColorHex;
                    inf.name = i.transactionCategoryID.categoryName;
                    inf.icon = i.transactionCategoryID.drawableIcon.drawableIcon;
                    hashMapOutcome.put(i.transactionCategoryID.categoryName, inf);
                }
                else{
                    Objects.requireNonNull(hashMapOutcome.get(i.transactionCategoryID.categoryName)).sum += i.sum;
                }
            }
        }

        List<StatPieChartModel> pieChartModels = new LinkedList<>();
        StatPieChartModel pieChartModel1 = new StatPieChartModel(StatPieChartModel.MODE_INCOME);
        StatPieChartModel pieChartModel2 = new StatPieChartModel(StatPieChartModel.MODE_OUTCOME);
        List<StatInf> infIncome = new ArrayList<>(hashMapIncome.values());
        List<StatInf> infOutcome = new ArrayList<>(hashMapOutcome.values());

        List<CategoryStatModel> categoryStatModelsIncome = new LinkedList<>();
        List<CategoryStatModel> categoryStatModelsOutcome = new LinkedList<>();

        double[] xDataIncome = new double[infIncome.size()];
        List<String> xDataIncomeColors = new ArrayList<>(infIncome.size());

        for (int i = 0; i < infIncome.size(); i++) {
            xDataIncome[i] = infIncome.get(i).sum;
            xDataIncomeColors.add(infIncome.get(i).color);
            CategoryStatModel model = new CategoryStatModel();
            model.setSum(infIncome.get(i).sum);
            model.setColor(infIncome.get(i).color);
            model.setName(infIncome.get(i).name);
            model.setIcon(infIncome.get(i).icon);
            model.setIncome(true);
            categoryStatModelsIncome.add(model);
        }

        double[] xDataOutcome = new double[infOutcome.size()];
        List<String> xDataOutcomeColors = new ArrayList<>(infOutcome.size());
        for (int i = 0; i < infOutcome.size(); i++) {
            xDataOutcome[i] = infOutcome.get(i).sum;
            xDataOutcomeColors.add(infOutcome.get(i).color);
            CategoryStatModel model = new CategoryStatModel();
            model.setSum(infOutcome.get(i).sum);
            model.setColor(infOutcome.get(i).color);
            model.setName(infOutcome.get(i).name);
            model.setIcon(infOutcome.get(i).icon);
            model.setIncome(false);
            categoryStatModelsOutcome.add(model);
        }
        pieChartModel1.setColors(xDataIncomeColors);
        pieChartModel1.setxData(xDataIncome);

        pieChartModel2.setColors(xDataOutcomeColors);
        pieChartModel2.setxData(xDataOutcome);
        if (mode.equals(StatisticsFragment.MODE_INCOME))
        {
            pieChartModels.add(pieChartModel1);
            getViewState().updateCategories(categoryStatModelsIncome);
        }
        else{
            pieChartModels.add(pieChartModel2);
            getViewState().updateCategories(categoryStatModelsOutcome);
        }

        getViewState().updateViewPager(pieChartModels);

    }

    public void onMonthClicked(StatMonthModel statMonthModel) {
        statMonthModel.setYear(currentYear);
        getViewState().startMonthStatActivity(statMonthModel);
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void onDatePickerClicked() {
        getViewState().showDatePickerDialog();
    }

    public void onYearSelected(String year) {
        this.currentYear = Integer.valueOf(year);
        if (mFromDate==null || mToDate ==null) {
            Calendar to = Calendar.getInstance();
            to.set(Calendar.HOUR_OF_DAY, 23);
            to.set(Calendar.MINUTE, 59);
            to.set(Calendar.SECOND, 59);

            Calendar from = Calendar.getInstance();
            from.set(Calendar.YEAR, to.get(Calendar.YEAR));
            from.set(Calendar.MONTH, to.get(Calendar.MONTH));
            from.set(Calendar.DAY_OF_MONTH, 1);
            from.set(Calendar.HOUR_OF_DAY, 0);
            from.set(Calendar.MINUTE, 0);
            from.set(Calendar.SECOND, 0);
            Log.d(TAG, "from: " + Utility.formatDate(from.getTime().getTime()) + " to: " + Utility.formatDate(to.getTime().getTime()));
            initTransactions(from.getTime().getTime(), to.getTime().getTime());
            mFromDate = from.getTime().getTime();
            mToDate = to.getTime().getTime();

            PrimeCalendar startDay = CalendarFactory.newInstance(CalendarType.CIVIL);
            startDay.setTimeInMillis(from.getTime().getTime());
            PrimeCalendar endDay = CalendarFactory.newInstance(CalendarType.CIVIL);
            endDay.setTimeInMillis(to.getTime().getTime());
            getViewState().updateDate(
                    startDay.getShortDateString().replace("/", "."),
                    endDay.getShortDateString().replace("/", ".")
            );
        } else {
            initTransactions(mFromDate, mToDate);
        }
        initBarChart();
    }
}
