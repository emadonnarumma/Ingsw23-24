package com.ingsw.dietiDeals24.ui.home.myAuctions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ingsw.dietiDeals24.R;

public abstract class AuctionDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_details);
    }
}