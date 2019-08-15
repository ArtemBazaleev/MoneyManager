package com.example.moneymanager.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moneymanager.R;
import com.example.moneymanager.adapters.AccountAdapter;
import com.example.moneymanager.adapters.CategoryAdapter;
import com.example.moneymanager.custom.CustomSelector;
import com.example.moneymanager.model.AccountModel;
import com.example.moneymanager.model.CategoryModel;
import com.example.moneymanager.utils.SimpleItemTouchHelperCallback;
import com.example.moneymanager.utils.Utility;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventActionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */

public class EventActionsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    @BindView(R.id.cardView_category) CardView categoryCard;
    @BindView(R.id.account_card) CardView cardAccount;
    @BindView(R.id.cardView) CardView dateCard;
    @BindView(R.id.editText) EditText noteEditText;
    @BindView(R.id.editText2) EditText sumEditText;
    @BindView(R.id.imageView3) ImageView categoryImg;
    @BindView(R.id.textView15) TextView categoryTxy;
    @BindView(R.id.imageView_account_card) ImageView accountImg;
    @BindView(R.id.textView16) TextView accountTxt;
    @BindView(R.id.date_event_action) TextView dateTxt;
    @BindView(R.id.customSelector) CustomSelector selector;

    public EventActionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_event_actions, container, false);
        ButterKnife.bind(this, v);
        init();
        return  v;
    }

    private void init() {

        categoryCard.setOnClickListener((l)-> {
            mListener.onCategoryClicked();
        });
        cardAccount.setOnClickListener(l->{
            mListener.onAccountClicked();
        });

        dateCard.setOnClickListener(l->showDateTimeDialog());
        selector.setmListener(mListener::isIncome);

        noteEditText.setOnClickListener(l-> mListener.hideBottomSheet());
        noteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mListener.onNoteTextChanged(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sumEditText.setOnClickListener(l-> mListener.hideBottomSheet());
        sumEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0)
                    mListener.onSumChanged(Double.parseDouble(charSequence.toString()));
                else mListener.onSumChanged(0.0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        noteEditText.setOnFocusChangeListener((view, b) -> {
            if (b)
                mListener.hideBottomSheet();
        });
        sumEditText.setOnFocusChangeListener((view, b) -> {
            if (b)
                mListener.hideBottomSheet();
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onAccountClicked();
        void onCategoryClicked();
        void hideKeyboard();
        void hideBottomSheet();
        void onNoteTextChanged(String note);
        void onDateChosen(Long date);
        void onSumChanged(double sum);
        void isIncome(boolean isIncome);
    }

    private void showDateTimeDialog(){
        SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                getString(R.string.chose_date_txt),
                "OK",
                "Cancel"
        );
        dateTimeDialogFragment.startAtCalendarView();
        dateTimeDialogFragment.set24HoursMode(true);
        dateTimeDialogFragment.setMaximumDateTime(Calendar.getInstance().getTime());
        // Define new day and month format
        try {
            dateTimeDialogFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat(
                    "dd MMMM", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            e.printStackTrace();
        }

        // Set listener
        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                mListener.onDateChosen(date.getTime());
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Date is get on negative button click
            }
        });
        dateTimeDialogFragment.show(getActivity().getSupportFragmentManager(), "dialog_time");
    }

    public void setDate(Long date){
        dateTxt.setText(Utility.formatDate(date));
    }

    public void setCategory(CategoryModel category,boolean requestFocus){
        categoryImg.setImageDrawable(getResources().getDrawable(Utility.getResId(category.getIconId(), R.drawable.class)));
        categoryTxy.setText(category.getName());
        if (requestFocus) {
            sumEditText.requestFocus();
            InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).showSoftInput(sumEditText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void setAccount(AccountModel account){
        accountImg.setImageDrawable(getResources().getDrawable(Utility.getResId(account.getIcon(), R.drawable.class)));
        accountTxt.setText(account.getName());
    }

}
