package com.example.moneymanager.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.moneymanager.interfaces.db.DataBaseGoalContract;
import com.example.moneymanager.interfaces.db.DataBaseTransactionContract;
import com.example.moneymanager.interfaces.db.DbAccountInteraction;
import com.example.moneymanager.interfaces.db.DbCategoryInteraction;
import com.example.moneymanager.interfaces.db.DbGoalTransactionInteraction;
import com.example.moneymanager.interfaces.db.DbIconInteraction;
import com.example.moneymanager.model.dbModel.DbAccount;
import com.example.moneymanager.model.dbModel.DbCategory;
import com.example.moneymanager.model.dbModel.DbGoal;
import com.example.moneymanager.model.dbModel.DbGoalTransaction;
import com.example.moneymanager.model.dbModel.DbIcon;
import com.example.moneymanager.model.dbModel.DbTransaction;

@Database(entities = {DbGoal.class, DbTransaction.class, DbAccount.class, DbCategory.class,
        DbGoalTransaction.class, DbIcon.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    public abstract DataBaseGoalContract dataBaseGoalContract();
    public abstract DataBaseTransactionContract dataBaseTransactionContract();
    public abstract DbAccountInteraction dbAccountInteraction();
    public abstract DbCategoryInteraction dbCategoryInteraction();
    public abstract DbGoalTransactionInteraction dbGoalTransactionInteraction();
    public abstract DbIconInteraction dbIconInteraction();
}