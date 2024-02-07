package com.ingsw.dietiDeals24.ui.registration.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.ui.login.LoginActivity;
import com.ingsw.dietiDeals24.controller.RegistrationController;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MandatoryRegistrationInfoFragment extends Fragment implements BlockingStep {

    private EditText userNameEditText, emailEditText, passwordEditText, passwordConfirmationEditText;
    private User registeringUser = RegistrationController.user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mandatory_registration_info, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUserNameTextView();
        setEmailTextView();
        setPasswordTextView();
        setPasswordConfirmationEditText();
    }

    private void setUserNameTextView() {
        userNameEditText = requireView().findViewById(R.id.username_edit_text_madatory_registration_info);
    }

    private void setEmailTextView() {
        emailEditText = requireView().findViewById(R.id.email_edit_text_madatory_registration_info);
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                CompletableFuture<Boolean> emailAlredyExists = RegistrationController.emailAlreadyExists(s.toString());
                try {

                    if (emailAlredyExists.get()) {
                        emailEditText.setError("Email già in uso");
                    }

                } catch (ExecutionException e) {

                    if (e.getCause() instanceof ConnectionException) {
                        requireActivity().runOnUiThread(() -> ToastManager.showToast(requireContext(), "Errore di connessione"));
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void setPasswordTextView() {
        passwordEditText = requireView().findViewById(R.id.password_edit_text_mandatory_registration_info);
    }

    private void setPasswordConfirmationEditText() {
        passwordConfirmationEditText = requireView().findViewById(R.id.password_confirmation_edit_text_mandatory_registration_info);
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
       if (verifyStep() == null) {
            setRegisteringUserValues();
            callback.goToNextStep();
        }
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
        String username = userNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordConfirmation = passwordConfirmationEditText.getText().toString();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty()) {

            requireActivity().runOnUiThread(() -> ToastManager.showToast(requireContext(), "Tutti i campi devono essere compilati"));
            return new VerificationError("Tutti i campi devono essere compilati");

        } else if (emailEditText.getError() != null) {

            requireActivity().runOnUiThread(() -> ToastManager.showToast(requireContext(), "Email già in uso"));
            return new VerificationError("Email già in uso");

        }


        return null;
    }

    @Override
    public void onSelected() {

    }


    @Override
    public void onError(@NonNull VerificationError error) {

    }

    private void setRegisteringUserValues() {
        registeringUser.setPassword(passwordEditText.getText().toString());
        registeringUser.setEmail(emailEditText.getText().toString());
        registeringUser.setName(userNameEditText.getText().toString());
    }
}
