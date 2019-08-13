package com.example.moneymanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.moneymanager.R;
import com.example.moneymanager.adapters.HistoryAdapter;
import com.example.moneymanager.model.HistoryModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO: 2019-08-13 add moxy

public class MonthStatActivity extends AppCompatActivity {
    @BindView(R.id.recycler_history_stat)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_stat);
        ButterKnife.bind(this);
        init();
    }

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
        HistoryAdapter adapter = new HistoryAdapter(this, models, this::onHistoryItemClicked);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void onHistoryItemClicked() {

    }
}
