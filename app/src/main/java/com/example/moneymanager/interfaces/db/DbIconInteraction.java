package com.example.moneymanager.interfaces.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moneymanager.model.dbModel.DbIcon;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface DbIconInteraction {
    @Query("SELECT * FROM dbicon")
    Flowable<List<DbIcon>> getAllIcons();

    @Query("SELECT * FROM dbicon WHERE iconName = :name")
    DbIcon getIconByName(String name);

    @Insert
    void insertData(DbIcon icon);

    @Insert
    void insertData(List<DbIcon> icons);
}
