package com.ingsw.dietiDeals24.ui.registration.fragment;

import static java.lang.Thread.sleep;

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
import com.ingsw.dietiDeals24.ui.login.LoginActivity;
import com.ingsw.dietiDeals24.controller.RegistrationController;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.ui.utility.PopupGeneratorOf;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MandatoryRegistrationInfoFragment extends Fragment implements BlockingStep {

    private EditText userNameEditText, emailEditText, passwordEditText, passwordConfirmationEditText;
    private User registeringUser = RegistrationController.user;

    private TextWatcher registrationTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            RegistrationController.registrationInputChanged(
                    userNameEditText.getText().toString(),
                    emailEditText.getText().toString(),
                    passwordEditText.getText().toString(),
                    passwordConfirmationEditText.getText().toString(),
                    getResources()
            );
        }
    };

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
        observeRegistrationFormState();
    }

    private void setUserNameTextView() {
        userNameEditText = requireView().findViewById(R.id.username_edit_text_madatory_registration_info);
        userNameEditText.addTextChangedListener(registrationTextWatcher);
    }

    private void setEmailTextView() {
        emailEditText = requireView().findViewById(R.id.email_edit_text_madatory_registration_info);
        emailEditText.addTextChangedListener(registrationTextWatcher);
    }

    private void setPasswordTextView() {
        passwordEditText = requireView().findViewById(R.id.password_edit_text_mandatory_registration_info);
        passwordEditText.addTextChangedListener(registrationTextWatcher);
    }

    private void setPasswordConfirmationEditText() {
        passwordConfirmationEditText = requireView().findViewById(R.id.password_confirmation_edit_text_mandatory_registration_info);
        passwordConfirmationEditText.addTextChangedListener(registrationTextWatcher);
    }

    private void observeRegistrationFormState() {
        RegistrationController.getRegistrationFormState().observe(getViewLifecycleOwner(), registrationFormState -> {
            if (registrationFormState == null) {
                return;
            }
            String usernameError = registrationFormState.getUsernameError();
            String emailError = registrationFormState.getEmailError();
            String passwordError = registrationFormState.getPasswordError();
            String passwordConfirmationError = registrationFormState.getRepeatPasswordError();
            if (usernameError != null) {
                userNameEditText.setError(usernameError);
            }
            if (emailError != null) {
                emailEditText.setError(emailError);
            }
            if (passwordError != null) {
                passwordEditText.setError(passwordError);
            }
            if (passwordConfirmationError != null) {
                passwordConfirmationEditText.setError(passwordConfirmationError);
            }
        });
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        PopupDialog loading = PopupGeneratorOf.loadingPopup(getContext());
        new Thread(() -> {
            try {
                if (RegistrationController.emailAlreadyExists(emailEditText.getText().toString()).get()) {
                    requireActivity().runOnUiThread(() -> PopupGeneratorOf.errorPopup(getContext(), getString(R.string.email_already_exists)));
                } else {
                    requireActivity().runOnUiThread(() -> {
                        if (verifyStep() == null) {
                            setRegisteringUserValues();
                            callback.goToNextStep();
                        }
                    });
                }
            } catch (ExecutionException e) {
                requireActivity().runOnUiThread(() -> PopupGeneratorOf.errorPopup(getContext(), e.getCause().getMessage()));
            } catch (InterruptedException e) {
                requireActivity().runOnUiThread(() -> PopupGeneratorOf.errorPopup(getContext(), "Operazione interrotta, riprovare"));
            } finally {
                requireActivity().runOnUiThread(loading::dismissDialog);
            }
        }).start();
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
        if(RegistrationController.getRegistrationFormState().getValue() == null) {
            requireActivity().runOnUiThread(() -> ToastManager.showToast(requireContext(), "Inserire tutti i campi"));
            return new VerificationError("Inserire tutti i campi");
        }

        String usernameError = RegistrationController.getRegistrationFormState().getValue().getUsernameError();
        String emailError = RegistrationController.getRegistrationFormState().getValue().getEmailError();
        String passwordError = RegistrationController.getRegistrationFormState().getValue().getPasswordError();
        String passwordConfirmationError = RegistrationController.getRegistrationFormState().getValue().getRepeatPasswordError();

        if (usernameError != null || emailError != null || passwordError != null || passwordConfirmationError != null) {
            requireActivity().runOnUiThread(() -> ToastManager.showToast(requireContext(), "Controllare che tutti i campi siano corretti"));
            return new VerificationError("Controllare che tutti i campi siano corretti");
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
