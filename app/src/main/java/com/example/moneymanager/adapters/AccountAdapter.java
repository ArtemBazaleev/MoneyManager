package com.example.moneymanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.model.AccountModel;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    public static final int MODE_HORIZONTAL = 0;
    public static final int MODE_VERTICAL =1;
    private int mode = MODE_HORIZONTAL;
    private List<AccountModel> mData;
    private Context mContext;
    private IOnItemClicked mListener;

    public AccountAdapter(List<AccountModel> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }
    public AccountAdapter(List<AccountModel> mData, Context mContext, int mode) {
        this.mData = mData;
        this.mContext = mContext;
        this.mode = mode;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (mode == MODE_HORIZONTAL)
            v = LayoutInflater.from(mContext).inflate(R.layout.item_account, parent, false);
        else v = LayoutInflater.from(mContext).inflate(R.layout.item_account_vert, parent, false);
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

    public void setmListener(IOnItemClicked mListener) {
        this.mListener = mListener;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setSelected(AccountModel selected){
        unselectAll();
        for (AccountModel i: mData) {
            if (i==selected)
                i.setSelected(true);

        }
        notifyDataSetChanged();
    }

    private void unselectAll() {
        for (AccountModel i: mData)
            i.setSelected(false);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        AccountModel model;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_account);
            cardView.setOnClickListener(this::onClicked);
        }

        private void onClicked(View view) {
            if (mListener!=null)
                mListener.onItemClicked(model);
        }

        public void bind(AccountModel accountModel) {
            model = accountModel;
            if (model.isSelected()) {
                cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorLightBlue));
            }
            else cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }
    }

    public interface IOnItemClicked{
        void onItemClicked(AccountModel model);
    }
}
