package com.ingsw.dietiDeals24.ui.home;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes.GeneralAuctionAttributesFragment;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes.DownwardAuctionAttributesFragment;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes.ReverseAuctionAttributesFragment;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes.SilentAuctionAttributesFragment;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.userTypeAuctionAttributes.BuyerAuctionTypesFragment;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.userTypeAuctionAttributes.SellerAuctionTypesFragment;
import com.ingsw.dietiDeals24.ui.home.myAuctions.MyAuctionFragment;
import com.ingsw.dietiDeals24.ui.home.profile.EditBankAccountFragment;
import com.ingsw.dietiDeals24.ui.home.profile.EditBioFragment;
import com.ingsw.dietiDeals24.ui.home.profile.EditExternalLinksFragment;
import com.ingsw.dietiDeals24.ui.home.profile.EditProfileFragment;
import com.ingsw.dietiDeals24.ui.home.profile.EditRegionFragment;
import com.ingsw.dietiDeals24.ui.home.profile.ProfileFragment;
import com.ingsw.dietiDeals24.ui.home.search.SearchFragment;

public class HomeActivity extends AppCompatActivity {
    private NavigationBarView navigationBarView;
    private Toolbar toolbar;
    private final OnBackPressedCallback callback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_home);

            if (currentFragment instanceof BuyerAuctionTypesFragment
                    || currentFragment instanceof SellerAuctionTypesFragment) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_home, new GeneralAuctionAttributesFragment())
                        .commit();

            } else if (currentFragment instanceof ReverseAuctionAttributesFragment) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_home, new BuyerAuctionTypesFragment())
                        .commit();

            } else if (currentFragment instanceof SilentAuctionAttributesFragment
                    || currentFragment instanceof DownwardAuctionAttributesFragment) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_home, new SellerAuctionTypesFragment())
                        .commit();

            } else if (currentFragment instanceof EditProfileFragment) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_home, new ProfileFragment())
                        .commit();
            } else if (currentFragment instanceof EditRegionFragment) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_home, new EditProfileFragment())
                        .commit();
            } else if (currentFragment instanceof EditBioFragment) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_home, new EditProfileFragment())
                        .commit();
            } else if (currentFragment instanceof EditExternalLinksFragment) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_home, new EditProfileFragment())
                        .commit();
            } else if (currentFragment instanceof EditBankAccountFragment) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_home, new EditProfileFragment())
                        .commit();
            }
            else {
                callback.setEnabled(false);
                onBackPressed();
                callback.setEnabled(true);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setActionBar();
        getOnBackPressedDispatcher().addCallback(this, callback);
        setNavigatioBarView(findViewById(R.id.bottom_navigation_home));
    }

    private void setActionBar() {
        toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            callback.handleOnBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNavigatioBarView(NavigationBarView navigationBarView) {
        this.navigationBarView = navigationBarView;
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

    public NavigationBarView getNavigationBarView() {
        return navigationBarView;
    }
}