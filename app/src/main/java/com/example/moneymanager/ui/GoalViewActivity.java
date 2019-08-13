package com.example.moneymanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;

import com.example.moneymanager.R;
import com.example.moneymanager.adapters.HistoryGoalAdapter;
import com.example.moneymanager.model.HistoryGoalModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO: 2019-08-13 add moxy
public class GoalViewActivity extends AppCompatActivity {
    @BindView(R.id.recycler_history_goal)
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_view);
        ButterKnife.bind(this);
        List<HistoryGoalModel> models = new ArrayList<>();
        for (int i = 0; i<10; i++)
            models.add(new HistoryGoalModel());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new HistoryGoalAdapter(models, this));
    }

}
