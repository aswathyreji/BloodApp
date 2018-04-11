package com.bloodbank.ui.stats;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bloodbank.R;
import com.bloodbank.app.AppDelegate;
import com.bloodbank.db.BloodGroupResult;
import com.bloodbank.ui.main.MainPagerAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {
    private SwipeRefreshLayout mStatsLayout;
    private RecyclerView mListView;
    private MainPagerAdapter mPagerAdapter;

    public StatisticsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStatsLayout = view.findViewById(R.id.statsLayout);
        mListView = view.findViewById(R.id.statsListView);
        mStatsLayout.setOnRefreshListener(this::refresh);
        refresh();
    }

    public void refresh() {
        mStatsLayout.setRefreshing(true);
        new Thread(() -> {
            List<BloodGroupResult> statistics = AppDelegate.db.getAppDao().statistics();
            getActivity().runOnUiThread(() -> {
                mListView.setAdapter(new StatisticsAdapter(statistics));
                mListView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                mStatsLayout.setRefreshing(false);
            });
        }).start();
    }

    public void setPagerAdapter(MainPagerAdapter adapter) {
        this.mPagerAdapter = adapter;
    }
}
