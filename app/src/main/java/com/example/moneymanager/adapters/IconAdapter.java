package com.example.moneymanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.model.AccountModel;
import com.example.moneymanager.model.IconModel;

import java.util.List;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.ViewHolder> {
    private List<IconModel> mData;
    private Context mContext;
    private IconAdapter.IOnItemClicked mListener;

    public IconAdapter(List<IconModel> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public IconAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_icon, parent, false);
        return new IconAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IconAdapter.ViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setmListener(IOnItemClicked mListener) {
        this.mListener = mListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.icon_constraint);
            constraintLayout.setOnClickListener(this::onClicked);
        }

        private void onClicked(View view) {
            if (mListener!=null)
                mListener.onItemClicked(new IconModel());
        }

        public void bind(IconModel accountModel) {

        }
    }

    public interface IOnItemClicked{
        void onItemClicked(IconModel model);
    }
}
