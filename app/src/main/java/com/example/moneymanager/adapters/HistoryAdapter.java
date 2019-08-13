package com.example.moneymanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.model.HistoryModel;

import java.util.List;

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

        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(HistoryModel historyModel) {

        }
    }

    private class TotalViewHolder extends RecyclerView.ViewHolder{
        TotalViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(HistoryModel historyModel) {

        }
    }

    public interface IOnHistoryClicked{
        void onHistoryClicked();
    }
}
