package com.example.moneymanager.ui;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.moneymanager.App;
import com.example.moneymanager.R;
import com.example.moneymanager.custom.CustomIcon;
import com.example.moneymanager.model.IconModel;
import com.example.moneymanager.presentation.presenter.GoalActivityPresenter;
import com.example.moneymanager.presentation.view.GoalActivityView;
import com.example.moneymanager.utils.Utility;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GoalActivity extends MvpAppCompatActivity implements GoalActivityView {
    public static final String MODE = "MODE";
    public static final String MODE_NEW = "MODE_NEW";
    public static final String MODE_CONFIGURE = "MODE_CONFIGURE";
    public static final String GOALID = "goalID";

    private String mode = MODE_NEW;
    private int goalID ;

    @BindView(R.id.save_goal_btn) Button saveBtn;
    @BindView(R.id.chose_category_btn) TextView choseCategory;
    @BindView(R.id.title_goal) TextView title;
    @BindView(R.id.custom_icon) CustomIcon iconImg;
    @BindView(R.id.editText3) EditText goalName;
    @BindView(R.id.editText4) EditText goalNote;
    @BindView(R.id.editText6) EditText goalSum;
    @BindView(R.id.imageView10) ImageView delBtn;
    private BottomSheetImageFragment category;

    @InjectPresenter
    GoalActivityPresenter presenter;
    @ProvidePresenter
    GoalActivityPresenter providePresenter(){
        App app = (App) getApplication();
        return new GoalActivityPresenter(
                app.getDatabase().dataBaseGoalContract(),
                app.getDatabase().dbIconInteraction(),
                app.getDatabase().dbGoalTransactionInteraction()
        );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        ButterKnife.bind(this);
        if (getIntent().getExtras()!= null){
            mode = getIntent().getExtras().getString(MODE);
            goalID = getIntent().getExtras().getInt(GOALID);
        }
        init();
        presenter.setMode(mode, goalID);
        presenter.onCreate();
    }
    private void init(){
        saveBtn.setOnClickListener(l-> presenter.onSaveClicked());
        category = BottomSheetImageFragment.newInstance();
        category.setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
        category.setmListener(presenter::onIconChosen);
        delBtn.setOnClickListener(v-> presenter.onDelBtnClicked());
        choseCategory.setOnClickListener(l-> presenter.onChoseIconClicked());
        initTextWatchers();
    }

    private void initTextWatchers() {
        goalName.addTextChangedListener(goalNameTextWatcher);
        goalNote.addTextChangedListener(goalNoteTextWatcher);
        goalSum.addTextChangedListener(sumTextWatcher);
    }

    @Override
    public void showBottomSheetIcon() {
        category.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onIconChosen(IconModel icon) {
        iconImg.setImageDrawable(getResources().getDrawable(Utility.getResId(icon.getDbIcon().drawableIcon,R.drawable.class)));
    }

    @Override
    public void setSum(String sum) {
        goalSum.removeTextChangedListener(sumTextWatcher);
        goalSum.setText(sum);
        goalSum.addTextChangedListener(sumTextWatcher);
    }


    @Override
    public void stopSelf() {
        finish();
    }

    @Override
    public void showMessageNoGoalName() {
        Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessageNoGoalTotalSum() {
        Toast.makeText(this, "Введите сумму", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setEnabledDelBtn(Boolean enabledDelBtn) {
        if (enabledDelBtn)
            delBtn.setVisibility(View.VISIBLE);
        else delBtn.setVisibility(View.GONE);
    }

    @Override
    public void setName(String name) {
        goalName.removeTextChangedListener(goalNameTextWatcher);
        goalName.setText(name);
        goalName.addTextChangedListener(goalNameTextWatcher);
    }

    @Override
    public void setNote(String note){
        goalNote.removeTextChangedListener(goalNoteTextWatcher);
        goalNote.setText(note);
        goalNote.addTextChangedListener(goalNoteTextWatcher);
    }

    @Override
    public void startMainActivityAndClearStack() {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private TextWatcher sumTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            presenter.onSumChanged(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private TextWatcher goalNameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            presenter.onGoalNameChanged(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) { }
    };

    private TextWatcher goalNoteTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            presenter.onGoalNoteChanged(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) { }
    };
}
