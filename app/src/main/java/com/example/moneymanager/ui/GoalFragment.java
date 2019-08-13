package com.example.moneymanager.ui;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.moneymanager.R;
import com.example.moneymanager.adapters.GoalAdapter;
import com.example.moneymanager.model.GoalModel;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
// TODO: 2019-08-13 add moxy
public class GoalFragment extends MvpAppCompatFragment {
    @BindView(R.id.recycler_goals) RecyclerView recyclerView;
    @BindView(R.id.new_goal_btn)
    Button newGoalbtn;

    public GoalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_goal, container, false);
        init(v);
        return v;
    }

    private void init(View v) {
        ButterKnife.bind(this, v);
//        recyclerView = v.findViewById(R.id.recycler_goals);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<GoalModel> models = new LinkedList<>();
        for (int i = 0; i<25; i++)
            models.add(new GoalModel());
        recyclerView.setAdapter(new GoalAdapter(models, getContext(), this::onGoalClicked));

        newGoalbtn.setOnClickListener(l->{
            Intent i = new Intent(getContext(), GoalActivity.class);
            startActivity(i);
        });
    }

    private void onGoalClicked(GoalModel model) {
        Intent i = new Intent(getContext(), GoalViewActivity.class);
        startActivity(i);
    }

}
