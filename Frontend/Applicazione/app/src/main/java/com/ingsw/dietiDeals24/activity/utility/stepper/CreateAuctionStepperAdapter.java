package com.ingsw.dietiDeals24.activity.utility.stepper;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.ingsw.dietiDeals24.activity.ui.home.createAuction.AuctionGeneralAttributesFragment;
import com.ingsw.dietiDeals24.activity.ui.home.createAuction.AuctionTypeFragment;
import com.ingsw.dietiDeals24.activity.ui.registration.MandatoryRegistrationInfoFragment;
import com.ingsw.dietiDeals24.activity.ui.registration.OptionalInfoRegistrationFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

public class CreateAuctionStepperAdapter extends AbstractFragmentStepAdapter {


    private static final String CURRENT_STEP_POSITION_KEY = "0";

    public CreateAuctionStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        Bundle b = new Bundle();

        switch (position) {
            case 0:
                AuctionGeneralAttributesFragment auctionGeneralAttributesFragment =
                        new AuctionGeneralAttributesFragment();

                b.putInt(CURRENT_STEP_POSITION_KEY, position);
                auctionGeneralAttributesFragment.setArguments(b);

                return auctionGeneralAttributesFragment;

            case 1:
                AuctionTypeFragment auctionTypeFragment = new AuctionTypeFragment();
                b.putInt(CURRENT_STEP_POSITION_KEY, position);
                auctionTypeFragment.setArguments(b);

                return auctionTypeFragment;

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
                        .setTitle("Attributi generali")
                        .setEndButtonLabel("Tipologia");
                break;

            case 1:
            builder
                    .setTitle("Tipologia")
                    .setBackButtonLabel("Attributi generali");
            break;

            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
        return builder.create();
    }

    @Override
    public int getCount() {
        return 1;
    }
}
