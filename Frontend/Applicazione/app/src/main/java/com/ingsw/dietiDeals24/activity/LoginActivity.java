package com.ingsw.dietiDeals24.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.activity.utility.ToastManager;
import com.ingsw.dietiDeals24.controller.LogInController;

import java.util.concurrent.Future;

public class LoginActivity extends AppCompatActivity {
    TextView registrationTextView;
    EditText emailEditText, passwordEditText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registrationTextView = findViewById(R.id.registrationTextView);

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

            Thread thread = getLoginThread(email, password);
            thread.start();
        });
    }

    @NonNull
    private Thread getLoginThread(String email, String password) {
        return new Thread(() ->
        {
            boolean isLoggedIn = tryToLogin(email, password);

            if (isLoggedIn) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            } else {
                runOnUiThread(() -> ToastManager.showToast(getApplicationContext(), "Credenziali errate"));
            }
        });
    }

    private static boolean tryToLogin(String email, String password) {
        Future<Boolean> future = LogInController.login(email, password);
        boolean isLoggedIn;
        try {
            isLoggedIn = future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return isLoggedIn;
    }
}