package com.example.moneymanager.model;

import java.util.Date;

public class StatMonthModel {
    private String name;
    private String value;
    private Date date;

    public StatMonthModel(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
