package com.example.moneymanager.model;

import com.example.moneymanager.model.dbModel.DbGoal;

public class GoalModel {
    private int progress = 35;
    private DbGoal goal;

    public GoalModel(DbGoal dbGoal) {
        goal = dbGoal;
    }

    public GoalModel() {
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public DbGoal getGoal() {
        return goal;
    }

    public void setGoal(DbGoal goal) {
        this.goal = goal;
    }
}
