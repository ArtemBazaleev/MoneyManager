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
import android.widget.ImageView;

import com.example.moneymanager.R;
import com.example.moneymanager.adapters.AccountAdapter;
import com.example.moneymanager.adapters.CategoryAdapter;
import com.example.moneymanager.model.AccountModel;
import com.example.moneymanager.model.CategoryModel;
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


// TODO: 2019-08-13 add Moxy
public class EventActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener,
        EventActionsFragment.OnFragmentInteractionListener{
    @BindView(R.id.recycler_category) RecyclerView recyclerView;
    @BindView(R.id.recycler_account) RecyclerView recyclerViewAccounts;

    private BottomSheetBehavior mBottomSheetBehavior;
    private BottomSheetBehavior bottomSheetBehaviorAccount;
    private EventActionsFragment eventActionsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);
        init();
        hideKeyboard();
    }

    //fake init
    private void init() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, Utility.calculateNoOfColumns(this)));
        List<CategoryModel> models = new LinkedList<>();
        for (int i=0; i<50; i++)
            models.add(new CategoryModel());
        CategoryAdapter adapter = new CategoryAdapter(models, this);
        adapter.setmListener(this::onCategoryClicked);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        View v = findViewById(R.id.bottom_sheet_category);
        View v1 = findViewById(R.id.bottom_sheet_account);
        mBottomSheetBehavior = BottomSheetBehavior.from(v);
        bottomSheetBehaviorAccount = BottomSheetBehavior.from(v1);
        bottomSheetBehaviorAccount.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehaviorAccount.setBottomSheetCallback(bottomSheetCallback);
        mBottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);

        eventActionsFragment = new EventActionsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_events, eventActionsFragment)
                .commit();


        List<AccountModel> mData = new LinkedList<>();
        for (int i=0; i<5; i++)
            mData.add(new AccountModel());
        AccountAdapter adapter1 = new AccountAdapter(mData, this);
        adapter1.setmListener(this::onAccountClicked);
        recyclerViewAccounts.setAdapter(adapter1);
        recyclerViewAccounts.setLayoutManager(new LinearLayoutManager(this));
    }

    private void onAccountClicked(AccountModel model) {
        hideBottomSheet();
        eventActionsFragment.onAccountChosen(model);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void onCategoryClicked(CategoryModel model) {
        hideBottomSheet();
        eventActionsFragment.onCategoryChosen(model);
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
}
