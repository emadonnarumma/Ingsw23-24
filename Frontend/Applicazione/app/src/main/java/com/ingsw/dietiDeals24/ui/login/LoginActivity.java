package com.ingsw.dietiDeals24.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.OnNavigateToHomeActivityFragmentListener;
import com.ingsw.dietiDeals24.ui.registration.activity.RegistrationActivity;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.controller.LogInController;


import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity implements OnNavigateToHomeActivityFragmentListener {

    private TextView registrationTextView;
    private EditText emailEditText, passwordEditText;
    private CircularProgressButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email_edit_text_login);
        passwordEditText = findViewById(R.id.password_edit_text_login);
        loginButton = findViewById(R.id.login_button);
        registrationTextView = findViewById(R.id.go_to_registration_text_view);

        userPressRegistrationButton();
        userPressLoginButton();
    }


    private void userPressRegistrationButton() {
        registrationTextView.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
            startActivity(intent);
        });
    }


    private void userPressLoginButton() {
        loginButton.setOnClickListener(v -> {

            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            loginButton.startAnimation();
            Thread thread = getLoginThread(email, password);
            thread.start();
        });
    }


    @NonNull
    private Thread getLoginThread(String email, String password) {
        return new Thread(() -> {
            try {

                LogInController.login(email, password).get();
                onNavigateToHomeActivityFragmentRequest("HomeFragment", getApplicationContext());

            } catch (ExecutionException e) {

                if (e.getCause() instanceof AuthenticationException) {
                    runOnUiThread(() -> loginButton.revertAnimation());
                    runOnUiThread(() -> ToastManager.showToast(getApplicationContext(), "Credenziali errate"));

                } else if (e.getCause() instanceof ConnectionException) {
                    runOnUiThread(() -> loginButton.revertAnimation());
                    runOnUiThread(() -> ToastManager.showToast(getApplicationContext(), "Errore di connessione"));
                }

            } catch (InterruptedException e) {
                throw new RuntimeException("Interruzione non prevista");
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

}