package com.example.moneymanager.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primecalendar.civil.CivilCalendar;
import com.aminography.primecalendar.common.CalendarFactory;
import com.aminography.primecalendar.common.CalendarType;
import com.aminography.primedatepicker.PickType;
import com.aminography.primedatepicker.fragment.PrimeDatePickerBottomSheet;
import com.example.moneymanager.R;
import com.example.moneymanager.adapters.CategoryAdapter;
import com.example.moneymanager.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO: 2019-08-13 add Moxy
public class FilterActivity extends AppCompatActivity {
    @BindView(R.id.filter_category) RecyclerView recyclerCategoryFilter;
    @BindView(R.id.select_date_constraint) ConstraintLayout selectDate;
    @BindView(R.id.textView_to_date) TextView toDate;
    @BindView(R.id.textView_from_date) TextView fromDate;
    @BindView(R.id.recycler_account) RecyclerView recyclerAccount;
    private PrimeDatePickerBottomSheet datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        init();
    }

    //Fake init
    private void init() {
        List<CategoryModel> models = new ArrayList<>();
        for (int i=0; i<10; i++)
            models.add(new CategoryModel());

        recyclerCategoryFilter.setAdapter(new CategoryAdapter(models, this));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerCategoryFilter.setLayoutManager(manager);

        LinearLayoutManager managerAccount = new LinearLayoutManager(this);
        managerAccount.setOrientation(RecyclerView.HORIZONTAL);
        recyclerAccount.setLayoutManager(managerAccount);
        recyclerAccount.setAdapter(new CategoryAdapter(models, this));


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
            }
        });

        selectDate.setOnClickListener(l->showDatePicker());

    }


    private void showDatePicker() {
        datePicker.show(getSupportFragmentManager());
    }
}
