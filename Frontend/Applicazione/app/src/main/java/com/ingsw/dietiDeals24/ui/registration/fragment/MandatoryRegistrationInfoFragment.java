package com.ingsw.dietiDeals24.ui.registration.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.RegistrationController;
import com.ingsw.dietiDeals24.controller.formstate.RegistrationFormState;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.ui.login.LoginActivity;
import com.ingsw.dietiDeals24.utility.PopupGenerator;
import com.ingsw.dietiDeals24.utility.ToastManager;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.concurrent.ExecutionException;

public class MandatoryRegistrationInfoFragment extends Fragment implements BlockingStep {

    private TextView usernameErrorTextView, emailErrorTextView, passwordErrorTextView, passwordConfirmationErrorTextView;
    private TextInputLayout usernameTextInputLayout, emailTextInputLayout, passwordTextInputLayout, passwordConfirmationTextInputLayout;
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
        setupTextViews();
        setupTextInputLayouts();
        setPasswordConfirmationEditText();
        observeRegistrationFormState();
    }

    private void setupTextInputLayouts() {
        usernameTextInputLayout = requireView().findViewById(R.id.username_layout_mandatory_registration_info);
        emailTextInputLayout = requireView().findViewById(R.id.email_layout_mandatory_registration_info);
        passwordTextInputLayout = requireView().findViewById(R.id.password_layout_mandatory_registration_info);
        passwordConfirmationTextInputLayout = requireView().findViewById(R.id.password_confirmation_layout_mandatory_registration_info);
    }

    private void setupTextViews() {
        setupUserNameTextView();
        setEmailTextView();
        setPasswordTextView();
        usernameErrorTextView = requireView().findViewById(R.id.username_error_text_view_mandatory_registration_info);
        emailErrorTextView = requireView().findViewById(R.id.email_error_text_view_mandatory_registration_info);
        passwordErrorTextView = requireView().findViewById(R.id.password_error_text_view_mandatory_registration_info);
        passwordConfirmationErrorTextView = requireView().findViewById(R.id.password_confirmation_error_text_view_mandatory_registration_info);
    }

    private void setupUserNameTextView() {
        userNameEditText = requireView().findViewById(R.id.username_edit_text_madatory_registration_info);
        userNameEditText.addTextChangedListener(registrationTextWatcher);
    }

    private void setEmailTextView() {
        emailEditText = requireView().findViewById(R.id.email_edit_text_mandatory_registration_info);
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
            if (registrationFormState.getUsernameError() != null) {
                showErrorAndChangeColor(
                        registrationFormState,
                        userNameEditText,
                        usernameErrorTextView,
                        usernameTextInputLayout
                );
            } else {
                hideErrorAndChangeColor(
                        userNameEditText,
                        usernameErrorTextView,
                        usernameTextInputLayout
                );
            }
            if (registrationFormState.getEmailError() != null) {
                showErrorAndChangeColor(
                        registrationFormState,
                        emailEditText,
                        emailErrorTextView,
                        emailTextInputLayout
                );
            } else {
                hideErrorAndChangeColor(
                        emailEditText,
                        emailErrorTextView,
                        emailTextInputLayout
                );
            }
            if (registrationFormState.getPasswordError() != null) {
                showErrorAndChangeColor(
                        registrationFormState,
                        passwordEditText,
                        passwordErrorTextView,
                        passwordTextInputLayout
                );
            } else {
                hideErrorAndChangeColor(
                        passwordEditText,
                        passwordErrorTextView,
                        passwordTextInputLayout
                );
            }
            if (registrationFormState.getRepeatPasswordError() != null) {
                showErrorAndChangeColor(
                        registrationFormState,
                        passwordConfirmationEditText,
                        passwordConfirmationErrorTextView,
                        passwordConfirmationTextInputLayout
                );
            } else {
                hideErrorAndChangeColor(
                        passwordConfirmationEditText,
                        passwordConfirmationErrorTextView,
                        passwordConfirmationTextInputLayout
                );
            }
        });
    }

    private void hideErrorAndChangeColor(EditText editText, TextView errorTextView, TextInputLayout textInputLayout) {
        errorTextView.setVisibility(View.GONE);
        editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
        textInputLayout.setBoxStrokeColor(ContextCompat.getColor(requireContext(), R.color.green));
    }

    private void showErrorAndChangeColor(RegistrationFormState formState, EditText editText, TextView errorTextView, TextInputLayout layout) {
        if (errorTextView.equals(usernameErrorTextView)) {
            errorTextView.setText(formState.getUsernameError());
        } else if (errorTextView.equals(emailErrorTextView)) {
            errorTextView.setText(formState.getEmailError());
        } else if (errorTextView.equals(passwordErrorTextView)) {
            errorTextView.setText(formState.getPasswordError());
        } else {
            errorTextView.setText(formState.getRepeatPasswordError());
        }
        errorTextView.setVisibility(View.VISIBLE);
        editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
        layout.setBoxStrokeColor(ContextCompat.getColor(requireContext(), R.color.red));
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        PopupDialog loading = PopupGenerator.loadingPopup(getContext());
        new Thread(() -> {
            try {
                if (RegistrationController.emailAlreadyExists(emailEditText.getText().toString()).get()) {
                    requireActivity().runOnUiThread(() -> PopupGenerator.errorPopup(getContext(), getString(R.string.email_already_exists)));
                } else {
                    requireActivity().runOnUiThread(() -> {
                        if (verifyStep() == null) {
                            setRegisteringUserValues();
                            callback.goToNextStep();
                        }
                    });
                }
            } catch (ExecutionException e) {
                requireActivity().runOnUiThread(() -> PopupGenerator.errorPopup(getContext(), e.getCause().getMessage()));
            } catch (InterruptedException e) {
                requireActivity().runOnUiThread(() -> PopupGenerator.errorPopup(getContext(), "Operazione interrotta, riprovare"));
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
