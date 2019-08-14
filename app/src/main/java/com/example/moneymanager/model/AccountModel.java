package com.example.moneymanager.model;

import com.example.moneymanager.model.dbModel.DbAccount;

public class AccountModel {
    private boolean selected = false;
    private String name;
    private String icon;
    private DbAccount account;

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
        this.account = account;
    }

    public AccountModel() {
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public DbAccount getAccount() {
        return account;
    }

    public void setAccount(DbAccount account) {
        this.account = account;
    }
}
