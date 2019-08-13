package com.example.moneymanager.ui;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.moneymanager.R;
import com.example.moneymanager.adapters.HistoryAdapter;
import com.example.moneymanager.model.HistoryModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
// TODO: 2019-08-13 add moxy

public class HistoryFragment extends MvpAppCompatFragment {

    @BindView(R.id.recycler_history)
    RecyclerView recyclerHistory;
    @BindView(R.id.filter_btn)
    ImageView filterBtn;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, v);
        init();
        return v;
    }
    //Fake init just to see design
    private void init() {
        List<HistoryModel> models = new ArrayList<>();
        models.add(new HistoryModel(HistoryModel.TYPE_TOTAL));
        for (int i = 0; i<4; i++)
            models.add(new HistoryModel(HistoryModel.TYPE_HISTORY));
        models.add(new HistoryModel(HistoryModel.TYPE_TOTAL));
        for (int i = 0; i<4; i++)
            models.add(new HistoryModel(HistoryModel.TYPE_HISTORY));
        models.add(new HistoryModel(HistoryModel.TYPE_TOTAL));
        for (int i = 0; i<4; i++)
            models.add(new HistoryModel(HistoryModel.TYPE_HISTORY));
        HistoryAdapter adapter = new HistoryAdapter(getContext(), models, this::onHistoryItemClicked);
        recyclerHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerHistory.setAdapter(adapter);

        filterBtn.setOnClickListener(l-> startActivity(new Intent(getContext(), FilterActivity.class)));
    }

    private void onHistoryItemClicked() {

    }

}
