package com.example.moneymanager.model.dbModel;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DbIcon {

//    public static final String dbId = "id";
//    public static final String dbDrawable = "drawableIcon";
//    public static final String dbName = "name";
    @PrimaryKey(autoGenerate = true)
    public int iconId;
    public int drawableIcon;
    public int iconName;

}
