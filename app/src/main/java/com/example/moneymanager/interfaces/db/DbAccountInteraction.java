package com.example.moneymanager.interfaces.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moneymanager.model.AccountModel;
import com.example.moneymanager.model.dbModel.DbAccount;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface DbAccountInteraction {

    @Query("SELECT * FROM DbAccount")
    Flowable<List<DbAccount>> getAllAccounts();

    @Insert
    void addAccount(DbAccount accountModel);

    @Insert
    void addAccount(List<DbAccount> accounts);

    @Delete
    void deleteAccount(DbAccount accountModel);
}
