package com.ingsw.dietiDeals24.ui.home;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes.GeneralAuctionAttributesFragment;
import com.ingsw.dietiDeals24.ui.home.myAuctions.MyAuctionFragment;
import com.ingsw.dietiDeals24.ui.home.profile.ProfileFragment;
import com.ingsw.dietiDeals24.ui.home.search.SearchFragment;

public class HomeActivity extends AppCompatActivity {
    private NavigationBarView navigationBarView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationBarView = findViewById(R.id.bottom_navigation);
        setNavigatioBarView();
    }




    private void setNavigatioBarView() {
        navigationBarView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_search){

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                        new SearchFragment()).commit();
                return true;

            } else if (item.getItemId() == R.id.navigation_my_auctions) {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                        new MyAuctionFragment()).commit();
                return true;

            } else if (item.getItemId() == R.id.navigation_create_auction) {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                        new GeneralAuctionAttributesFragment()).commit();
                return true;

            } else if (item.getItemId() == R.id.navigation_my_bids) {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                        new MyAuctionFragment()).commit();
                return true;

            } else if (item.getItemId() == R.id.navigation_profile) {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                        new ProfileFragment()).commit();
                return true;

            } else {

                return false;
            }
        });
    }

}