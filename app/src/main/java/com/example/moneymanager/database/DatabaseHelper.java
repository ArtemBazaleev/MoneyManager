package com.example.moneymanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "MoneyManagerDB";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL(DbRequest.CREATE_TRANSACTION_TABLE);
//        sqLiteDatabase.execSQL(DbRequest.CREATE_ACCOUNT_TABLE);
//        sqLiteDatabase.execSQL(DbRequest.CREATE_CATEGORY_TABLE);
//        sqLiteDatabase.execSQL(DbRequest.CREATE_GOAL_TABLE);
//        sqLiteDatabase.execSQL(DbRequest.CREATE_GOAL_TRANSACTION_TABLE);
//        sqLiteDatabase.execSQL(DbRequest.CREATE_ICON_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + DbRequest.TABLE_ACCOUNT + "'");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + DbRequest.TABLE_CATEGORY + "'");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + DbRequest.TABLE_TRANSACTION + "'");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + DbRequest.TABLE_GOAL + "'");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + DbRequest.TABLE_GOAL_TRANSACTION + "'");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + DbRequest.TABLE_ICON + "'");
        onCreate(sqLiteDatabase);
    }
}
