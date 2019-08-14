package com.example.moneymanager.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.adapters.CategoryAdapter;
import com.example.moneymanager.adapters.IconAdapter;
import com.example.moneymanager.model.CategoryModel;
import com.example.moneymanager.model.IconModel;
import com.example.moneymanager.utils.Utility;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomSheetImageFragment extends BottomSheetDialogFragment {

    @BindView(R.id.recycler_account) RecyclerView recyclerView;
    private IOnCategoryModelClicked mListener;


    public static BottomSheetImageFragment newInstance() {
        return new BottomSheetImageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_bottom_sheet_category, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), Utility.calculateNoOfColumns(getContext(), 56)));
        List<IconModel> models = new LinkedList<>();
        for (int i=0; i<50; i++)
            models.add(new IconModel());
        IconAdapter adapter = new IconAdapter(models, getContext());
        adapter.setmListener(this::onCategoryClicked);
        recyclerView.setAdapter(adapter);

        return view;

    }

    private void onCategoryClicked(IconModel iconModel) {
        if (mListener!=null)
            mListener.onCategoryClicked(iconModel);
        dismiss();
    }

    public interface IOnCategoryModelClicked{
        void onCategoryClicked(IconModel model);
    }

    public IOnCategoryModelClicked getmListener() {
        return mListener;
    }

    public void setmListener(IOnCategoryModelClicked mListener) {
        this.mListener = mListener;
    }

}
