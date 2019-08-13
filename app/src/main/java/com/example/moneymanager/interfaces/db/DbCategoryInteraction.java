package com.example.moneymanager.interfaces.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moneymanager.model.dbModel.DbCategory;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface DbCategoryInteraction {

    @Query("SELECT * FROM dbcategory")
    Flowable<List<DbCategory>> getAllCategories();

    @Insert
    void addCategory(DbCategory category);

    @Insert
    void addCategory(List<DbCategory> categories);

    @Delete
    void deleteCategory(DbCategory category);
}
