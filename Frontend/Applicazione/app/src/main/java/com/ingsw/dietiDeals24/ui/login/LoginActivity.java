package com.ingsw.dietiDeals24.ui.login;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.formstate.DownwardAuctionAttributesFormState;
import com.ingsw.dietiDeals24.controller.formstate.LoginFormState;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.ui.utility.OnNavigateToHomeActivityFragmentListener;
import com.ingsw.dietiDeals24.ui.registration.activity.RegistrationActivity;
import com.ingsw.dietiDeals24.ui.utility.PopupGeneratorOf;
import com.ingsw.dietiDeals24.controller.LogInController;


import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity implements OnNavigateToHomeActivityFragmentListener {

    private TextInputLayout emailTextInputLayout, passwordTextInputLayout;
    private TextView registrationTextView, emailErrorTextView, passwordErrorTextView;
    private EditText emailEditText, passwordEditText;
    private CircularProgressButton loginButton;

//    private FirebaseAnalytics firebaseAnalytics;

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            LogInController.loginInputChanged(
                    emailEditText.getText().toString(),
                    passwordEditText.getText().toString(),
                    getResources()
            );
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//
//        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
//
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Schermata di Login");
//        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "LoginActivity");
//        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
//        firebaseAnalytics.setAnalyticsCollectionEnabled(true);

        setupLoginButton();
        setupTextViews();
        setupEditText();
        setupTextInputLayouts();
        observeLoginFormState();
        disableBackPress();
    }

    private void setupTextInputLayouts() {
        emailTextInputLayout = findViewById(R.id.email_layout_login);
        passwordTextInputLayout = findViewById(R.id.password_layout_login);
    }

    private void setupLoginButton() {
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            loginButton.startAnimation();
            Thread thread = getLoginThread(email, password);
            thread.start();
        });
    }

    private void setupTextViews() {
        passwordErrorTextView = findViewById(R.id.password_error_text_view_login);
        emailErrorTextView = findViewById(R.id.email_error_text_view_login);
        setupRegistrationTextView();
    }

    private void setupRegistrationTextView() {
        registrationTextView = findViewById(R.id.go_to_registration_text_view);
        registrationTextView.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
            startActivity(intent);
        });
    }

    private void setupEditText() {
        setupEmailEditText();
        setupPasswordEditText();
    }

    private void setupPasswordEditText() {
        passwordEditText = findViewById(R.id.password_edit_text_login);
        passwordEditText.addTextChangedListener(loginTextWatcher);
    }

    private void setupEmailEditText() {
        emailEditText = findViewById(R.id.email_edit_text_login);
        emailEditText.addTextChangedListener(loginTextWatcher);
    }

    private void observeLoginFormState() {
        LogInController.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getEmailError() != null) {
                showErrorAndChangeColor(
                        loginFormState,
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
            if (loginFormState.getPasswordError() != null) {
                showErrorAndChangeColor(
                        loginFormState,
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
        });
    }

    private void hideErrorAndChangeColor(EditText editText, TextView errorTextView, TextInputLayout textInputLayout) {
        errorTextView.setVisibility(View.GONE);
        editText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        textInputLayout.setBoxStrokeColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
    }

    private void showErrorAndChangeColor(LoginFormState formState, EditText editText, TextView errorTextView, TextInputLayout layout) {
        if (errorTextView.equals(emailErrorTextView)) {
            errorTextView.setText(formState.getEmailError());
        } else {
            errorTextView.setText(formState.getPasswordError());
        }
        errorTextView.setVisibility(View.VISIBLE);
        editText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        layout.setBoxStrokeColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
    }

    @NonNull
    private Thread getLoginThread(String email, String password) {
        return new Thread(() -> {
            try {

                LogInController.login(email, password).get();
                OnNavigateToHomeActivityFragmentListener.navigateTo("HomeFragment", getApplicationContext());

            } catch (ExecutionException e) {

                if (e.getCause() instanceof AuthenticationException) {
                    runOnUiThread(() -> loginButton.revertAnimation());
                    runOnUiThread(() -> PopupGeneratorOf.errorPopup(this, "Credenziali errate"));

                } else if (e.getCause() instanceof ConnectionException) {
                    runOnUiThread(() -> loginButton.revertAnimation());
                    runOnUiThread(() -> PopupGeneratorOf.errorPopup(this, "Errore di connessione"));
                }

            } catch (InterruptedException e) {
                runOnUiThread(() -> PopupGeneratorOf.errorPopup(this, "Operazione interrotta, riprovare"));
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        runOnUiThread(() -> loginButton.revertAnimation());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        feedTheCollector();
    }

    private void feedTheCollector() {
        loginButton.dispose();
        loginButton = null;
        emailEditText = null;
        passwordEditText = null;
        registrationTextView = null;
    }

    private void disableBackPress() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishAffinity();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}