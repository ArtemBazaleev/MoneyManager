package com.example.moneymanager.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.moneymanager.App;
import com.example.moneymanager.R;
import com.example.moneymanager.database.RoomDB;
import com.example.moneymanager.interfaces.db.DbIconInteraction;
import com.example.moneymanager.model.dbModel.DbAccount;
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

    public static List<DbCategory> getCategories(DbIconInteraction iconInteraction){ //тут добавить категории
        List<DbCategory> categories = new LinkedList<>();
        categories.add(
                new DbCategory(
                        "Food", 0,  iconInteraction.getIconByName("food")));
        categories.add(
                new DbCategory(
                        "Transport", 0,  iconInteraction.getIconByName("transport")));
        categories.add(
                new DbCategory(
                        "Bills", 0,  iconInteraction.getIconByName("bills")));
        categories.add(
                new DbCategory(
                        "Car", 0,  iconInteraction.getIconByName("car")));
        categories.add(
                new DbCategory(
                        "Shopping", 0,  iconInteraction.getIconByName("shopping")));
        categories.add(
                new DbCategory(
                        "Clothing", 0,  iconInteraction.getIconByName("clothing")));
        categories.add(
                new DbCategory(
                        "Health", 0,  iconInteraction.getIconByName("health")));
        categories.add(
                new DbCategory(
                        "Sport", 0,  iconInteraction.getIconByName("sport")));
        categories.add(
                new DbCategory(
                        "Baby", 0,  iconInteraction.getIconByName("baby")));
        categories.add(
                new DbCategory(
                        "Fastfood", 0,  iconInteraction.getIconByName("fastfood")));
        categories.add(
                new DbCategory(
                        "Gift", 0,  iconInteraction.getIconByName("gift")));
        categories.add(
                new DbCategory(
                        "Travel", 0,  iconInteraction.getIconByName("travel")));
        categories.add(
                new DbCategory(
                        "Education", 0,  iconInteraction.getIconByName("education")));
        categories.add(
                new DbCategory(
                        "Book", 0,  iconInteraction.getIconByName("book")));
        categories.add(
                new DbCategory(
                        "Tax", 0,  iconInteraction.getIconByName("tax")));
        categories.add(
                new DbCategory(
                        "Phone", 0,  iconInteraction.getIconByName("phone")));
        categories.add(
                new DbCategory(
                        "Insurance", 0,  iconInteraction.getIconByName("insurance")));
        categories.add(
                new DbCategory(
                        "Salary", 1,  iconInteraction.getIconByName("salary")));
        categories.add(
                new DbCategory(
                        "Business", 1,  iconInteraction.getIconByName("business")));
        return categories;
    }

    public static List<DbIcon> getIcons(){ //тут добавить иконки
        List<DbIcon> icons = new LinkedList<>();
        icons.add(new DbIcon("ic_car", "car"));
        icons.add(new DbIcon("ic_coins", "coins"));
        icons.add(new DbIcon("ic_transport", "transport"));
        icons.add(new DbIcon("ic_travel", "travel"));
        icons.add(new DbIcon("ic_baby", "baby"));
        icons.add(new DbIcon("ic_beauty", "beauty"));
        icons.add(new DbIcon("ic_book", "book"));
        icons.add(new DbIcon("ic_carrot", "carrot"));
        icons.add(new DbIcon("ic_cheers", "cheers"));
        icons.add(new DbIcon("ic_clapperboard", "clapperboard"));
        icons.add(new DbIcon("ic_clothing", "clothing"));
        icons.add(new DbIcon("ic_education", "education"));
        icons.add(new DbIcon("ic_fastfood", "fastfood"));
        icons.add(new DbIcon("ic_fish", "fish"));
        icons.add(new DbIcon("ic_food", "food"));
        icons.add(new DbIcon("ic_gift", "gift"));
        icons.add(new DbIcon("ic_grapes", "grapes"));
        icons.add(new DbIcon("ic_health", "health"));
        icons.add(new DbIcon("ic_insurance", "insurance"));
        icons.add(new DbIcon("ic_pet", "pet"));
        icons.add(new DbIcon("ic_phone", "phone"));
        icons.add(new DbIcon("ic_pizza", "pizza"));
        icons.add(new DbIcon("ic_shopping", "shopping"));
        icons.add(new DbIcon("ic_sport", "sport"));
        icons.add(new DbIcon("ic_taco", "taco"));
        icons.add(new DbIcon("ic_tax", "tax"));
        icons.add(new DbIcon("ic_bills", "bills"));
        icons.add(new DbIcon("ic_cash", "cash"));
        icons.add(new DbIcon("ic_credit_card", "creditCard"));
        icons.add(new DbIcon("ic_debet_card", "debetCard"));
        icons.add(new DbIcon("ic_salary", "salary"));
        icons.add(new DbIcon("ic_business", "business"));
        return icons;
    }

    public static List<DbAccount> getAccount(DbIconInteraction iconInteraction){
        List<DbAccount> accounts = new LinkedList<>();
        accounts.add(new DbAccount("Cash", iconInteraction.getIconByName("cash")));
        accounts.add(new DbAccount("Debit card", iconInteraction.getIconByName("debetCard")));
        accounts.add(new DbAccount("Credit card", iconInteraction.getIconByName("debetCard")));
        return accounts;
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

    public static void fillDatabase(RoomDB database) {
        database.dbIconInteraction().insertData(Utility.getIcons());
        database.dbCategoryInteraction().addCategory(Utility.getCategories(database.dbIconInteraction()));
        database.dbAccountInteraction().addAccount(Utility.getAccount(database.dbIconInteraction()));
    }
}
