package com.ingsw.dietiDeals24;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.ingsw.dietiDeals24.dao.UserDao;
import com.ingsw.dietiDeals24.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}