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

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.model.enumeration.Region;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.ui.home.HomeActivity;
import com.ingsw.dietiDeals24.ui.utility.PopupGeneratorOf;
import com.ingsw.dietiDeals24.controller.RegistrationController;
import com.ingsw.dietiDeals24.model.User;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class OptionalRegistrationInfoFragment extends Fragment implements BlockingStep {
    private SmartMaterialSpinner<String> regionSmartSpinner;
    private EditText bioEditText;
    private User registeringUser = RegistrationController.user;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_optional_registration_info, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
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
        if(regionSmartSpinner.getSelectedItem() != null)
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
        PopupDialog loading = PopupDialog.getInstance(requireContext());
        return new Thread(() -> {
            try {

                RegistrationController.register().get();
                goToHomeActivity();
                requireActivity().runOnUiThread(() -> PopupGeneratorOf.successPopup(requireContext(), getString(R.string.registration_successful)));

            } catch(ExecutionException e){

                if (e.getCause() instanceof AuthenticationException) {
                    requireActivity().runOnUiThread(() -> PopupGeneratorOf.errorPopup(requireContext(), getString(R.string.email_already_exists)));
                } else if (e.getCause() instanceof ConnectionException) {
                    requireActivity().runOnUiThread(() -> PopupGeneratorOf.errorPopup(requireContext(), "Errore di connessione"));
                }

            } catch(InterruptedException e){
                requireActivity().runOnUiThread(() -> PopupGeneratorOf.errorPopup(requireContext(), "Operazione interrotta, riprovare"));
            } finally {
                requireActivity().runOnUiThread(loading::dismissDialog);
            }
        });
    }

    private void goToHomeActivity() {
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
    }

    private void setupViews() {
        regionSmartSpinner = requireView().findViewById(R.id.region_spinner_registration);
        regionSmartSpinner.setSelection(0);
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
