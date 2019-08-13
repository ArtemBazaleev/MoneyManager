package com.example.moneymanager.model;

public class CategoryModel {
    private int color;
    private int tintColor;
    private String name;

    public CategoryModel(int color , String name, int tintColor) {
        this.color = color;
        this.name = name;
        this.tintColor = tintColor;
    }

    public CategoryModel() {
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
}
