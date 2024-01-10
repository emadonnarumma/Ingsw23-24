package com.ingsw.dietiDeals24.activity.ui.registration.optional;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.activity.HomeActivity;
import com.ingsw.dietiDeals24.activity.utility.ToastManager;
import com.ingsw.dietiDeals24.controller.LogInController;
import com.ingsw.dietiDeals24.controller.RegistrationController;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

public class OptionalInfoRegistrationFragment extends Fragment implements BlockingStep {

    private SmartMaterialSpinner<String> regionSmartSpinner;

    public static OptionalInfoRegistrationFragment newInstance() {
        return new OptionalInfoRegistrationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_optional_info_registration, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSpinner();



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
        callback.goToPrevStep();
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

    private void initSpinner() {
        regionSmartSpinner = getActivity().findViewById(R.id.spinner1);
        List<String> regionList = Arrays.asList(getResources().getStringArray(R.array.italian_regions));

        regionSmartSpinner.setItem(regionList);

        regionSmartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    @NonNull
    private Thread getLoginThread(String email, String password) {
        return new Thread(() ->
        {
            boolean isLoggedIn = tryToRegister();

            if (isLoggedIn) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
            } else {
                getActivity().runOnUiThread(() -> ToastManager.showToast(
                        getContext(), "Registrazione fallita"));
            }
        });
    }

    private static boolean tryToRegister() {
        Future<Boolean> future = RegistrationController.register();
        boolean regisrationSuccessfull;
        try {
            regisrationSuccessfull = future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return regisrationSuccessfull;
    }
}