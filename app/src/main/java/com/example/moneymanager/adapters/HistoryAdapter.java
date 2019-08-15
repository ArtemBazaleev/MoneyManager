package com.example.moneymanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.model.HistoryModel;
import com.example.moneymanager.utils.Utility;

import java.util.List;
import java.util.concurrent.TimeoutException;

public class HistoryAdapter extends RecyclerView.Adapter {
    private static final int TYPE_HISTORY_ITEM = 0;
    private static final int TYPE_HISTORY_TOTAL_ITEM = 1;

    private Context mContext;
    private List<HistoryModel> mData;
    private IOnHistoryClicked mListener;

    public HistoryAdapter(Context mContext, List<HistoryModel> mData, IOnHistoryClicked mListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mListener = mListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getType() == HistoryModel.TYPE_TOTAL)
            return TYPE_HISTORY_TOTAL_ITEM;
        else
            return TYPE_HISTORY_ITEM;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_HISTORY_TOTAL_ITEM) {
            v = LayoutInflater.from(mContext).inflate(R.layout.item_history_total, parent, false);
            return new TotalViewHolder(v);
        }
        else {
            v = LayoutInflater.from(mContext).inflate(R.layout.item_history, parent, false);
            return new HistoryViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_HISTORY_ITEM){
            ((HistoryViewHolder) holder).bind(mData.get(position));
        }
        else
            ((TotalViewHolder) holder).bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class HistoryViewHolder extends RecyclerView.ViewHolder{
        TextView categoryTxt;
        ImageView categoryImg;
        TextView note;
        TextView sum;
        HistoryModel model;
        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImg = itemView.findViewById(R.id.imageView4);
            categoryTxt = itemView.findViewById(R.id.textView17);
            note = itemView.findViewById(R.id.textView19);
            sum = itemView.findViewById(R.id.textView20);
        }

        void bind(HistoryModel historyModel) {
            model = historyModel;
            categoryTxt.setText(model.getTransaction().transactionCategoryID.categoryName);
            categoryImg.setImageDrawable(
                    mContext.getResources()
                            .getDrawable(
                                    Utility.getResId(
                                            model.getTransaction().transactionCategoryID.drawableIcon.drawableIcon,
                                            R.drawable.class)));
            if (model.getTransaction().isIncome == 1) {
                categoryImg.setBackground(mContext.getResources().getDrawable(R.drawable.bg_light_green_round));
                sum.setText(String.format("\u20BD + %s", Utility.formatDouble(model.getSum())));
                sum.setTextColor(mContext.getResources().getColor(R.color.colorLightGreen));
            }
            else {
                categoryImg.setBackground(mContext.getResources().getDrawable(R.drawable.bg_light_red_round));
                sum.setText(String.format("\u20BD - %s", Utility.formatDouble(model.getSum())));
                sum.setTextColor(mContext.getResources().getColor(R.color.colorLightRed));
            }
            note.setText(model.getNote());
        }
    }

    private class TotalViewHolder extends RecyclerView.ViewHolder{
        TextView sum;
        TextView date;
        HistoryModel model;
        TotalViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.textView21);
            sum = itemView.findViewById(R.id.textView22);
        }

        void bind(HistoryModel historyModel) {
            model = historyModel;
            if (model.getSum()<= 0.0)
                sum.setBackground(mContext.getResources().getDrawable(R.drawable.bg_light_red_round));
            else sum.setBackground(mContext.getResources().getDrawable(R.drawable.bg_light_green_round));
            sum.setText(Utility.formatDouble(model.getSum()));
            date.setText(model.getDate());
        }
    }

    public interface IOnHistoryClicked{
        void onHistoryClicked();
    }
}
