package com.example.moneymanager.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.App;
import com.example.moneymanager.R;
import com.example.moneymanager.adapters.CategoryAdapter;
import com.example.moneymanager.interfaces.db.DbCategoryInteraction;
import com.example.moneymanager.model.CategoryModel;
import com.example.moneymanager.model.dbModel.DbCategory;
import com.example.moneymanager.utils.Utility;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

public class BottomSheetCategoryFragment extends BottomSheetDialogFragment
        implements CategoryAdapter.IOnItemClicked {
    public static final String MODE = "Mode";
    public static final String MODE_ALL = "All";
    public static final String MODE_INCOME = "InCome";
    public static final String MODE_OUTCOME = "OutCome";
    private App app;

    private String mode = MODE_ALL;

    CompositeDisposable disposables = new CompositeDisposable();

    @BindView(R.id.recycler_account) RecyclerView recyclerView;
    @BindView(R.id.account) TextView textViewHeader;
    CategoryAdapter adapter;

    private BottomSheetCategoryFragment.IOnCategoryModelClicked mListener;


    public static BottomSheetCategoryFragment newInstance() {
        return new BottomSheetCategoryFragment();
    }
    public static BottomSheetCategoryFragment newInstance(String mode) {
        Bundle args = new Bundle();
        args.putString(MODE, mode);
        BottomSheetCategoryFragment fragment = new BottomSheetCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null)
            mode = getArguments().getString(MODE, MODE_ALL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_bottom_sheet_category, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init(){
        app = (App) Objects.requireNonNull(getContext()).getApplicationContext();
        textViewHeader.setText("Categories");
        switch (mode)
        {
            case MODE_ALL:
                requestAllCategories();
                break;
            case MODE_INCOME:
                requestInComeCategories();
                break;
            case MODE_OUTCOME:
                requestOutcomeCategories();
        }
    }
    @Override
    public void onCategoryClicked(CategoryModel categoryModel) {
        Log.d("CATEGORYFRAGMENT", "onCategoryClicked: ");
        disposables.dispose();
        if (mListener!=null)
            mListener.onCategoryClicked(categoryModel);
        dismiss();
    }
    @Override
    public void onAddNewCategoryClicked(){
        disposables.dispose();
        if (mListener!=null)
            mListener.onAddNewCategoryClicked();
        dismiss();
    }

    public interface IOnCategoryModelClicked{
        void onCategoryClicked(CategoryModel model);
        default void onAddNewCategoryClicked(){}
    }

    public BottomSheetCategoryFragment.IOnCategoryModelClicked getmListener() {
        return mListener;
    }

    public void setmListener(BottomSheetCategoryFragment.IOnCategoryModelClicked mListener) {
        this.mListener = mListener;
    }


    private void requestOutcomeCategories() {
        DbCategoryInteraction categoryInteraction = app.getDatabase().dbCategoryInteraction();
        disposables.add(categoryInteraction.getAllOutComeCategories("Add new")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe(dbCategories -> {
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), Utility.calculateNoOfColumns(Objects.requireNonNull(getContext()))));
                    List<CategoryModel> models = new LinkedList<>();
                    for (DbCategory i:dbCategories)
                        models.add(new CategoryModel(i));
                    adapter = new CategoryAdapter(models, getContext());
                    adapter.setmListener(this::onCategoryClicked);
                    recyclerView.setAdapter(adapter);
                }, throwable -> {
                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                }));
    }

    private void requestInComeCategories() {
        DbCategoryInteraction categoryInteraction = app.getDatabase().dbCategoryInteraction();
        disposables.add(categoryInteraction.getAllInComeCategories("Add new")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe(dbCategories -> {
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), Utility.calculateNoOfColumns(Objects.requireNonNull(getContext()))));
                    List<CategoryModel> models = new LinkedList<>();
                    for (DbCategory i:dbCategories)
                        models.add(new CategoryModel(i));
                    adapter = new CategoryAdapter(models, getContext());
                    adapter.setmListener(this);
                    recyclerView.setAdapter(adapter);
                }, throwable -> {
                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                }));
    }

    private void requestAllCategories() {
        DbCategoryInteraction categoryInteraction = app.getDatabase().dbCategoryInteraction();
        disposables.add(categoryInteraction.getAllCategories("Add new")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe(dbCategories -> {
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), Utility.calculateNoOfColumns(Objects.requireNonNull(getContext()))));
                    List<CategoryModel> models = new LinkedList<>();
                    for (DbCategory i:dbCategories)
                        models.add(new CategoryModel(i));
                    adapter = new CategoryAdapter(models, getContext());
                    adapter.setmListener(this);
                    recyclerView.setAdapter(adapter);
                }, throwable -> {
                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                }));
    }
}
