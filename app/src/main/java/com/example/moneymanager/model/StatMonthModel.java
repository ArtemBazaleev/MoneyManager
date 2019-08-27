package com.example.moneymanager.model;

import java.util.Date;

public class StatMonthModel {
    public static final String TYPE_INCOME = "income";
    public static final String TYPE_OUTCOME = "outcome";
    private String name;
    private double value;
    private Date date;
    private String type;
    private int year;
    private int month;

    public StatMonthModel(String name, float value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public StatMonthModel(String name, float value, String type,int month) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.month = month;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
