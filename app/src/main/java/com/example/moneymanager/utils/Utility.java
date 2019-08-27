package com.example.moneymanager.utils;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
                        "Food", 0,  iconInteraction.getIconByName("food"), 0));
        categories.add(
                new DbCategory(
                        "Transport", 0,  iconInteraction.getIconByName("transport"), 1));
        categories.add(
                new DbCategory(
                        "Bills", 0,  iconInteraction.getIconByName("bills"), 2));
        categories.add(
                new DbCategory(
                        "Car", 0,  iconInteraction.getIconByName("car"), 3));
        categories.add(
                new DbCategory(
                        "Shopping", 0,  iconInteraction.getIconByName("shopping"), 4));
        categories.add(
                new DbCategory(
                        "Clothing", 0,  iconInteraction.getIconByName("clothing"), 5));
        categories.add(
                new DbCategory(
                        "Health", 0,  iconInteraction.getIconByName("health"), 6));
        categories.add(
                new DbCategory(
                        "Sport", 0,  iconInteraction.getIconByName("sport"), 7));
        categories.add(
                new DbCategory(
                        "Baby", 0,  iconInteraction.getIconByName("baby"), 8));
        categories.add(
                new DbCategory(
                        "Fastfood", 0,  iconInteraction.getIconByName("fastfood"), 9));
        categories.add(
                new DbCategory(
                        "Gift", 0,  iconInteraction.getIconByName("gift"), 10));
        categories.add(
                new DbCategory(
                        "Travel", 0,  iconInteraction.getIconByName("travel"), 11));
        categories.add(
                new DbCategory(
                        "Education", 0,  iconInteraction.getIconByName("education"), 12));
        categories.add(
                new DbCategory(
                        "Book", 0,  iconInteraction.getIconByName("book"), 13));
        categories.add(
                new DbCategory(
                        "Tax", 0,  iconInteraction.getIconByName("tax"), 14));
        categories.add(
                new DbCategory(
                        "Phone", 0,  iconInteraction.getIconByName("phone"), 15));
        categories.add(
                new DbCategory(
                        "Insurance", 0,  iconInteraction.getIconByName("insurance"), 16));
        categories.add(
                new DbCategory(
                        "Salary", 1,  iconInteraction.getIconByName("salary"), 0));
        categories.add(
                new DbCategory(
                        "Business", 1,  iconInteraction.getIconByName("business"), 1));
        categories.add(
                new DbCategory(
                        "Add new", 1,  iconInteraction.getIconByName("add"), 2));
        categories.add(
                new DbCategory(
                        "Add new", 0,  iconInteraction.getIconByName("add"), 17));
        return categories;
    }

    public static List<DbIcon> getIcons(){ //тут добавить иконки
        List<DbIcon> icons = new LinkedList<>();
        icons.add(new DbIcon("ic_car", "car", "#FFF59D"));
        icons.add(new DbIcon("ic_coins", "coins","#C5E1A5"));
        icons.add(new DbIcon("ic_transport", "transport","#EF9A9A"));
        icons.add(new DbIcon("ic_travel", "travel","#90CAF9"));
        icons.add(new DbIcon("ic_baby", "baby","#CE93D8"));
        icons.add(new DbIcon("ic_beauty", "beauty","#B39DDB"));
        icons.add(new DbIcon("ic_book", "book","#9FA8DA"));
        icons.add(new DbIcon("ic_carrot", "carrot","#90CAF9"));
        icons.add(new DbIcon("ic_cheers", "cheers","#81D4FA"));
        icons.add(new DbIcon("ic_clapperboard", "clapperboard","#80DEEA"));
        icons.add(new DbIcon("ic_clothing", "clothing","#B39DDB"));
        icons.add(new DbIcon("ic_education", "education","#80CBC4"));
        icons.add(new DbIcon("ic_fastfood", "fastfood","#A5D6A7"));
        icons.add(new DbIcon("ic_fish", "fish","#E6EE9C"));
        icons.add(new DbIcon("ic_food", "food","#FFE082"));
        icons.add(new DbIcon("ic_gift", "gift","#FFCC80"));
        icons.add(new DbIcon("ic_grapes", "grapes","#FFAB91"));
        icons.add(new DbIcon("ic_health", "health","#E57373"));
        icons.add(new DbIcon("ic_insurance", "insurance","#F06292"));
        icons.add(new DbIcon("ic_pet", "pet","#BA68C8"));
        icons.add(new DbIcon("ic_phone", "phone","#9575CD"));
        icons.add(new DbIcon("ic_pizza", "pizza","#7986CB"));
        icons.add(new DbIcon("ic_shopping", "shopping","#64B5F6"));
        icons.add(new DbIcon("ic_sport", "sport","#64B5F6"));
        icons.add(new DbIcon("ic_taco", "taco","#4FC3F7"));
        icons.add(new DbIcon("ic_tax", "tax","#4DD0E1"));
        icons.add(new DbIcon("ic_bills", "bills","#4DB6AC"));
        icons.add(new DbIcon("ic_cash", "cash","#81C784"));
        icons.add(new DbIcon("ic_credit_card", "creditCard","#EF5350"));
        icons.add(new DbIcon("ic_debet_card", "debetCard","#EC407A"));
        icons.add(new DbIcon("ic_salary", "salary","#00BCD4"));
        icons.add(new DbIcon("ic_business", "business","#26A69A"));
        icons.add(new DbIcon("ic_add_circle_outline_black_24dp", "add", "#BA68C8"));
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

    public static String formatDate(Long date){
        DateFormat df = new DateFormat();
        return DateFormat.format("dd MMM HH:mm", new Date(date)).toString();
    }

    public static String formatDateForHistoryTitle(Long date){
        DateFormat df = new DateFormat();
        return DateFormat.format("dd MMM yyyy", new Date(date)).toString();
    }

    public static String formatDouble(Double value){
        String res = String.format(Locale.getDefault(),"%.2f", value);
        res = res.replace(".",",");
        StringBuilder stringBuilder = new StringBuilder(res);
        int index = stringBuilder.indexOf(",");
        for (int i = index; i>0;i--){
            if ((i-index)%3 == 0 && i!=index)
                stringBuilder.insert(i,' ');
        }
        return stringBuilder.toString();
    }

}
