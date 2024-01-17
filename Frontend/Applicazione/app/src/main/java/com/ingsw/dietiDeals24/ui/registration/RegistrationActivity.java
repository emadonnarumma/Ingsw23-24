package com.ingsw.dietiDeals24.ui.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.utility.stepper.RegistrationStepperAdapter;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class RegistrationActivity extends AppCompatActivity implements StepperLayout.StepperListener {
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

    @Override
    public void onCompleted(View completeButton) {

    }

    @Override
    public void onError(VerificationError verificationError) {

    }

    @Override
    public void onStepSelected(int newStepPosition) {
        if (newStepPosition == 2) { // Se sei nel terzo frammento (l'indice inizia da 0)
            stepperLayout.setCompleteButtonEnabled(false);
        } else {
            stepperLayout.setCompleteButtonEnabled(true);
        }
    }


    @Override
    public void onReturn() {

    }
}