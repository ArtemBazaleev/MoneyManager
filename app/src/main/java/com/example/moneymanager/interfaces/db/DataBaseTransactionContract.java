package com.example.moneymanager.interfaces.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import com.example.moneymanager.model.dbModel.DbTransaction;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class DataBaseTransactionContract {

    @RawQuery
    public abstract List<DbTransaction> getTransactions(SupportSQLiteQuery query);

    @Insert
    public abstract void addTransaction(DbTransaction transaction);

    @Delete
    public abstract void delete(DbTransaction transaction);

    @Query("SELECT * FROM DbTransaction")
    public abstract Flowable<List<DbTransaction>> getAllTransactions();

    @Query("SELECT * FROM dbtransaction WHERE date>=:fromDate AND date <=:toDate")
    public abstract Flowable<List<DbTransaction>> getDataWithRange(Long fromDate, Long toDate);

}
