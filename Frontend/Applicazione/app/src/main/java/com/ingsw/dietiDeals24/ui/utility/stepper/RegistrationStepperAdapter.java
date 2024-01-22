package com.ingsw.dietiDeals24.ui.utility.stepper;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.ingsw.dietiDeals24.ui.home.registration.fragment.MandatoryRegistrationInfoFragment;
import com.ingsw.dietiDeals24.ui.home.registration.fragment.OptionalRegistrationInfoFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

public class RegistrationStepperAdapter extends AbstractFragmentStepAdapter {
    private static final String CURRENT_STEP_POSITION_KEY = "0";




    public RegistrationStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }




    @Override
    public Step createStep(int position) {
        Bundle b = new Bundle();

        switch (position) {
            case 0:
                MandatoryRegistrationInfoFragment mandatoryRegistrationInfoFragment =
                        new MandatoryRegistrationInfoFragment();
                b.putInt(CURRENT_STEP_POSITION_KEY, position);
                mandatoryRegistrationInfoFragment.setArguments(b);

                return mandatoryRegistrationInfoFragment;

            case 1:
                OptionalRegistrationInfoFragment optionalRegistrationInfoFragment =
                        new OptionalRegistrationInfoFragment();
                b.putInt(CURRENT_STEP_POSITION_KEY, position);
                optionalRegistrationInfoFragment.setArguments(b);

                return optionalRegistrationInfoFragment;

            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
    }




    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        StepViewModel.Builder builder = new StepViewModel.Builder(context);
        switch (position) {
            case 0:
                builder
                        .setTitle("Informazioni obbligatorie")
                        .setEndButtonLabel("Personalizza")
                        .setBackButtonLabel("Login");
                break;

            case 1:
                builder
                        .setTitle("Informazioni opzionali")
                        .setEndButtonLabel("Registrati")
                        .setBackButtonLabel("Indietro");
                break;

            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
        return builder.create();
    }




    @Override
    public int getCount() {
        return 2;
    }
}