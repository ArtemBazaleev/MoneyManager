package com.example.moneymanager.ui;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
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
import com.example.moneymanager.model.AccountModel;
import com.example.moneymanager.model.CategoryModel;
import com.example.moneymanager.model.FilterModel;
import com.example.moneymanager.presentation.presenter.FilterActivityPresenter;
import com.example.moneymanager.presentation.view.FilterActivityView;
import com.example.moneymanager.utils.Utility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterActivity extends MvpAppCompatActivity implements FilterActivityView {
    public static final String TAG = "FilterActivity";

    @BindView(R.id.filter_category) ConstraintLayout category;
    @BindView(R.id.select_date_constraint) ConstraintLayout selectDate;
    @BindView(R.id.textView_to_date) TextView toDate;
    @BindView(R.id.textView_from_date) TextView fromDate;
    @BindView(R.id.recycler_account) RecyclerView recyclerAccount;
    @BindView(R.id.constraintLayout6) ConstraintLayout categoryBtn;
    @BindView(R.id.imageView3) ImageView categoryImage;
    @BindView(R.id.textView16) TextView categoryText;
    @BindView(R.id.imageView11) ImageView delCategory;
    @BindView(R.id.radioGroup) RadioGroup radioGroup;
    @BindView(R.id.clearFilterBtn) ConstraintLayout clearFilterBtn;
    @BindView(R.id.constraintLayout7) ConstraintLayout dateLayout;
    @BindView(R.id.imageView13) ImageView delDateBtn;
    @BindView(R.id.button3) Button saveBtn;

    private PrimeDatePickerBottomSheet datePicker;
    private BottomSheetCategoryFragment categoryFragment;
    private AccountAdapter accountAdapter;


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
        App app = (App) getApplication();
        presenter.onCreate(app.getFilterHistory());
    }

    private void init() {
        CalendarType calendarType = CalendarType.CIVIL;
        PrimeCalendar today = CalendarFactory.newInstance(calendarType);
        datePicker = PrimeDatePickerBottomSheet.newInstance(
                today,
                null ,
                today ,
                PickType.RANGE_START,
                null,
                new CivilCalendar(),
                new CivilCalendar(),
                "fonts/open_sans_ligh.ttf"
        );

        categoryBtn.setOnClickListener(v-> presenter.showCategoryClicked());
        delCategory.setOnClickListener(v-> presenter.onDelCategoryClicked());
        clearFilterBtn.setOnClickListener(v-> presenter.clearFilter());
        delDateBtn.setOnClickListener(v->presenter.onDelDateClicked());
        saveBtn.setOnClickListener(v -> presenter.onSaveClicked());
        datePicker.setOnDateSetListener(new PrimeDatePickerBottomSheet.OnDayPickedListener() {
            @Override
            public void onSingleDayPicked(@NonNull PrimeCalendar singleDay) {
            }

            @Override
            public void onRangeDaysPicked(@NonNull PrimeCalendar startDay, @NonNull PrimeCalendar endDay) {
                presenter.onRangeDaysPicked(startDay, endDay);
            }
        });

        selectDate.setOnClickListener(l->presenter.onSelectDate());
        radioGroup.setOnCheckedChangeListener(checkedChangeListener);


    }

    //MVP methods
    @Override
    public void showToastyMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
    public void setSelectedAccount(AccountModel account) {
        accountAdapter.setSelected(account);
    }

    @Override
    public void showDateTimePicker() {
        datePicker.show(getSupportFragmentManager());
    }

    @Override
    public void showCategories(String mode) {
        categoryFragment = BottomSheetCategoryFragment.newInstance(mode);
        categoryFragment.setmListener(presenter::onCategoryClicked);
        this.categoryFragment.show(getSupportFragmentManager(),"category");
    }

    @Override
    public void setEnabledCategory(boolean enabled) {
        if (enabled)
            category.setVisibility(View.VISIBLE);
        else category.setVisibility(View.GONE);
    }

    @Override
    public void setCategory(CategoryModel model) {
        categoryText.setText(model.getName());
        categoryImage.setImageDrawable(
                getResources().getDrawable(Utility.getResId(model.getIconId(), R.drawable.class))
        );
    }

    @Override
    public void setFromToDate(String from, String to) {
        fromDate.setText(String.format("c %s", from));
        toDate.setText(String.format("до %s", to));
    }

    @Override
    public void clearCheck() {
        radioGroup.check(R.id.radioButtonAll);
    }

    @Override
    public void setEnabledDateLayout(boolean enabledDateLayout) {
        if (enabledDateLayout)
            dateLayout.setVisibility(View.VISIBLE);
        else dateLayout.setVisibility(View.GONE);
    }

    @Override
    public void stopSelf(FilterModel filter) {
        App app = (App) getApplication();
        app.setFilterHistory(filter);
        finish();
    }

    @Override
    public void setIncomeSelected() {
        radioGroup.check(R.id.radioButton);
    }

    @Override
    public void setOutcomeSelected() {
        radioGroup.check(R.id.radioButton2);
    }

    private RadioGroup.OnCheckedChangeListener checkedChangeListener = (radioGroup, i)->{
        switch (i){
            case R.id.radioButton:
                presenter.onIncome();
                Log.d(TAG, "checkedChangeListener: radioButton");
                break;
            case R.id.radioButton2:
                presenter.onOutCome();
                Log.d(TAG, "checkedChangeListener: radioButton2");
                break;
            case R.id.radioButtonAll:
                presenter.onIncomeAndOutCome();
                break;
        }
    };
}
