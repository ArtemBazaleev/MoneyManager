package com.example.moneymanager.model.dbModel;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DbAccount {
//    public static final String dbId = "id";
//    public static final String dbName = "name";
//    public static final String dbDrawableIcon = "icon";

    @PrimaryKey(autoGenerate = true)
    public int accountId;
    public String accountName;
    @Embedded
    public DbIcon drawableIcon;

    public DbAccount() {
    }

    public DbAccount(String accountName, DbIcon drawableIcon) {
        this.accountName = accountName;
        this.drawableIcon = drawableIcon;
    }
}
