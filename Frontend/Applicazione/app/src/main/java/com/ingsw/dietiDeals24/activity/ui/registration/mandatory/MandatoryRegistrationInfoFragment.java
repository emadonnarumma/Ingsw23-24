package com.ingsw.dietiDeals24.activity.ui.registration.mandatory;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.activity.ui.registration.RegistrationInfoViewModel;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class MandatoryRegistrationInfoFragment extends Fragment implements BlockingStep {

    private RegistrationInfoViewModel viewModel;

    public static MandatoryRegistrationInfoFragment newInstance() {
        return new MandatoryRegistrationInfoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RegistrationInfoViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_mandatory_registration_info, container, false);
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}