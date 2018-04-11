package com.bloodbank.ui.search;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bloodbank.R;
import com.bloodbank.app.AppDelegate;
import com.bloodbank.db.Registration;
import com.bloodbank.ui.main.MainPagerAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private MainPagerAdapter mPagerAdapter;
    private Spinner mBloodGroupSpinner;
    private RecyclerView mSearchListView;
    private TextView mNoResultsText;
    private SearchAdapter mSearchAdapter;
    private String[] mBloodGroupSpinnerItems;
    private String mNumberToCallAfterPermissionGrant;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBloodGroupSpinner = view.findViewById(R.id.searchSpnBloodGroup);
        mSearchListView = view.findViewById(R.id.searchListView);
        mNoResultsText = view.findViewById(R.id.searchNoResultsText);

        mSearchAdapter = new SearchAdapter(this);
        mBloodGroupSpinnerItems = new String[]{
                getString(R.string.register_spn_select),
                "O+",
                "O-",
                "A+",
                "A-",
                "B+",
                "B-",
                "AB+",
                "AB-"
        };

        mBloodGroupSpinner.setAdapter(new ArrayAdapter<>(
                        getContext(),
                        android.R.layout.simple_spinner_item,
                        mBloodGroupSpinnerItems
                )
        );

        mSearchListView.setAdapter(mSearchAdapter);
        mSearchListView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mBloodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    mBloodGroupSpinner.setEnabled(false);
                    new Thread(() -> {
                        List<Registration> results = AppDelegate.db.getAppDao().search(mBloodGroupSpinnerItems[position]);
                        getActivity().runOnUiThread(() -> {
                            mSearchAdapter.setSearchResults(results);
                            mNoResultsText.setVisibility((results != null && results.size() > 0) ? View.INVISIBLE : View.VISIBLE);
                            mBloodGroupSpinner.setEnabled(true);
                        });
                    }).start();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 200: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.fromParts("tel", mNumberToCallAfterPermissionGrant, null));
                    startActivity(callIntent);
                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void callPhone(String number) {
        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_DENIED) {
            mNumberToCallAfterPermissionGrant = number;
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 200);
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.fromParts("tel", number, null));
            startActivity(callIntent);
        }
    }

    public void setPagerAdapter(MainPagerAdapter adapter) {
        this.mPagerAdapter = adapter;
    }
}
