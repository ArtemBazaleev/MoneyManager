package com.example.moneymanager.model.dbModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DbIcon {

//    public static final String dbId = "id";
//    public static final String dbDrawable = "drawableIcon";
//    public static final String dbName = "name";
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "icon_id")
    public int iconId;
    public String drawableIcon;
    public String iconName;
    public String iconColorHex;

    public DbIcon(String drawableIcon, String iconName, String iconColorHex) {
        this.drawableIcon = drawableIcon;
        this.iconName = iconName;
        this.iconColorHex = iconColorHex;
    }

    public DbIcon() {
    }
}
