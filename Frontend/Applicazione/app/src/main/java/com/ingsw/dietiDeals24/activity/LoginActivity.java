package com.ingsw.dietiDeals24.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ingsw.dietiDeals24.R;

public class LoginActivity extends AppCompatActivity {
    View emailView, passwordView;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.20:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserDao userDao = retrofit.create(UserDao.class);
        Call<User> call = userDao.getUserByEmailAndPassword("admin@admin.com", "admin");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User u = response.body();
                TextView prova = findViewById(R.id.Prova);
                prova.setText(u.username);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    */
        emailView = findViewById(R.id.emailEditText);
        passwordView = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });
    }
}