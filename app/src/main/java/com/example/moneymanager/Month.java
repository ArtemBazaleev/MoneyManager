package com.example.moneymanager;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Switch;

public enum Month {
    January,
    February,
    March,
    April,
    May,
    June,
    July,
    August,
    September,
    October,
    November,
    December;

    public static String getMonthName(Month month, Context mContext){
        Resources res = mContext.getResources();
        switch(month){
            case January:
                return res.getString(R.string.january);
            case February:
                return res.getString(R.string.february);
            case March:
                return res.getString(R.string.march);
            case April:
                return res.getString(R.string.april);
            case May:
                return res.getString(R.string.may);
            case June:
                return res.getString(R.string.june);
            case July:
                return res.getString(R.string.july);
            case August:
                return res.getString(R.string.august);
            case September:
                return res.getString(R.string.september);
            case October:
                return res.getString(R.string.october);
            case November:
                return res.getString(R.string.november);
            case December:
                return res.getString(R.string.december);
        }
        return "unknown";
    }



}
