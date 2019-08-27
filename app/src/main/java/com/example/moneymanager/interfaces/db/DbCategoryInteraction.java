package com.example.moneymanager.interfaces.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.moneymanager.model.dbModel.DbCategory;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface DbCategoryInteraction {

    @Query("SELECT * FROM dbcategory WHERE categoryName != :name")
    Flowable<List<DbCategory>> getAllCategories(String name);

    @Query("SELECT * FROM dbcategory WHERE isIncome = 0")
    Flowable<List<DbCategory>> getAllOutComeCategories();

    @Query("SELECT * FROM dbcategory WHERE isIncome = 0 AND categoryName != :name")
    Flowable<List<DbCategory>> getAllOutComeCategories(String name);


    @Query("SELECT * FROM dbcategory WHERE isIncome = 1")
    Flowable<List<DbCategory>> getAllInComeCategories();

    @Query("SELECT * FROM dbcategory WHERE isIncome = 1 AND categoryName != :name")
    Flowable<List<DbCategory>> getAllInComeCategories(String name);

    @Update
    void updateCategory(DbCategory category);

    @Update
    void updateCategory(List<DbCategory> category);

    @Insert
    void addCategory(DbCategory category);

    @Insert
    void addCategory(List<DbCategory> categories);

    @Delete
    void deleteCategory(DbCategory category);
}
