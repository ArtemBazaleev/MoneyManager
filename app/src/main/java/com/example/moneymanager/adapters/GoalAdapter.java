package com.example.moneymanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.model.GoalModel;
import com.example.moneymanager.R;
import com.example.moneymanager.custom.CircleProgressBar;
import com.example.moneymanager.utils.Utility;

import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.ViewHolder> {

    private List<GoalModel> mData;
    private Context mContext;
    private IOnGoalClicked listener;

    public GoalAdapter(List<GoalModel> mData, Context mContext, IOnGoalClicked listener) {
        this.mData = mData;
        this.mContext = mContext;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_goal, parent,false);
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

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleProgressBar progressBar;
        CardView cardView;
        TextView goalName;
        TextView goalNote;
        ImageView goalImg;
        GoalModel model;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.custom_progressBar);
            cardView = itemView.findViewById(R.id.card_goal_item);
            goalName = itemView.findViewById(R.id.textView10);
            goalNote = itemView.findViewById(R.id.textView11);
            goalImg = itemView.findViewById(R.id.imageView);
            cardView.setOnClickListener(this::onClick);
        }

        private void onClick(View view) {
            if (listener!=null)
                listener.onGoalClicked(model);
        }

        void bind(GoalModel model) {
            this.model = model;
            progressBar.setProgress((float) (model.getGoal().balance / model.getGoal().sumTotal)*100);
            goalName.setText(model.getGoal().goalName);
            goalNote.setText(model.getGoal().note);
            goalImg.setImageDrawable(
                    mContext.getResources().getDrawable(
                            Utility.getResId(model.getGoal().iconID.drawableIcon, R.drawable.class)
                    )
            );
        }
    }

    public interface IOnGoalClicked{
        void onGoalClicked(GoalModel model);
    }
}
