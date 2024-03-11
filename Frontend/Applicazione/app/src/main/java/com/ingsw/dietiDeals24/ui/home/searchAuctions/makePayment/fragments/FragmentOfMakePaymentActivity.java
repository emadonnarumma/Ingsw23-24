package com.ingsw.dietiDeals24.ui.home.searchAuctions.makePayment.fragments;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public abstract class FragmentOfMakePaymentActivity extends Fragment {
    public void setBackButtonEnabled(boolean enabled) {
        if (getActivity() instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(enabled);
            }
        }
    }
}