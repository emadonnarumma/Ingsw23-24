package com.ingsw.dietiDeals24.activity.ui.registration.mandatoryInfo;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingsw.dietiDeals24.R;

public class RegistrationMandatoryInfoFragment extends Fragment {

    private RegistrationMandatoryInfoViewModel mViewModel;

    public static RegistrationMandatoryInfoFragment newInstance() {
        return new RegistrationMandatoryInfoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegistrationMandatoryInfoViewModel.class);
        // TODO: Use the ViewModel
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration_mandatory_info, container, false);
    }

}