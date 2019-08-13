package com.example.moneymanager.model.dbModel;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DbGoal {
//    public static final String dbId = "id";
//    public static final String dbName = "name";
//    public static final String dbNote = "note";
//    public static final String dbIcon = "icon";
//    public static final String dbSumTotal = "sum";
//    public static final String dbDate = "date";
//    public static final String dbBalance = "balance";
//    public static final String dbIsActive = "isActive";

    @PrimaryKey(autoGenerate = true)
    public int goalId;
    public String goalName;
    public String note;
    @Embedded(prefix = "goal_")
    public DbIcon iconID;
    public double sumTotal;
    public long date;
    public double balance;
    public boolean isActive;

}
