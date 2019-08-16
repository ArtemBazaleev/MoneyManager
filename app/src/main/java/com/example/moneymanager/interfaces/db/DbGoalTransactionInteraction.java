package com.example.moneymanager.interfaces.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moneymanager.model.dbModel.DbGoalTransaction;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface DbGoalTransactionInteraction {

    @Query("SELECT * FROM DbGoalTransaction")
    Flowable<List<DbGoalTransaction>> getGoalTransactions();

    @Insert
    void addGoalTransaction(DbGoalTransaction transaction);

    @Delete
    void deleteGoalTransaction(DbGoalTransaction transaction);

    @Query("SELECT * FROM DbGoalTransaction WHERE date >= :date")
    Flowable<List<DbGoalTransaction>> getTimeTransaction(long date);

}
