package com.example.moneymanager.model.dbModel;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DbTransaction {

//    public static final String dbId = "id";
//    public static final String dbNote = "note";
//    public static final String dbImage = "image";
//    public static final String dbDate = "date";
//    public static final String dbCategory = "category";
//    public static final String dbAccount = "account";
//    public static final String dbSum = "sum";

    @PrimaryKey(autoGenerate = true)
    public int transactionId;
    public String note;
    public String image;
    public long date;
    @Embedded(prefix = "type_")
    public DbCategory transactionCategoryID;
    @Embedded(prefix = "type_")
    public DbAccount transactionAccountID;
    public double sum;
    @Embedded(prefix = "type_")
    public DbIcon transactionIcon;

}
