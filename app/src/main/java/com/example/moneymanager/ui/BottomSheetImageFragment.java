package com.example.moneymanager.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpBottomSheetDialogFragment;
import com.example.moneymanager.App;
import com.example.moneymanager.R;
import com.example.moneymanager.adapters.IconAdapter;
import com.example.moneymanager.model.IconModel;
import com.example.moneymanager.model.dbModel.DbIcon;
import com.example.moneymanager.utils.Utility;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BottomSheetImageFragment extends MvpBottomSheetDialogFragment {

    @BindView(R.id.recycler_account) RecyclerView recyclerView;
    private IOnCategoryModelClicked mListener;


    static BottomSheetImageFragment newInstance() {
        return new BottomSheetImageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_bottom_sheet_category, container, false);
        ButterKnife.bind(this, view);
        App app = (App) Objects.requireNonNull(getActivity()).getApplication();
        Disposable d = app.getDatabase().dbIconInteraction()
                .getAllIcons()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(dbIcons -> {
                    List<IconModel> models = new LinkedList<>();
                    for (DbIcon i: dbIcons)
                        models.add(new IconModel(i));
                    loadImages(models);

                },Throwable::printStackTrace);

        return view;

    }

    private void onCategoryClicked(IconModel iconModel) {
        if (mListener!=null)
            mListener.onCategoryClicked(iconModel);
        dismiss();
    }

    private void loadImages(List<IconModel> mData) {
        recyclerView.setLayoutManager(new GridLayoutManager(
                getContext(), Utility.calculateNoOfColumns(Objects.requireNonNull(getContext()), 56)));
        IconAdapter adapter = new IconAdapter(mData, getContext());
        adapter.setmListener(this::onCategoryClicked);
        recyclerView.setAdapter(adapter);
    }

    public interface IOnCategoryModelClicked{
        void onCategoryClicked(IconModel model);
    }

    public IOnCategoryModelClicked getmListener() {
        return mListener;
    }

    public void setmListener(IOnCategoryModelClicked mListener) {
        this.mListener = mListener;
    }

}
