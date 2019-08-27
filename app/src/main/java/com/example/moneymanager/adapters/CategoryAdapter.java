package com.example.moneymanager.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.interfaces.ItemTouchHelperAdapter;
import com.example.moneymanager.model.AccountModel;
import com.example.moneymanager.model.CategoryModel;
import com.example.moneymanager.model.dbModel.DbCategory;
import com.example.moneymanager.utils.Utility;

import java.util.Collections;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {

    private List<CategoryModel> mData;
    private Context mContext ;
    private IOnItemClicked mListener;

    public CategoryAdapter(List<CategoryModel> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    public void setSelected(CategoryModel selected){
        unselectAll();
        for (CategoryModel i: mData) {
            if (i==selected) {
                i.setSelected(true);
                Log.d("CategoryAdapter", "setSelected: found");
            }

        }
        notifyDataSetChanged();
    }

    private void unselectAll() {
        for (CategoryModel i: mData)
            i.setSelected(false);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mData, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mData, i, i - 1);
            }
        }
        Log.d("CategoryAdapter", "fromPosition:"+ fromPosition+" toPosition:"+toPosition);
        if (mListener!=null)
            mListener.onPositionChanged(mData.get(fromPosition).getCategory(), mData.get(toPosition).getCategory());
        Log.d("\nCategoryAdapter", "fromPosition:"+ mData.get(fromPosition).getCategory().position + " toPosition:"+mData.get(toPosition).getCategory().position);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {

    }


    public void setmListener(IOnItemClicked mListener) {
        this.mListener = mListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView img;
        TextView txt;
        CategoryModel model;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_category);
            img = itemView.findViewById(R.id.imageView3);
            txt= itemView.findViewById(R.id.textView12);
            cardView.setOnClickListener(this::onCardClicked);
        }

        private void onCardClicked(View view) {
            if (mListener!=null) {
                if (model.getCategory().categoryName.equals("Add new"))
                    mListener.onAddNewCategoryClicked();
                else mListener.onCategoryClicked(model);
            }
        }

        public void bind(CategoryModel categoryModel) {
            model = categoryModel;
            img.setImageDrawable(mContext.getResources().getDrawable(Utility.getResId(model.getIconId(), R.drawable.class)));
            txt.setText(model.getName());
            if (model.isSelected())
                cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorLightBlue));
            else cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }
    }

    public interface IOnItemClicked{
        void onCategoryClicked(CategoryModel model);

        default void onAddNewCategoryClicked(){

        }
        default void onPositionChanged(DbCategory oldPosition, DbCategory newPosition){

        }
    }
}
