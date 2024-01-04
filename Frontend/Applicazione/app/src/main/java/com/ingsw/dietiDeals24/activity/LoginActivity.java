package com.ingsw.dietiDeals24.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.LogInController;

public class LoginActivity extends AppCompatActivity {
    EditText emailView, passwordView;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailView = findViewById(R.id.emailEditText);
        passwordView = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loginButton.setOnClickListener(v -> {
            String email = emailView.getText().toString();
            String password = passwordView.getText().toString();

            LogInController.login(email, password);
            if (LogInController.isLoggedIn) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Credenziali non valide", Toast.LENGTH_SHORT).show();
            }
        });
    }
}