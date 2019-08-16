package com.example.moneymanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.model.HistoryGoalModel;
import com.example.moneymanager.model.HistoryModel;
import com.example.moneymanager.utils.Utility;

import java.util.List;

public class HistoryGoalAdapter extends RecyclerView.Adapter<HistoryGoalAdapter.ViewHolder> {
    private List<HistoryGoalModel> mData;
    private Context mContext;

    public HistoryGoalAdapter(List<HistoryGoalModel> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_history_goal, parent, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sum;
        private TextView date;
        private HistoryGoalModel model;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            sum = itemView.findViewById(R.id.textView20);
            date = itemView.findViewById(R.id.textView19);
        }

        public void bind(HistoryGoalModel historyGoalModel) {
            model = historyGoalModel;
            sum.setText(String.format("\u20BD %s", Utility.formatDouble(model.getTransaction().sum)));
            date.setText(Utility.formatDate(model.getTransaction().goalTransactionDate));
        }
    }
}
