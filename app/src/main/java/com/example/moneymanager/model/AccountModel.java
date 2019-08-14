package com.example.moneymanager.model;

import com.example.moneymanager.model.dbModel.DbAccount;

public class AccountModel {
    private boolean selected = false;
    private String name;
    private String icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public AccountModel(DbAccount account) {
        name = account.accountName;
        icon = account.drawableIcon.drawableIcon;
    }

    public AccountModel() {
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
