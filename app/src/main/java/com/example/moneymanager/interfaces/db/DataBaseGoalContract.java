package com.example.moneymanager.interfaces.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.moneymanager.model.GoalModel;
import com.example.moneymanager.model.dbModel.DbGoal;

import java.util.List;
import io.reactivex.Flowable;

@Dao
public interface DataBaseGoalContract {

    @RawQuery
    List<DbGoal> getTransactions(SupportSQLiteQuery query);

    @Query("SELECT * FROM dbgoal")
    Flowable<List<DbGoal>> getAllGoals();
    @Insert
    void addTransaction(DbGoal goal);
    @Delete
    void deleteTransaction(DbGoal goal);
    @Query("SELECT * FROM dbgoal WHERE goalId = :goalID")
    Flowable<DbGoal> getGoalByID(int goalID);

    @Update
    void updateGoal(DbGoal goal);

}
