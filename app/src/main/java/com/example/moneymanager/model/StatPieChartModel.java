package com.example.moneymanager.model;

public class StatPieChartModel {
    public static final int MODE_OUTCOME = -1;
    public static final int MODE_INCOME = 1;
    private  int mode;

    public StatPieChartModel(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
