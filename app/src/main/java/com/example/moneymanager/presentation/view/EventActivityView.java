package com.example.moneymanager.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.example.moneymanager.model.AccountModel;
import com.example.moneymanager.model.CategoryModel;

import java.util.List;

public interface EventActivityView extends MvpView {
    void showToastyMessage(String message);

    void loadAccounts(List<AccountModel> accountModels);

    void loadCategories(List<CategoryModel> categoryModels);

    void requestPhotoOrGallery();

    void hideBottomSheet();

    void onCategoryChosen(CategoryModel categoryModel);

    void onAccountChosen(AccountModel accountModel);

    void setCategoryShown(boolean shown);

    void setAccountShown(boolean shown);
}
