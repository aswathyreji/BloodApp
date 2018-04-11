package com.bloodbank.ui.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bloodbank.R;
import com.bloodbank.db.Registration;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchItemViewController> {
    private List<Registration> mSearchResults;
    private SearchFragment mSearchFragment;

    public SearchAdapter(SearchFragment searchFragment) {
        mSearchFragment = searchFragment;
    }

    @NonNull
    @Override
    public SearchItemViewController onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.search_item, parent, false);
        return new SearchItemViewController(view, mSearchFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewController holder, int position) {
        holder.setData(mSearchResults.get(position));
    }

    @Override
    public int getItemCount() {
        return mSearchResults != null ? mSearchResults.size() : 0;
    }

    public void setSearchResults(List<Registration> mSearchResults) {
        this.mSearchResults = mSearchResults;
        notifyDataSetChanged();
    }
}
