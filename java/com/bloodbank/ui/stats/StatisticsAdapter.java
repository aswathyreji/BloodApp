package com.bloodbank.ui.stats;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bloodbank.R;
import com.bloodbank.db.BloodGroupResult;

import java.util.List;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsItemViewController> {
    private final List<BloodGroupResult> mData;

    public StatisticsAdapter(List<BloodGroupResult> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public StatisticsItemViewController onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.statistics_item, parent, false);
        return new StatisticsItemViewController(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsItemViewController holder, int position) {
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
