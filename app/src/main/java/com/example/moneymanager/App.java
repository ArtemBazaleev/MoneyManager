package com.example.moneymanager;

import android.app.Application;

import androidx.room.Room;

import com.example.moneymanager.database.RoomDB;

public class App extends Application {
    public static App instance;

    private RoomDB database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, RoomDB.class, "database")
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public RoomDB getDatabase() {
        return database;
    }
}
