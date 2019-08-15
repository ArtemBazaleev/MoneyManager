package com.example.moneymanager.model;

import com.example.moneymanager.model.dbModel.DbTransaction;
import com.example.moneymanager.utils.Utility;

public class HistoryModel {
    public static final int TYPE_HISTORY = 321;
    public static final int TYPE_TOTAL = 123;

    private int type = TYPE_HISTORY;
    private DbTransaction transaction;
    private boolean isIncome;
    private double sum = 0.0;
    private String date;
    private String note;

    public HistoryModel(int type) {
        this.type = type;
    }

    public HistoryModel(DbTransaction transaction){
        this.transaction = transaction;
        type = TYPE_HISTORY;
        isIncome = transaction.isIncome == 1;
        sum = transaction.sum;
        date = Utility.formatDate(transaction.date);

        note = transaction.note;
    }

    public DbTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(DbTransaction transaction) {
        this.transaction = transaction;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
