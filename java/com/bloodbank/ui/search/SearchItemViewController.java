package com.bloodbank.ui.search;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bloodbank.R;
import com.bloodbank.db.Registration;

public class SearchItemViewController extends RecyclerView.ViewHolder {
    private TextView mDonorName;
    private TextView mDonorNumber;
    private ImageView mCallButton;
    private SearchFragment mSearchFragment;

    private Registration mData;

    public SearchItemViewController(View itemView, SearchFragment searchFragment) {
        super(itemView);
        this.mSearchFragment = searchFragment;
        mDonorName = itemView.findViewById(R.id.searchDonorName);
        mDonorNumber = itemView.findViewById(R.id.searchMobileNumber);
        mCallButton = itemView.findViewById(R.id.searchCallButton);

        itemView.setOnClickListener(v -> new AlertDialog.Builder(v.getContext())
                .setTitle(R.string.search_details_dialog_title)
                .setMessage(String.format(
                        v.getContext().getString(R.string.search_details_dialog_msg),
                        mData.name,
                        mData.email,
                        mData.mobileNumber,
                        mData.address,
                        mData.street,
                        mData.city,
                        mData.state,
                        mData.country,
                        mData.bloodGroup
                ))
                .show());
        mCallButton.setOnClickListener(v -> mSearchFragment.callPhone(mData.mobileNumber));
    }

    public void setData(Registration data) {
        this.mData = data;
        mDonorName.setText(data.name);
        mDonorNumber.setText(data.mobileNumber);
    }
}
