package com.example.moneymanager.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.moneymanager.R;

public class CustomSelector extends ConstraintLayout {
    private String TAG = "Selector";
    private ConstraintLayout leftBtn;
    private ConstraintLayout rightBtn;
    private TextView leftText;
    private TextView rightText;
    private ConstraintLayout border;

    public CustomSelector(Context context) {
        super(context);
        init();
    }

    public CustomSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void initListeners() {
        leftBtn.setOnClickListener(l->{
            Log.d(TAG, "leftBtn: Clicked");
            leftBtn.setBackgroundResource(R.drawable.bg_red_selector);
            border.setBackgroundResource(R.drawable.bg_custom_selector_red);
            leftText.setTextColor(getContext().getResources().getColor(R.color.colorWhite));

            rightText.setTextColor(getContext().getResources().getColor(R.color.colorDarkText));
            rightBtn.setBackgroundResource(0);
        });

        rightBtn.setOnClickListener(l->{
            Log.d(TAG, "rightBtn: Clicked");

            rightBtn.setBackgroundResource(R.drawable.bg_green_selector);
            border.setBackgroundResource(R.drawable.bg_custom_selector_green);
            rightText.setTextColor(getContext().getResources().getColor(R.color.colorWhite));

            leftText.setTextColor(getContext().getResources().getColor(R.color.colorDarkText));
            leftBtn.setBackgroundResource(0);
        });
    }

    private void init(){
        View v = inflate(getContext(), R.layout.layout_selector, this);
        leftBtn = v.findViewById(R.id.leftBtnSelector);
        rightBtn = v.findViewById(R.id.right_btn_selector);
        leftText = v.findViewById(R.id.left_text_selector);
        rightText = v.findViewById(R.id.right_text_selector);
        border = v.findViewById(R.id.border_selector);
        initListeners();
    }
}
