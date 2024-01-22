package com.ingsw.dietiDeals24.ui.home.registration.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.login.LoginActivity;
import com.ingsw.dietiDeals24.controller.RegistrationController;
import com.ingsw.dietiDeals24.model.User;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class MandatoryRegistrationInfoFragment extends Fragment implements BlockingStep {
    private EditText userNameEditText, emailEditText, passwordEditText;
    private User registeringUser = RegistrationController.user;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_mandatory_registration_info, container, false);
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findTheViews();
    }




    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        setRegisteringUserValues();
        callback.goToNextStep();
    }




    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }




    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
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


    private void findTheViews() {
        userNameEditText = requireView().findViewById(R.id.username_edit_text_registration);
        emailEditText = requireView().findViewById(R.id.email_edit_text_registration);
        passwordEditText = requireView().findViewById(R.id.password_edit_text_registration);
    }


    private void setRegisteringUserValues() {
        registeringUser.setPassword(passwordEditText.getText().toString());
        registeringUser.setEmail(emailEditText.getText().toString());
        registeringUser.setUsername(userNameEditText.getText().toString());
    }
}
