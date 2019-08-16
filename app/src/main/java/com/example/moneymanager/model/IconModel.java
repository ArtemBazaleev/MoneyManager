package com.example.moneymanager.model;

import com.example.moneymanager.model.dbModel.DbIcon;

public class IconModel {
    private DbIcon dbIcon;
    public IconModel(DbIcon i) {
        dbIcon = i;
    }

    public DbIcon getDbIcon() {
        return dbIcon;
    }

    public void setDbIcon(DbIcon dbIcon) {
        this.dbIcon = dbIcon;
    }
}
