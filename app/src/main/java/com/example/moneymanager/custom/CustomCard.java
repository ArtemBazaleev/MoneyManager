package com.example.moneymanager.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.moneymanager.R;

public class CustomCard extends ConstraintLayout {
    private TextView total;
    private TextView dayBalance;
    private TextView monthBalance;
    private TextView weekBalance;

    public CustomCard(Context context) {
        super(context);
        init();
    }

    public CustomCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        View v = inflate(getContext(), R.layout.layout_custom_card, this);
        total = v.findViewById(R.id.textView3);
        dayBalance = v.findViewById(R.id.textView7);
        monthBalance = v.findViewById(R.id.textView9);
        weekBalance = v.findViewById(R.id.textView8);
    }

    public void setTotal(String total){
        this.total.setText(total);
    }

    public void setDayBalance(String blc){
        dayBalance.setText(blc);
    }

    public void setMonthBalance(String blc){
        monthBalance.setText(blc);
    }

    public void setWeekBalance(String blc) {
        weekBalance.setText(blc);
    }
}
