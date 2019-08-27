package com.example.moneymanager.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.moneymanager.R;
import com.example.moneymanager.model.CategoryModel;
import com.example.moneymanager.model.StatPieChartModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StatPagerAdapter extends PagerAdapter {

    private float[] yData = {10F,20F,30F,40F,50F};
    private String[] xDate = {"","","","",""};


    private List<StatPieChartModel> mData;
    private Context mContext;

    public StatPagerAdapter(List<StatPieChartModel> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.item_stat_pie_chart, container, false);
        PieChart pieChart = view.findViewById(R.id.pie_chart_stat);
        initPieChart(pieChart, position);
        container.addView(view);
        return view;
    }

    private void initPieChart(PieChart pieChart, int position) {
        ArrayList<PieEntry> yEntries = new ArrayList<>();
        for (int i = 0; i < mData.get(position).getxData().length; i++) {
            yEntries.add(new PieEntry((float) mData.get(position).getxData()[i], i));
        }
        PieDataSet dataset = new PieDataSet(yEntries,"Категории");
        dataset.setSliceSpace(1);
        dataset.setValueTextSize(0);
        dataset.setLabel("");

        dataset.setValueTextColor(Color.BLACK);
        //initColors
        ArrayList<Integer> colors = new ArrayList<>();
        for (String i:mData.get(position).getColors()) {
            colors.add(Color.parseColor(i));
        }
        dataset.setColors(colors);

        PieData data = new PieData(dataset);
        pieChart.setData(data);
        pieChart.setCenterText("");
        pieChart.setRotationEnabled(false);
        pieChart.invalidate();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void updatePieChart(List<StatPieChartModel> mData){
        this.mData = mData;
        notifyDataSetChanged();
        Log.d("StatPagerAdapter", "updatePieChart: Called");
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
