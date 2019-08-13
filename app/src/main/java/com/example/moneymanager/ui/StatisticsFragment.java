package com.example.moneymanager.ui;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primecalendar.civil.CivilCalendar;
import com.aminography.primecalendar.common.CalendarFactory;
import com.aminography.primecalendar.common.CalendarType;
import com.aminography.primedatepicker.PickType;
import com.aminography.primedatepicker.fragment.PrimeDatePickerBottomSheet;
import com.example.moneymanager.Month;
import com.example.moneymanager.R;
import com.example.moneymanager.adapters.CategoryStatAdapter;
import com.example.moneymanager.adapters.MonthStatAdapter;
import com.example.moneymanager.adapters.StatPagerAdapter;
import com.example.moneymanager.model.CategoryModel;
import com.example.moneymanager.model.StatMonthModel;
import com.example.moneymanager.model.StatPieChartModel;
import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar;


import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO: 2019-08-13 add moxy
public class StatisticsFragment extends Fragment{
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
    @BindView(R.id.imageView8)
    ImageView imgDragRight;
    @BindView(R.id.imageView9)
    ImageView imgDragLeft;
    String[] fakeYears = new String[]{"2019", "2018", "2017", "2015", "2014"};

    List<StatPieChartModel> pieChartModels;

    public StatisticsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, v);
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

        datePicker.setOnDateSetListener(new PrimeDatePickerBottomSheet.OnDayPickedListener() {

            @Override
            public void onSingleDayPicked(@NonNull PrimeCalendar singleDay) {
            }

            @Override
            public void onRangeDaysPicked(@NonNull PrimeCalendar startDay, @NonNull PrimeCalendar endDay) {
                fromDate.setText(String.format("c %s", startDay.getShortDateString().replace("/", ".")));
                toDate.setText(String.format("до %s", endDay.getShortDateString().replace("/", ".")));
                Log.d(TAG, "onRangeDaysPicked: "+startDay.getShortDateString());
            }
        });

        selectDate.setOnClickListener(l->showDatePicker());


        init();
        initBarChart();
        pieChartModels = new ArrayList<>(2);
        pieChartModels.add(new StatPieChartModel(StatPieChartModel.MODE_OUTCOME));
        pieChartModels.add(new StatPieChartModel(StatPieChartModel.MODE_INCOME));
        viewPager.setAdapter(new StatPagerAdapter(pieChartModels, getContext()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (pieChartModels.get(position).getMode()== StatPieChartModel.MODE_INCOME) {
                    textView.setText(getResources().getString(R.string.income_two_dots));
                    imgDragRight.setVisibility(View.GONE);
                    imgDragLeft.setVisibility(View.VISIBLE);
                }
                else {
                    textView.setText(getResources().getString(R.string.outcome_two_dots));
                    imgDragRight.setVisibility(View.VISIBLE);
                    imgDragLeft.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                if (state == ViewPager.SCROLL_STATE_DRAGGING)
//                    setInvisibleDragIcons();
            }
        });
        return v;
    }

    private void setInvisibleDragIcons() {
        imgDragLeft.setVisibility(View.GONE);
        imgDragRight.setVisibility(View.GONE);
    }

    private void initBarChart() {
        ArrayList<BarData> dataList = new ArrayList<>();

        BarData data = new BarData("Янв.", 3.4f, "3.4€");
        dataList.add(data);
        data = new BarData("Фев.", 8f, "8€");
        dataList.add(data);
        data = new BarData("Мар.", 1.8f, "1.8€");
        dataList.add(data);
        data = new BarData("Апр.", 7.3f, "7.3€");
        dataList.add(data);
        data = new BarData("Май", 6.2f, "6.2€");
        dataList.add(data);
        data = new BarData("Июн.", 3.3f, "3.3€");
        dataList.add(data);
        data = new BarData("Июл.", 3.3f, "3.3€");
        dataList.add(data);
        data = new BarData("Авг.", 3.3f, "3.3€");
        dataList.add(data);
        data = new BarData("Сен.", 3.3f, "3.3€");
        dataList.add(data);
        data = new BarData("Окт.", 3.3f, "3.3€");
        dataList.add(data);
        data = new BarData("Ноя.", 3.3f, "3.3€");
        dataList.add(data);
        data = new BarData("Дек.", 3.3f, "3.3€");
        dataList.add(data);

        mChart.setDataList(dataList);
        mChart.build();

    }

    private void init() {
        Resources res = getResources();

        Runnable runnable = () -> {
            List<CategoryModel> models = new LinkedList<>();
            models.add(new CategoryModel(res.getColor(R.color.colorGradientStart), "Автомобиль", Color.WHITE));
            models.add(new CategoryModel(res.getColor(R.color.colorGradientEnd), "Комунальные услуги",Color.WHITE));
            models.add(new CategoryModel(res.getColor(R.color.colorLightGray), "Питание",Color.BLACK));
            models.add(new CategoryModel(res.getColor(R.color.colorLightGreen), "Дети",Color.BLACK));
            models.add(new CategoryModel(res.getColor(R.color.colorLightYellow), "Животные",Color.BLACK));
            List<StatMonthModel> monthModels = new LinkedList<>();
            monthModels.add(new StatMonthModel(Month.getMonthName(Month.January, getContext()), "\u20BD - 20 000,00"));
            monthModels.add(new StatMonthModel(Month.getMonthName(Month.February, getContext()), "\u20BD - 25 400,00"));
            monthModels.add(new StatMonthModel(Month.getMonthName(Month.March, getContext()), "\u20BD - 40 051,00"));
            monthModels.add(new StatMonthModel(Month.getMonthName(Month.April, getContext()), "\u20BD - 20 300,00"));
            monthModels.add(new StatMonthModel(Month.getMonthName(Month.May, getContext()), "\u20BD - 20 600,00"));
            monthModels.add(new StatMonthModel(Month.getMonthName(Month.June, getContext()), "\u20BD - 20 312,00"));
            monthModels.add(new StatMonthModel(Month.getMonthName(Month.July, getContext()), "\u20BD - 23 0120,00"));
            monthModels.add(new StatMonthModel(Month.getMonthName(Month.August, getContext()), "\u20BD - 70 330,00"));
            monthModels.add(new StatMonthModel(Month.getMonthName(Month.September, getContext()), "\u20BD - 15 000,00"));
            monthModels.add(new StatMonthModel(Month.getMonthName(Month.October, getContext()), "\u20BD - 10 000,00"));
            monthModels.add(new StatMonthModel(Month.getMonthName(Month.November, getContext()), "\u20BD - 60 000,00"));
            monthModels.add(new StatMonthModel(Month.getMonthName(Month.December, getContext()), "\u20BD - 80 000,00"));

            StatisticsFragment.this.getActivity().runOnUiThread(() -> {
                recyclerView.setAdapter(new CategoryStatAdapter(models, getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                monthRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                MonthStatAdapter monthStatAdapter = new MonthStatAdapter(monthModels, getContext());
                monthStatAdapter.setmListener(this::onMonthClicked);
                monthRecycler.setAdapter(monthStatAdapter);
            });
        };
        Thread thread = new Thread(runnable);
        thread.start();



        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, fakeYears);
        yearSpinner.setAdapter(spinnerArrayAdapter);


    }

    private void onMonthClicked(StatMonthModel monthModel) {
        Intent i = new Intent(getContext(), MonthStatActivity.class);
        startActivity(i);
    }

    private void showDatePicker() {
        datePicker.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager());
    }

}
