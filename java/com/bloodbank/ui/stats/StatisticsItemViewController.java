package com.bloodbank.ui.stats;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bloodbank.R;
import com.bloodbank.db.BloodGroupResult;

import java.util.Locale;

public class StatisticsItemViewController extends RecyclerView.ViewHolder {
    private TextView mBloodGroupTextView;
    private TextView mCountTextView;

    public StatisticsItemViewController(View itemView) {
        super(itemView);
        mBloodGroupTextView = itemView.findViewById(R.id.bloodGroupText);
        mCountTextView = itemView.findViewById(R.id.countText);
    }

    public void setData(BloodGroupResult data) {
        mBloodGroupTextView.setText(data.bloodGroup);
        mCountTextView.setText(String.format(Locale.getDefault(), "%d", data.count));
    }
}
