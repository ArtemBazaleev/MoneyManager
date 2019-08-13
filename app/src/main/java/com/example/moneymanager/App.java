package com.example.moneymanager;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.moneymanager.database.RoomDB;
import com.example.moneymanager.utils.Utility;

import java.util.concurrent.Executors;

public class App extends Application {
    public static App instance;

    private RoomDB database;

    @Override
    public void onCreate() {
        super.onCreate();
        RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                Executors.newSingleThreadScheduledExecutor().execute(() -> {
                    getDatabase().dbIconInteraction()
                            .insertData(Utility.getIcons());
                    getDatabase().dbCategoryInteraction()
                            .addCategory(Utility.getCategories(getDatabase().dbIconInteraction()));
                });

            }
        };
        instance = this;
        database = Room.databaseBuilder(this, RoomDB.class, "database")
                .addCallback(rdc)
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public RoomDB getDatabase() {
        return database;
    }
}
