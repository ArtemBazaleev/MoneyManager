package com.example.moneymanager.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.interfaces.ItemTouchHelperAdapter;
import com.example.moneymanager.model.CategoryModel;

import java.util.Collections;
import java.util.List;

public class CategoryStatAdapter extends RecyclerView.Adapter<CategoryStatAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {
    private List<CategoryModel> mData;
    private Context mContext ;
    private IOnItemClicked mListener;

    public CategoryStatAdapter(List<CategoryModel> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_category_stat, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mData.get(position));
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
        TextView categoryName;
        ImageView img;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView4);
            categoryName = itemView.findViewById(R.id.textView37);
            img = itemView.findViewById(R.id.category_stat);
            cardView.setOnClickListener(this::onCardClicked);
        }

        private void onCardClicked(View view) {
            if (mListener!=null)
                mListener.onCategoryStatClicked(new CategoryModel());
        }

        public void bind(CategoryModel categoryModel) {
            cardView.setCardBackgroundColor(categoryModel.getColor());
            categoryName.setText(categoryModel.getName());
            ImageViewCompat.setImageTintList(img, ColorStateList.valueOf(categoryModel.getTintColor()));

        }
    }

    public interface IOnItemClicked{
        void onCategoryStatClicked(CategoryModel model);
    }
}
