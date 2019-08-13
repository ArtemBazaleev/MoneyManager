package com.example.moneymanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.moneymanager.R;
import com.example.moneymanager.model.CategoryModel;
import com.example.moneymanager.model.IconModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO: 2019-08-13 add moxy 
public class GoalActivity extends AppCompatActivity {
    @BindView(R.id.save_goal_btn) Button saveBtn;
    @BindView(R.id.chose_category_btn) TextView choseCategory;
    @BindView(R.id.title_goal) TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        ButterKnife.bind(this);
        saveBtn.setOnClickListener(l-> finish());
        BottomSheetImageFragment category = BottomSheetImageFragment.newInstance();
        category.setStyle(BottomSheetDialogFragment.STYLE_NORMAL,R.style.AppBottomSheetDialogTheme);
        category.setmListener(this::onIconClistener);
        choseCategory.setOnClickListener(l-> category.show(getSupportFragmentManager(), ""));
    }

    private void onIconClistener(IconModel iconModel) {

    }


}
