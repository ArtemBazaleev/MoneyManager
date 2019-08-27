package com.example.moneymanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.model.AccountModel;
import com.example.moneymanager.model.StatMonthModel;
import com.example.moneymanager.utils.Utility;

import java.util.List;

public class MonthStatAdapter extends RecyclerView.Adapter<MonthStatAdapter.ViewHolder> {
    private List<StatMonthModel> mData;
    private Context mContext;
    private MonthStatAdapter.IOnItemClicked mListener;

    public MonthStatAdapter(List<StatMonthModel> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MonthStatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_stat_month, parent, false);
        return new MonthStatAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthStatAdapter.ViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setmListener(MonthStatAdapter.IOnItemClicked mListener) {
        this.mListener = mListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView monthName;
        TextView value;
        ConstraintLayout constraintLayout;
        StatMonthModel model;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            monthName = itemView.findViewById(R.id.month_stat_name);
            value = itemView.findViewById(R.id.month_stat_value);
            constraintLayout = itemView.findViewById(R.id.month_stat_constraint);
            constraintLayout.setOnClickListener(this::onClicked);
        }

        private void onClicked(View view) {
            if (mListener!= null)
                mListener.onItemClicked(model);
        }

        public void bind(StatMonthModel monthModel) {
            this.model = monthModel;
            monthName.setText(monthModel.getName());
            if (model.getType().equals(StatMonthModel.TYPE_INCOME)) {
                value.setText(String.format("\u20BD +%s", Utility.formatDouble(monthModel.getValue())));
                value.setBackground(mContext.getResources().getDrawable(R.drawable.bg_light_green_round));
            }
            else {
                value.setText(String.format("\u20BD -%s", Utility.formatDouble(monthModel.getValue())));
                value.setBackground(mContext.getResources().getDrawable(R.drawable.bg_light_red_round));
            }
        }
    }

    public interface IOnItemClicked{
        void onItemClicked(StatMonthModel model);
    }
}
