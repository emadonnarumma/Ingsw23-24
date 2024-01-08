package com.ingsw.dietiDeals24.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.activity.ui.registration.MandatoryRegistrationInfoFragment;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MandatoryRegistrationInfoFragment.newInstance())
                    .commitNow();
        }
    }
}