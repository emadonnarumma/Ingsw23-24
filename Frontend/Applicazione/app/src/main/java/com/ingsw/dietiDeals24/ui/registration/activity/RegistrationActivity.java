package com.ingsw.dietiDeals24.ui.registration.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.registration.stepper.RegistrationStepperAdapter;
import com.ingsw.dietiDeals24.ui.utility.CheckConnectionActivity;
import com.stepstone.stepper.StepperLayout;

public class RegistrationActivity extends CheckConnectionActivity {
    private StepperLayout stepperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        stepperLayout = findViewById(R.id.stepper_layout_registration);
        stepperLayout.setAdapter(new RegistrationStepperAdapter(getSupportFragmentManager(), this));
    }
}