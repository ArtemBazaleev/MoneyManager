package com.example.moneymanager.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.moneymanager.App;
import com.example.moneymanager.R;
import com.example.moneymanager.custom.CustomIcon;
import com.example.moneymanager.model.IconModel;
import com.example.moneymanager.presentation.presenter.CategoryAddActivityPresenter;
import com.example.moneymanager.presentation.view.CategoryAddActivityView;
import com.example.moneymanager.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAddActivity extends MvpAppCompatActivity implements CategoryAddActivityView {
    public static final String MODE = "mode";
    public static final int MODE_CATEGORY = 0;
    public static final int MODE_ACCOUNT = 1;

    public static final String IS_INCOME  = "isincome";
    public static final int INCOME = 2;
    public static final int OUTCOME = 3;


    @BindView(R.id.constraintLayout6) ConstraintLayout choseData;
    @BindView(R.id.textView33) TextView title;
    @BindView(R.id.filter_category) ConstraintLayout dataCell;
    @BindView(R.id.imageView3) ImageView dataImg;
    @BindView(R.id.textView16) TextView dataName;
    @BindView(R.id.textView43) TextView dataTxtName;
    @BindView(R.id.editText4) EditText dataEditName;
    @BindView(R.id.custom_icon) CustomIcon dataChoseIconImg;
    @BindView(R.id.chose_category_btn) TextView dataChoseIconBtn;
    @BindView(R.id.button5) Button save;
    @BindView(R.id.imageView11) ImageView delBtn;

    @InjectPresenter
    CategoryAddActivityPresenter presenter;


    @ProvidePresenter
    CategoryAddActivityPresenter providePresenter(){
        App app = (App) getApplication();
        return new CategoryAddActivityPresenter(
          app.getDatabase().dbAccountInteraction(),
          app.getDatabase().dbCategoryInteraction(),
          app.getDatabase().dbIconInteraction(),
          app.getDatabase().dataBaseTransactionContract()
        );
    }


    private int mode = MODE_CATEGORY;
    private int isincome = OUTCOME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);
        ButterKnife.bind(this);
        if (getIntent().getExtras()!=null){
            mode = getIntent().getExtras().getInt(MODE);
            isincome = getIntent().getExtras().getInt(IS_INCOME);
        }
        if (mode==MODE_ACCOUNT){
            title.setText("Редактор счетов");
            dataTxtName.setText("Название счета");
        }
        presenter.setMode(mode);
        presenter.setIsIncome(isincome);
        init();

    }
    private void init() {
        save.setOnClickListener(v-> presenter.onSaveClicked());
        choseData.setOnClickListener(v->presenter.onChoseDataClicked());
        delBtn.setOnClickListener(v->presenter.onDelClicked());
        dataEditName.addTextChangedListener(editName);
        dataChoseIconBtn.setOnClickListener(v->presenter.onChoseIconClicked());
    }

    //Mvp
    @Override
    public void onIconChosen(IconModel icon) {
        dataChoseIconImg.setImageDrawable(
                getDrawable(Utility.getResId(icon.getDbIcon().drawableIcon, R.drawable.class))
        );
        dataImg.setImageDrawable(
                getDrawable(Utility.getResId(icon.getDbIcon().drawableIcon, R.drawable.class))
        );
    }

    @Override
    public void onNameChosen(String name) {
        dataEditName.removeTextChangedListener(editName);
        dataEditName.setText(name);
        dataName.setText(name);
        dataEditName.addTextChangedListener(editName);
    }

    @Override
    public void changeTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void showBottomSheetAccount() {
//        BottomSheetCategoryFragment bottomSheetCategoryFragment = BottomSheetCategoryFragment.newInstance();
//        bottomSheetCategoryFragment.setmListener(presenter::onCategoryChosen);
//        bottomSheetCategoryFragment.show(getSupportFragmentManager(), "Account");
    }

    @Override
    public void showBottomSheetCategory() {
        BottomSheetCategoryFragment bottomSheetCategoryFragment;
        if (isincome == INCOME)
            bottomSheetCategoryFragment = BottomSheetCategoryFragment.newInstance(BottomSheetCategoryFragment.MODE_INCOME);
        else bottomSheetCategoryFragment = BottomSheetCategoryFragment.newInstance(BottomSheetCategoryFragment.MODE_OUTCOME);
        bottomSheetCategoryFragment.setmListener(presenter::onCategoryChosen);
        bottomSheetCategoryFragment.show(getSupportFragmentManager(), "category");
    }

    @Override
    public void showBottomSheetIcon() {
        BottomSheetImageFragment bottomSheetImageFragment = BottomSheetImageFragment.newInstance();
        bottomSheetImageFragment.setmListener(presenter::onImageChosen);
        bottomSheetImageFragment.show(getSupportFragmentManager(), "icons");
    }

    @Override
    public void setVisibleCategory(boolean b) {
        if (b)
            dataCell.setVisibility(View.VISIBLE);
        else dataCell.setVisibility(View.GONE);
    }

    @Override
    public void stopSelf() {
        finish();
    }

    @Override
    public void showErrorEmptyName() {
        Toast.makeText(this, "Введите название", Toast.LENGTH_SHORT).show();
    }


    private TextWatcher editName = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            presenter.onNameChanged(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
