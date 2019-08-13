package com.example.moneymanager.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.moneymanager.R;

public class CustomIcon extends ConstraintLayout {
    public CustomIcon(Context context) {
        super(context);
        init();
    }

    public CustomIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        View v = inflate(getContext(), R.layout.layout_icon, this);
    }

}
