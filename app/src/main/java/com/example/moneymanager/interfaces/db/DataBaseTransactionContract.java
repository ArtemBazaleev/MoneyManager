package com.example.moneymanager.interfaces.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import com.example.moneymanager.model.dbModel.DbTransaction;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface DataBaseTransactionContract {

    @RawQuery
    List<DbTransaction> getTransactions(SupportSQLiteQuery query);

    @Insert
    void addTransaction(DbTransaction transaction);

    @Delete
    void delete(DbTransaction transaction);
}
