package com.example.moneymanager.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primecalendar.civil.CivilCalendar;
import com.aminography.primecalendar.common.CalendarFactory;
import com.aminography.primecalendar.common.CalendarType;
import com.aminography.primedatepicker.PickType;
import com.aminography.primedatepicker.fragment.PrimeDatePickerBottomSheet;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.moneymanager.App;
import com.example.moneymanager.R;
import com.example.moneymanager.adapters.CategoryStatAdapter;
import com.example.moneymanager.adapters.MonthStatAdapter;
import com.example.moneymanager.adapters.StatPagerAdapter;
import com.example.moneymanager.model.CategoryStatModel;
import com.example.moneymanager.model.StatMonthModel;
import com.example.moneymanager.model.StatPieChartModel;
import com.example.moneymanager.presentation.presenter.StatisticsFragmentPresenter;
import com.example.moneymanager.presentation.view.StatisticsFragmentView;
import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar;


import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticsFragment extends MvpAppCompatFragment implements StatisticsFragmentView {

    public static final String MODE_OUTCOME = "outcome";
    public static final String MODE_INCOME = "income";
    public static final String MODE = "mode";

    private String mode;

    public static final String TAG = "StatisticsFragment";
    @BindView(R.id.select_date_constraint)
    ConstraintLayout selectDate;
    private PrimeDatePickerBottomSheet datePicker;
    @BindView(R.id.from_date) TextView fromDate;
    @BindView(R.id.to_date) TextView toDate;
    @BindView(R.id.recycler_stat) RecyclerView recyclerView;
    @BindView(R.id.ChartProgressBar) ChartProgressBar mChart;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.textView40) TextView textView;
    @BindView(R.id.recycler_stat_month) RecyclerView monthRecycler;
    @BindView(R.id.year_spinner) Spinner yearSpinner;
    @BindView(R.id.imageView8) ImageView imgDragRight;
    @BindView(R.id.imageView9) ImageView imgDragLeft;

    private MonthStatAdapter monthStatAdapter;
    private CategoryStatAdapter categoryStatAdapter;

    @InjectPresenter
    StatisticsFragmentPresenter presenter;
    private boolean inited;
    private StatPagerAdapter adapter;
    private boolean firstInited = true;

    public static StatisticsFragment newInstance(String modeOutcome) {
        Bundle arg = new Bundle();
        arg.putString(MODE, modeOutcome);
        StatisticsFragment fragment = new StatisticsFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mode = getArguments().getString(MODE);

    }

    @ProvidePresenter
    StatisticsFragmentPresenter providePresenter(){
        App app = (App) Objects.requireNonNull(getActivity()).getApplication();
        return new StatisticsFragmentPresenter(app.getDatabase().dataBaseTransactionContract());
    }

    private List<StatPieChartModel> pieChartModels;

    public StatisticsFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, v);


        if (mode.equals(MODE_INCOME))
            textView.setText("Доход");
        else textView.setText("Расход");

        CalendarType calendarType = CalendarType.CIVIL; //use type that suits your use case
        PrimeCalendar today = CalendarFactory.newInstance(calendarType);
        datePicker = PrimeDatePickerBottomSheet.newInstance(
                today,
                null ,
                today ,
                PickType.RANGE_START,
                null,
                new CivilCalendar(),
                new CivilCalendar(),
                "fonts/open_sans_ligh.ttf" // can be null
        );
        selectDate.setOnClickListener(l->presenter.onDatePickerClicked());

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.setMode(mode);
        presenter.onStart();
    }


    //MVP methods
    @Override
    public void showDatePickerDialog() {

        datePicker.setOnDateSetListener(new PrimeDatePickerBottomSheet.OnDayPickedListener() {

            @Override
            public void onSingleDayPicked(@NonNull PrimeCalendar singleDay) {
                presenter.onDateChosen(singleDay, singleDay);
            }

            @Override
            public void onRangeDaysPicked(@NonNull PrimeCalendar startDay, @NonNull PrimeCalendar endDay) {
                presenter.onDateChosen(startDay, endDay);
            }
        });
        datePicker.show(getFragmentManager());
    }

    @Override
    public void updateViewPager(List<StatPieChartModel> mPieChartModels) {
        if (!inited){
            pieChartModels = mPieChartModels;
            adapter = new StatPagerAdapter(pieChartModels, getContext());
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

                @Override
                public void onPageSelected(int position) {
                    if (pieChartModels.get(position).getMode()== StatPieChartModel.MODE_INCOME) {
                        textView.setText(getResources().getString(R.string.income_two_dots));
                        imgDragRight.setVisibility(View.GONE);
                        imgDragLeft.setVisibility(View.VISIBLE);
                        presenter.onIncome();
                    }
                    else {
                        textView.setText(getResources().getString(R.string.outcome_two_dots));
                        imgDragRight.setVisibility(View.VISIBLE);
                        imgDragLeft.setVisibility(View.GONE);
                        presenter.onOutcome();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) { }
            });
            inited = true;
        }
        else
            adapter.updatePieChart(mPieChartModels);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void updateBarChart(ArrayList<BarData> charts, float maxVal) {
        mChart.setDataList(charts);
        mChart.setMaxValue(maxVal);
        mChart.build();
    }

    @Override
    public void updateCategories(List<CategoryStatModel> categoryModels) {
        if (categoryStatAdapter == null) {
            categoryStatAdapter = new CategoryStatAdapter(categoryModels, getContext());
            recyclerView.setAdapter(categoryStatAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }else {
            categoryStatAdapter.updateData(categoryModels);
        }
    }

    @Override
    public void updateMonthModel(List<StatMonthModel> monthModels) {
        monthRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        monthStatAdapter = new MonthStatAdapter(monthModels, getContext());
        monthStatAdapter.setmListener(presenter::onMonthClicked);
        monthRecycler.setAdapter(monthStatAdapter);
    }

    @Override
    public void updateAvailableYears(String[] years) {
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, years);
        yearSpinner.setAdapter(spinnerArrayAdapter);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemSelected: " +(String) adapterView.getItemAtPosition(i));
                presenter.onYearSelected((String) adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void updateDate(String from, String to) {
        fromDate.setText(String.format("c %s", from));
        toDate.setText(String.format("до %s", to));
    }

    @Override
    public void startMonthStatActivity(StatMonthModel name) {
        Intent i = new Intent(getContext(), MonthStatActivity.class);
        i.putExtra(MonthStatActivity.MONTH, name.getMonth());
        i.putExtra(MonthStatActivity.YEAR, name.getYear());
        if (name.getType().equals(StatMonthModel.TYPE_INCOME))
            i.putExtra(MonthStatActivity.MODE, MonthStatActivity.MODEINCOME);
        else i.putExtra(MonthStatActivity.MODE, MonthStatActivity.MODEOUTCOME);
        startActivity(i);
    }
}
