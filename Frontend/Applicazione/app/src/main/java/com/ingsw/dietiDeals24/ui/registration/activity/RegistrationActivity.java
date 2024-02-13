package com.ingsw.dietiDeals24.ui.registration.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.registration.stepper.RegistrationStepperAdapter;
import com.stepstone.stepper.StepperLayout;

public class RegistrationActivity extends AppCompatActivity {
    private StepperLayout stepperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        stepperLayout = findViewById(R.id.stepper_layout_registration);
        stepperLayout.setAdapter(new RegistrationStepperAdapter(getSupportFragmentManager(), this));

        //TODO: jonny - implementare la verifica dei dati inseriti dall'utente
        //stepperLayout.setNextButtonVerificationFailed(false);
        //stepperLayout.setCompleteButtonVerificationFailed(false);

    }
}