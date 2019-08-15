package com.example.moneymanager.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.moneymanager.App;
import com.example.moneymanager.R;
import com.example.moneymanager.adapters.AccountAdapter;
import com.example.moneymanager.adapters.CategoryAdapter;
import com.example.moneymanager.model.AccountModel;
import com.example.moneymanager.model.CategoryModel;
import com.example.moneymanager.presentation.presenter.EventActivityPresenter;
import com.example.moneymanager.presentation.view.EventActivityView;
import com.example.moneymanager.utils.SimpleItemTouchHelperCallback;
import com.example.moneymanager.utils.Utility;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EventActivity extends MvpAppCompatActivity implements DatePickerDialog.OnDateSetListener,
        EventActivityView, EventActionsFragment.OnFragmentInteractionListener{

    @BindView(R.id.recycler_category) RecyclerView recyclerView;
    @BindView(R.id.recycler_account) RecyclerView recyclerViewAccounts;
    @BindView(R.id.button2) Button saveBtn;
    private BottomSheetBehavior mBottomSheetBehavior;
    private BottomSheetBehavior bottomSheetBehaviorAccount;
    private EventActionsFragment eventActionsFragment;

    @InjectPresenter
    EventActivityPresenter presenter;
    @ProvidePresenter
    EventActivityPresenter providePresenter(){
        App app = (App) getApplicationContext();
        return new EventActivityPresenter(
                app.getDatabase().dbCategoryInteraction(),
                app.getDatabase().dbAccountInteraction(),
                app.getDatabase().dataBaseTransactionContract()
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);
        init();
        hideKeyboard();
        presenter.initBottomSheet();
    }

    //fake init
    private void init() {
        View v = findViewById(R.id.bottom_sheet_category);
        View v1 = findViewById(R.id.bottom_sheet_account);
        mBottomSheetBehavior = BottomSheetBehavior.from(v);
        bottomSheetBehaviorAccount = BottomSheetBehavior.from(v1);
        bottomSheetBehaviorAccount.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehaviorAccount.setBottomSheetCallback(bottomSheetCallback);
        mBottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);
        saveBtn.setOnClickListener(l -> presenter.onSaveClicked());

        eventActionsFragment = new EventActionsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_events, eventActionsFragment)
                .commit();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    @Override
    public void onAccountClicked() {
        hideKeyboard();
        Handler handler = new Handler();
            handler.postDelayed(() -> {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                bottomSheetBehaviorAccount.setState(BottomSheetBehavior.STATE_EXPANDED);
            }, 300);
    }

    @Override
    public void onCategoryClicked() {
        hideKeyboard();
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            bottomSheetBehaviorAccount.setState(BottomSheetBehavior.STATE_HIDDEN);
            }, 300);

    }

    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View view, int i) {
            if (i == BottomSheetBehavior.STATE_EXPANDED){
                hideKeyboard();
            }

        }

        @Override
        public void onSlide(@NonNull View view, float v) {

        }
    };

    //MVP
    @Override
    public void showToastyMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadAccounts(List<AccountModel> accountModels) {
        AccountAdapter adapter1 = new AccountAdapter(accountModels, this);
        adapter1.setmListener(presenter::onAccountClicked);
        recyclerViewAccounts.setAdapter(adapter1);
        recyclerViewAccounts.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void loadCategories(List<CategoryModel> categoryModels) {
        recyclerView.setLayoutManager(new GridLayoutManager(this, Utility.calculateNoOfColumns(this)));
        CategoryAdapter adapter = new CategoryAdapter(categoryModels, this);
        adapter.setmListener(presenter::onCategoryClicked);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void requestPhotoOrGallery() {

    }

    @Override
    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void hideBottomSheet() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehaviorAccount.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void onNoteTextChanged(String note) {
        presenter.onNoteChanged(note);
    }

    @Override
    public void onDateChosen(Long date) {
        presenter.onDateChosen(date);
    }

    @Override
    public void onSumChanged(double sum) {
        presenter.onSumChanged(sum);
    }

    @Override
    public void isIncome(boolean isIncome) {
        presenter.setIncome(isIncome);
    }

    @Override
    public void onCategoryChosen(CategoryModel categoryModel, boolean requestFocus) {
        eventActionsFragment.setCategory(categoryModel, requestFocus);
    }

    @Override
    public void onAccountChosen(AccountModel accountModel) {
        eventActionsFragment.setAccount(accountModel);
    }

    @Override
    public void setCategoryShown(boolean shown) {
        if (shown)
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        else mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void setAccountShown(boolean shown) {
        if (shown)
            bottomSheetBehaviorAccount.setState(BottomSheetBehavior.STATE_EXPANDED);
        else bottomSheetBehaviorAccount.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void setDateFragment(Long date) {
        eventActionsFragment.setDate(date);
    }

    @Override
    public void stopSelf() {
        this.finish();
    }
}
