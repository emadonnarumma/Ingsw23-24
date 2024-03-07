package com.ingsw.dietiDeals24.ui.registration.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.model.enumeration.Region;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.ui.home.HomeActivity;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.controller.RegistrationController;
import com.ingsw.dietiDeals24.model.User;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class OptionalRegistrationInfoFragment extends Fragment implements BlockingStep {
    private SmartMaterialSpinner<String> regionSmartSpinner;
    private EditText bioEditText;
    private User registeringUser = RegistrationController.user;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_optional_registration_info, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findTheViews();
        initRegionSmartSpinner();
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


    private void setRegisteringUserValues() {
        registeringUser.setRegion(Region.fromItalianString(regionSmartSpinner.getSelectedItem()));
        registeringUser.setBio(bioEditText.getText().toString());
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
        return new Thread(() -> {
            requireActivity().runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));
            try {

                RegistrationController.register().get();
                goToHomeActivity();

            } catch(ExecutionException e){

                if (e.getCause() instanceof AuthenticationException) {
                    requireActivity().runOnUiThread(() -> ToastManager.showToast(requireContext(), "Email già in uso"));
                } else if (e.getCause() instanceof ConnectionException) {
                    requireActivity().runOnUiThread(() -> ToastManager.showToast(requireContext(), "Errore di connessione"));
                }

            } catch(InterruptedException e){
                throw new RuntimeException("Interruzione non prevista");
            } finally {
                requireActivity().runOnUiThread(() -> progressBar.setVisibility(View.GONE));
            }
        });
    }

    private void goToHomeActivity() {
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
    }

    private void findTheViews() {
        progressBar = requireView().findViewById(R.id.progress_bar_registration_optional_info);
        progressBar.setVisibility(View.GONE);
        regionSmartSpinner = requireView().findViewById(R.id.region_spinner_registration);
        bioEditText = requireView().findViewById(R.id.bio_edit_text_registration);
    }


    private void initRegionSmartSpinner() {
        regionSmartSpinner.setItem(
                Arrays.asList(
                        getResources().getStringArray(R.array.italian_regions)
                )
        );
    }
}
