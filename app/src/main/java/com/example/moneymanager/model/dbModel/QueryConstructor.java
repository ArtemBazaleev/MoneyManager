package com.example.moneymanager.model.dbModel;

import android.util.Log;

import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.moneymanager.model.FilterModel;

import java.util.ArrayList;
import java.util.List;

public class QueryConstructor {

    public SupportSQLiteQuery buildQuery(FilterModel model){
        String queryString = "";
        List<Object> args = new ArrayList<>();
        boolean constraintCondition = false;
        queryString += "SELECT * FROM dbtransaction";

        if(model.getIncome() != null){
            constraintCondition = true;
            queryString += " WHERE";
            queryString += " isIncome = ?";
            if (model.getIncome())
                args.add(1);
            else
                args.add(0);
        }

        if (model.getFromDate()!=null){
            if (!constraintCondition) {
                queryString += " WHERE";
                constraintCondition = true;
            }
            else queryString+= " AND";
            queryString += " date>= ?";
            args.add(model.getFromDate());
            queryString += " AND date <= ?";
            args.add(model.getToDate());
        }
        if (model.getAccount() != null){
            if (!constraintCondition) {
                queryString += " WHERE";
                constraintCondition = true;
            }
            else queryString+= " AND";

            queryString += " account_accountId = ?";
            args.add(model.getAccount().getAccount().accountId);
        }

        if (model.getCategory()!= null){
            if (!constraintCondition) {
                queryString += " WHERE";
                constraintCondition = true;
            }
            else queryString+= " AND";

            queryString += " category_categoryId = ?";
            args.add(model.getCategory().getCategory().categoryId);
        }
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString, args.toArray());
        Log.d("QueryConstructor", "buildQuery: " + query.getSql());
        return query;
    }
}
