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
import android.widget.Toast;

import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primecalendar.civil.CivilCalendar;
import com.aminography.primecalendar.common.CalendarFactory;
import com.aminography.primecalendar.common.CalendarType;
import com.aminography.primedatepicker.PickType;
import com.aminography.primedatepicker.fragment.PrimeDatePickerBottomSheet;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.moneymanager.App;
import com.example.moneymanager.R;
import com.example.moneymanager.adapters.AccountAdapter;
import com.example.moneymanager.adapters.CategoryAdapter;
import com.example.moneymanager.model.AccountModel;
import com.example.moneymanager.model.CategoryModel;
import com.example.moneymanager.presentation.FilterActivityPresenter;
import com.example.moneymanager.presentation.FilterActivityView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterActivity extends MvpAppCompatActivity implements FilterActivityView {

    @BindView(R.id.filter_category) RecyclerView recyclerCategoryFilter;
    @BindView(R.id.select_date_constraint) ConstraintLayout selectDate;
    @BindView(R.id.textView_to_date) TextView toDate;
    @BindView(R.id.textView_from_date) TextView fromDate;
    @BindView(R.id.recycler_account) RecyclerView recyclerAccount;
    private PrimeDatePickerBottomSheet datePicker;
    private AccountAdapter accountAdapter;
    private CategoryAdapter categoryAdapter;

    @InjectPresenter
    FilterActivityPresenter presenter;

    @ProvidePresenter
    FilterActivityPresenter providePresenter(){
        App app = (App) getApplication();
        return new FilterActivityPresenter(app.getDatabase().dbAccountInteraction(),
                app.getDatabase().dbCategoryInteraction());
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        init();
        presenter.onCreate();
    }

    //Fake init
    private void init() {

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

        selectDate.setOnClickListener(l->presenter.onSelectDate());

    }

    private void showDatePicker() {
        datePicker.show(getSupportFragmentManager());
    }
    //MVP methods
    @Override
    public void showToastyMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initCategory(List<CategoryModel> categories) {
        categoryAdapter = new CategoryAdapter(categories, this);
        categoryAdapter.setmListener(presenter::onCategoryClicked);
        recyclerCategoryFilter.setAdapter(categoryAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerCategoryFilter.setLayoutManager(manager);
    }

    @Override
    public void initAccount(List<AccountModel> accounts) {
        LinearLayoutManager managerAccount = new LinearLayoutManager(this);
        managerAccount.setOrientation(RecyclerView.HORIZONTAL);
        recyclerAccount.setLayoutManager(managerAccount);
        accountAdapter = new AccountAdapter(accounts, this, AccountAdapter.MODE_VERTICAL);
        accountAdapter.setmListener(presenter::onAccountClicked);
        recyclerAccount.setAdapter(accountAdapter);
    }

    @Override
    public void setSelectedCategory(CategoryModel category) {
        categoryAdapter.setSelected(category);
    }

    @Override
    public void setSelectedAccount(AccountModel account) {
        accountAdapter.setSelected(account);
    }

    @Override
    public void showDateTimePicker() {
        datePicker.show(getSupportFragmentManager());
    }
}
