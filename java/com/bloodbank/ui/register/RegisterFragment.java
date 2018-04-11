package com.bloodbank.ui.register;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bloodbank.R;
import com.bloodbank.app.AppDelegate;
import com.bloodbank.db.AppDao;
import com.bloodbank.db.Registration;
import com.bloodbank.ui.main.MainPagerAdapter;
import com.bloodbank.ui.stats.StatisticsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements TextWatcher {

    private MainPagerAdapter mPagerAdapter;
    private Spinner mBloodGroupSpinner;
    private TextInputLayout mNameInput;
    private Spinner mGenderSpinner;
    private TextInputLayout mEmailInput;
    private TextInputLayout mMobileNumberInput;
    private TextInputLayout mAddressInput;
    private TextInputLayout mStreetInput;
    private TextInputLayout mCityInput;
    private TextInputLayout mStateInput;
    private TextInputLayout mCountryInput;
    private Button mSubmitButton;

    private TextInputLayout[] mInputFields;

    public RegisterFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBloodGroupSpinner = view.findViewById(R.id.registerSpnBloodGroup);
        mNameInput = view.findViewById(R.id.registerInputName);
        mGenderSpinner = view.findViewById(R.id.registerSpnGender);
        mEmailInput = view.findViewById(R.id.registerInputEmail);
        mMobileNumberInput = view.findViewById(R.id.registerInputMobileNumber);
        mAddressInput = view.findViewById(R.id.registerInputAddress);
        mStreetInput = view.findViewById(R.id.registerInputStreet);
        mCityInput = view.findViewById(R.id.registerInputCity);
        mStateInput = view.findViewById(R.id.registerInputState);
        mCountryInput = view.findViewById(R.id.registerInputCountry);
        mSubmitButton = view.findViewById(R.id.registerBtnSubmit);

        mInputFields = new TextInputLayout[]{
                mNameInput,
                mEmailInput,
                mMobileNumberInput,
                mAddressInput,
                mStreetInput,
                mCityInput,
                mStateInput,
                mCountryInput
        };

        for (TextInputLayout i : mInputFields) {
            i.getEditText().addTextChangedListener(this);
        }

        mGenderSpinner.setAdapter(new ArrayAdapter<>(
                        getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        new String[]{
                                getString(R.string.register_spn_select),
                                getString(R.string.register_gender_male),
                                getString(R.string.register_gender_female)
                        }
                )
        );

        mBloodGroupSpinner.setAdapter(new ArrayAdapter<>(
                        getContext(),
                        android.R.layout.simple_spinner_item,
                        new String[]{
                                getString(R.string.register_spn_select),
                                "O+",
                                "O-",
                                "A+",
                                "A-",
                                "B+",
                                "B-",
                                "AB+",
                                "AB-"
                        }
                )
        );
        mSubmitButton.setOnClickListener(v -> {
            if (validateInputs() && validateSpinners()) {

                Registration r = new Registration();
                r.name = getText(mNameInput);
                r.gender = mGenderSpinner.getSelectedItemPosition() == 1 ? 'M' : 'F';
                r.email = getText(mEmailInput);
                r.mobileNumber = getText(mMobileNumberInput);
                r.address = getText(mAddressInput);
                r.street = getText(mStreetInput);
                r.city = getText(mCityInput);
                r.state = getText(mStateInput);
                r.country = getText(mCountryInput);
                r.bloodGroup = (String) mBloodGroupSpinner.getSelectedItem();

                new Thread(() -> {
                    AppDao dao = AppDelegate.db.getAppDao();
                    Registration existingRegistration = dao.findByEmail(r.email);
                    if (existingRegistration != null) {
                        getActivity().runOnUiThread(() -> {
                            Toast.makeText(getContext(), String.format(getString(R.string.registration_err_already_exists), r.email), Toast.LENGTH_SHORT).show();
                        });
                        return;
                    }
                    dao.insert(r);
                    getActivity().runOnUiThread(() -> {
                        clearInputFields();
                        Toast.makeText(getContext(), R.string.register_success, Toast.LENGTH_SHORT).show();
                        StatisticsFragment statisticsFragment = mPagerAdapter.getStatisticsFragment();
                        if (statisticsFragment != null) {
                            statisticsFragment.refresh();
                        }
                    });
                }).start();
            }
        });
    }

    private String getText(TextInputLayout view) {
        EditText et = view.getEditText();
        if (et != null) {
            return et.getText().toString();
        }
        return null;
    }

    private void clearInputFields() {

        for (TextInputLayout i : mInputFields) {
            i.setError(null);
            i.setErrorEnabled(false);
            i.getEditText().setText(null);
        }

        mGenderSpinner.setSelection(0);
        mBloodGroupSpinner.setSelection(0);
    }

    private boolean validateInputs() {
        for (TextInputLayout i : mInputFields) {
            if (i.getEditText().getText().length() == 0) {
                i.setErrorEnabled(true);
                i.setError(getString(R.string.register_err_field_required));
                return false;
            }
        }
        return true;
    }

    private boolean validateSpinners() {
        if (mGenderSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(getContext(), R.string.register_err_select_gender, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mBloodGroupSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(getContext(), R.string.register_err_select_blood_grp, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void setPagerAdapter(MainPagerAdapter adapter) {
        this.mPagerAdapter = adapter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        for (TextInputLayout i : mInputFields) {
            if (i.isErrorEnabled()) {
                i.setErrorEnabled(false);
                i.setError(null);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
