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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventActionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */

// TODO: 2019-08-13 add moxy
public class EventActionsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    @BindView(R.id.cardView_category) CardView categoryCard;
    @BindView(R.id.account_card) CardView cardAccount;
    @BindView(R.id.cardView) CardView dateCard;
    @BindView(R.id.editText) EditText noteEditText;
    @BindView(R.id.editText2) EditText sumEditText;

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

        noteEditText.setOnClickListener(l-> mListener.hideBottomSheet());
        sumEditText.setOnClickListener(l-> mListener.hideBottomSheet());
        noteEditText.setOnFocusChangeListener((view, b) -> {
            if (b)
                mListener.hideBottomSheet();
        });
        sumEditText.setOnFocusChangeListener((view, b) -> {
            if (b)
                mListener.hideBottomSheet();
        });
    }

    void onAccountChosen(AccountModel model){

    }
    void onCategoryChosen(CategoryModel model){
        sumEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).showSoftInput(sumEditText, InputMethodManager.SHOW_IMPLICIT);
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

    }

    private void showDateTimeDialog(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this::onDateSelected,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        dpd.show(getActivity().getSupportFragmentManager(), "Datepickerdialog");
    }

    private void onDateSelected(DatePickerDialog datePickerDialog, int i, int i1, int i2) {

    }
}
