package com.example.moneymanager.model;

import java.util.ArrayList;
import java.util.List;

public class StatPieChartModel {
    public static final int MODE_OUTCOME = -1;
    public static final int MODE_INCOME = 1;
    private  int mode;

    private double[] xData;
    private List<String> colors = new ArrayList<>();

    public StatPieChartModel(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public double[] getxData() {
        return xData;
    }

    public void setxData(double[] xData) {
        this.xData = xData;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }
}
