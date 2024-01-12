package com.ingsw.dietiDeals24.activity.ui.registration;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.activity.HomeActivity;
import com.ingsw.dietiDeals24.activity.utility.RegionStringConverter;
import com.ingsw.dietiDeals24.activity.utility.ToastManager;
import com.ingsw.dietiDeals24.controller.RegistrationController;
import com.ingsw.dietiDeals24.enumeration.Region;
import com.ingsw.dietiDeals24.model.User;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

public class OptionalInfoRegistrationFragment extends Fragment implements BlockingStep {

    private SmartMaterialSpinner<String> regionSmartSpinner;
    private EditText bioEditText;
    private User registeringUser = RegistrationController.user;

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
        findTheViews();
        setTheSpinnerListener();
    }



    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
    }


    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        setRegisteringUserValues();

        Thread thread = getRegistrationThread();
        thread.start();
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


    @NonNull
    private Thread getRegistrationThread() {
        return new Thread(() ->
        {
            boolean registrationSuccessFull = tryToRegister();

            if (registrationSuccessFull) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
            } else {
                requireActivity().runOnUiThread(() -> ToastManager.showToast(
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


    private void findTheViews() {
        regionSmartSpinner = requireView().findViewById(R.id.region_spinner_registration);
        bioEditText = requireView().findViewById(R.id.bio_edit_text_registration);
    }


    private void setTheSpinnerListener() {
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


    private void setRegisteringUserValues() {
        registeringUser.setRegion(RegionStringConverter.convert(regionSmartSpinner.getSelectedItem()));
        registeringUser.setBio(bioEditText.getText().toString());
    }
}
