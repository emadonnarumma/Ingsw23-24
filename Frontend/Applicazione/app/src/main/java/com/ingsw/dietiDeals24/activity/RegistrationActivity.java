package com.ingsw.dietiDeals24.activity;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.ActivityKt;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.aceinteract.android.stepper.StepperNavigationView;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.activity.ui.registration.mandatoryInfo.RegistrationMandatoryInfoFragment;

import kotlin.Unit;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RegistrationMandatoryInfoFragment.newInstance())
                    .commitNow();
        }


//           StepperNavigationView stepper = findViewById(R.id.stepper);
//            NavController navController = findNavController(navHostFragment);
//            stepper.setupWithNavController(navController);


        StepperNavigationView stepper = findViewById(R.id.stepper);
        NavController navController = Navigation.findNavController(this, R.id.frame_stepper);
        stepper.setupWithNavController(navController);

    }
}