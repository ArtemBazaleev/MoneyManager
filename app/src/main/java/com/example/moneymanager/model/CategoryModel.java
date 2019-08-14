package com.example.moneymanager.model;

import com.example.moneymanager.model.dbModel.DbCategory;

public class CategoryModel {
    private int color;
    private int tintColor;
    private String icon;
    private String name;
    private boolean selected = false;
    private DbCategory category;

    public CategoryModel(int color , String name, int tintColor) {
        this.color = color;
        this.name = name;
        this.tintColor = tintColor;
    }

    public CategoryModel() {
    }

    public CategoryModel(DbCategory i) {
        icon = i.drawableIcon.drawableIcon;
        name = i.categoryName;
        category = i;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTintColor() {
        return tintColor;
    }

    public void setTintColor(int tintColor) {
        this.tintColor = tintColor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getIconId() {
        return icon;
    }

    public void setIconId(String iconId) {
        this.icon = iconId;
    }

    public DbCategory getCategory() {
        return category;
    }

    public void setCategory(DbCategory category) {
        this.category = category;
    }
}
