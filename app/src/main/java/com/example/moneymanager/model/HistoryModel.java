package com.example.moneymanager.model;

public class HistoryModel {
    public static final int TYPE_HISTORY = 321;
    public static final int TYPE_TOTAL = 123;

    private int type = TYPE_HISTORY;

    public HistoryModel(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
