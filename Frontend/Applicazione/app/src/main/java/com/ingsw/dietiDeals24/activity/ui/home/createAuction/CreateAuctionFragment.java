package com.ingsw.dietiDeals24.activity.ui.home.createAuction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.activity.LoginActivity;
import com.ingsw.dietiDeals24.activity.utility.stepper.CreateAuctionStepperAdapter;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class CreateAuctionFragment extends AppCompatActivity {
    StepperLayout stepperLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //stepperLayout = findViewById(R.id.stepper_layout_create_auction);
        //stepperLayout.setAdapter(new CreateAuctionStepperAdapter(getSupportFragmentManager(), getApplicationContext()));
    }
}