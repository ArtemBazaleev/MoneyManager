package com.example.moneymanager.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.moneymanager.App;
import com.example.moneymanager.R;
import com.example.moneymanager.interfaces.db.DbIconInteraction;
import com.example.moneymanager.model.dbModel.DbCategory;
import com.example.moneymanager.model.dbModel.DbIcon;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Utility {
    public static int calculateNoOfColumns(Context context) { // For example columnWidthdp=180
        float columnWidthDp = 80F;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (screenWidthDp / columnWidthDp + 0.5);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (screenWidthDp / columnWidthDp + 0.5);
    }

    public static List<DbCategory> getCategories(DbIconInteraction iconInteraction){
        List<DbCategory> categories = new LinkedList<>();
        categories.add(
                new DbCategory(
                        "Car", 0,  iconInteraction.getIconByName("car")));
        categories.add(new DbCategory(
                "Cash", 0, iconInteraction.getIconByName("coins")));
        return categories;
    }

    public static List<DbIcon> getIcons(){
        List<DbIcon> icons = new LinkedList<>();
        icons.add(new DbIcon("ic_car", "car"));
        icons.add(new DbIcon("ic_coins", "coins"));
        return icons;
    }

    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
