package com.ingsw.dietiDeals24.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.enumeration.Region;
import com.ingsw.dietiDeals24.enumeration.Role;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.ui.home.registration.activity.RegistrationActivity;
import com.ingsw.dietiDeals24.ui.home.HomeActivity;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.controller.LogInController;

import java.io.IOException;
import java.util.concurrent.Future;

public class LoginActivity extends AppCompatActivity {
    TextView registrationTextView;
    EditText emailEditText, passwordEditText;
    CircularProgressButton loginButton;


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
            LogInController.loggedUser = new User(Role.BUYER, "tes", "tes@tes.com", "tes", "tes", Region.ABRUZZO);
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);

            /*String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            loginButton.startAnimation();
            Thread thread = getLoginThread(email, password);
            thread.start();*/
        });
    }


    @NonNull
    private Thread getLoginThread(String email, String password) {
        return new Thread(() -> {
            boolean isLoggedIn;

            try {
                isLoggedIn = tryToLogin(email, password);

                if (isLoggedIn) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                } else {
                    runOnUiThread(() -> loginButton.revertAnimation());
                    runOnUiThread(() -> ToastManager.showToast(getApplicationContext(), "Credenziali errate"));
                }

            } catch (IOException e) {
                runOnUiThread(() -> loginButton.revertAnimation());
                runOnUiThread(() -> ToastManager.showToast(getApplicationContext(), "Errore di connessione"));
            }

        });
    }


    private boolean tryToLogin(String email, String password) throws IOException {
        boolean isLoggedIn;

        try {
            Future<Boolean> future = LogInController.login(email, password);
            isLoggedIn = future.get();
        } catch (Exception e) {
            throw new IOException();
        }

        return isLoggedIn;
    }
}