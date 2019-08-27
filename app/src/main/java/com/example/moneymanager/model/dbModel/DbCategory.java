package com.example.moneymanager.model.dbModel;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DbCategory {

//    public static final String dbId = "id";
//    public static final String dbName = "name";
//    public static final String dbIsIncome = "isIncome";
//    public static final String dbDrawableIcon = "icon";

    @PrimaryKey(autoGenerate = true)
    public int categoryId;
    public String categoryName;
    public int isIncome;
    public int position;
    @Embedded
    public DbIcon drawableIcon;

    public DbCategory(String categoryName, int isIncome, DbIcon drawableIcon) {
        this.categoryName = categoryName;
        this.isIncome = isIncome;
        this.drawableIcon = drawableIcon;
    }

    public DbCategory(String categoryName, int isIncome, DbIcon drawableIcon, int position) {
        this.categoryName = categoryName;
        this.isIncome = isIncome;
        this.drawableIcon = drawableIcon;
        this.position = position;
    }

    public DbCategory() {

    }
}
