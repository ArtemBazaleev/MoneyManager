package com.example.moneymanager.model;

import com.example.moneymanager.model.dbModel.DbGoalTransaction;

public class HistoryGoalModel {
    DbGoalTransaction transaction;

    public HistoryGoalModel(DbGoalTransaction i) {
        transaction = i;
    }

    public DbGoalTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(DbGoalTransaction transaction) {
        this.transaction = transaction;
    }
}
