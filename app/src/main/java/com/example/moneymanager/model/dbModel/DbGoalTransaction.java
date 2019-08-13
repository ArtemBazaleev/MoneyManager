package com.example.moneymanager.model.dbModel;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DbGoalTransaction {

//    public static final String dbId = "id";
//    public static final String dbSum = "sum";
//    public static final String dbDate = "date";
//    public static final String dbAccount = "account";
//    public static final String dbGoal = "goal";

    @PrimaryKey(autoGenerate = true)
    public int goalTransactionId;
    public double sum;
    public long goalTransactionDate;
    @Embedded
    public DbAccount accountID;
    @Embedded
    public DbGoal goalID;

}
