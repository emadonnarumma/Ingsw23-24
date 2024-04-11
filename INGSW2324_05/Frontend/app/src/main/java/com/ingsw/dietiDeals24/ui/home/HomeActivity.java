package com.ingsw.dietiDeals24.ui.home;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationBarView;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.NotificationController;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.ui.CheckConnectionActivity;
import com.ingsw.dietiDeals24.ui.home.createAuction.generalAuctionAttributes.GeneralAuctionAttributesFragment;
import com.ingsw.dietiDeals24.ui.home.createAuction.specificAuctionAttributes.DownwardAuctionAttributesFragment;
import com.ingsw.dietiDeals24.ui.home.createAuction.specificAuctionAttributes.ReverseAuctionAttributesFragment;
import com.ingsw.dietiDeals24.ui.home.createAuction.specificAuctionAttributes.SilentAuctionAttributesFragment;
import com.ingsw.dietiDeals24.ui.home.createAuction.userTypeAuctionAttributes.BuyerAuctionTypesFragment;
import com.ingsw.dietiDeals24.ui.home.createAuction.userTypeAuctionAttributes.SellerAuctionTypesFragment;
import com.ingsw.dietiDeals24.ui.home.myAuctions.MyAuctionsFragment;
import com.ingsw.dietiDeals24.ui.home.myBids.MyBidsFragment;
import com.ingsw.dietiDeals24.ui.home.profile.my.AddBankAccountFragment;
import com.ingsw.dietiDeals24.ui.home.profile.my.AddExternalLinkFragment;
import com.ingsw.dietiDeals24.ui.home.profile.my.EditBankAccountFragment;
import com.ingsw.dietiDeals24.ui.home.profile.my.EditBioFragment;
import com.ingsw.dietiDeals24.ui.home.profile.my.EditExternalLinkFragment;
import com.ingsw.dietiDeals24.ui.home.profile.my.EditExternalLinksFragment;
import com.ingsw.dietiDeals24.ui.home.profile.my.EditProfileFragment;
import com.ingsw.dietiDeals24.ui.home.profile.my.EditRegionFragment;
import com.ingsw.dietiDeals24.ui.home.profile.my.ProfileFragment;
import com.ingsw.dietiDeals24.ui.home.searchAuctions.SearchAuctionsFragment;
import com.ingsw.dietiDeals24.ui.recyclerViews.notifications.NotificationAdapter;
import com.ingsw.dietiDeals24.utility.PopupGenerator;
import com.ingsw.dietiDeals24.utility.ToastManager;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeActivity extends CheckConnectionActivity {
    private TextView notificationTextViewNumber;
    private DrawerLayout drawerLayout;
    private ImageButton notificationButton, closeDrawerButton;
    private ImageView notificationCountBackgroundImageView;
    private RecyclerView notificationsRecyclerView;
    private NavigationBarView navigationBarView;
    private Toolbar toolbar;
    private ExecutorService executorService;
    private volatile boolean isConnected = true;

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
            } else if (currentFragment instanceof AddExternalLinkFragment) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_home, new EditExternalLinksFragment())
                        .commit();
            } else if (currentFragment instanceof AddBankAccountFragment) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_home, new ProfileFragment())
                        .commit();
            } else if (currentFragment instanceof EditExternalLinkFragment) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_home, new EditExternalLinksFragment())
                        .commit();
            } else {
                callback.setEnabled(false);
                tryToDisconnect();
                callback.setEnabled(true);
            }
        }

        private void tryToDisconnect() {
            PopupGenerator.areYouSureToLogoutPopup(HomeActivity.this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupNotificationButton();
        setupNotificationNumberTextView();
        setupNotificationNumberBackgroundImageView();
        startNotificationUpdates();
        setupNavigationBarView();
        setupActionBar();
        setupDrawerLayout();
        getOnBackPressedDispatcher().addCallback(this, callback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new SearchAuctionsFragment()).commit();

        checkRequestToOpenAFragmentFromAnOtherActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    private void setupNotificationNumberTextView() {
        notificationTextViewNumber = findViewById(R.id.notification_count_home);
    }

    private void setupNotificationNumberBackgroundImageView() {
        notificationCountBackgroundImageView = findViewById(R.id.notification_count_background_home);
    }

    private void startNotificationUpdates() {
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            while (!Thread.currentThread().isInterrupted() && isConnected) {
                try {
                    int numberOfNotifications = NotificationController.updateNotifications().get();
                    if (numberOfNotifications == 0) {
                        hideNotificationBadge();
                    } else {
                        showNotificationBadge(numberOfNotifications);
                    }
                } catch (ExecutionException e) {
                    if (e.getCause() instanceof AuthenticationException) {
                        runOnUiThread(() -> ToastManager.showToast(getApplicationContext(), "Sessione scaduta, effettua nuovamente il login"));
                    } else if (e.getCause() instanceof ConnectionException) {
                        runOnUiThread(() -> ToastManager.showToast(getApplicationContext(), "Errore di connessione"));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        this.isConnected = isConnected;
        if (!isConnected) {
            executorService.shutdownNow();
            PopupGenerator.connectionLostPopup(this);
        } else {
            startNotificationUpdates();
        }
    }

    private void showNotificationBadge(int numberOfNotifications) {
        runOnUiThread(() -> {
            notificationTextViewNumber.setVisibility(View.VISIBLE);
            notificationCountBackgroundImageView.setVisibility(View.VISIBLE);
            notificationTextViewNumber.setText(String.valueOf(numberOfNotifications));
        });
    }

    private void hideNotificationBadge() {
        runOnUiThread(() -> {
            notificationTextViewNumber.setVisibility(View.GONE);
            notificationCountBackgroundImageView.setVisibility(View.GONE);
        });
    }

    private void checkRequestToOpenAFragmentFromAnOtherActivity() {
        String fragmentToOpen = getIntent().getStringExtra("openFragment");
        if ("MyAuctionFragment".equals(fragmentToOpen)) {
            navigationBarView.setSelectedItemId(R.id.navigation_my_auctions);

        } else if ("MyBidsFragment".equals(fragmentToOpen)) {
            navigationBarView.setSelectedItemId(R.id.navigation_my_bids);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_home, new MyBidsFragment())
                    .commit();

        } else if ("SilentAuctionAttributesFragment".equals(fragmentToOpen)) {
            navigationBarView.setSelectedItemId(R.id.navigation_create_auction);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_home, new SilentAuctionAttributesFragment())
                    .commit();

        } else if ("DownwardAuctionAttributesFragment".equals(fragmentToOpen)) {
            navigationBarView.setSelectedItemId(R.id.navigation_create_auction);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_home, new DownwardAuctionAttributesFragment())
                    .commit();

        } else if ("ReverseAuctionAttributesFragment".equals(fragmentToOpen)) {
            navigationBarView.setSelectedItemId(R.id.navigation_create_auction);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_home, new ReverseAuctionAttributesFragment())
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            callback.handleOnBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar() {
        toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
    }

    private void setupDrawerLayout() {
        drawerLayout = findViewById(R.id.drawer_layout_home);
        setupCloseDrawerButton();
    }

    private void setupCloseDrawerButton() {
        closeDrawerButton = findViewById(R.id.close_button_drawer_content);
        closeDrawerButton.setOnClickListener(v -> drawerLayout.closeDrawer(GravityCompat.END));
    }

    private void setupNotificationButton() {
        notificationButton = findViewById(R.id.notification_button);
        notificationButton.setOnClickListener(v -> {
            setupNotificationRecyclerView();
            drawerLayout.openDrawer(GravityCompat.END);
        });
    }

    private void setupNotificationRecyclerView() {
        notificationsRecyclerView = findViewById(R.id.notification_recycler_view);
        NotificationAdapter notificationAdapter = new NotificationAdapter(NotificationController.getNotifications());
        notificationsRecyclerView.setAdapter(notificationAdapter);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupNavigationBarView() {
        navigationBarView = findViewById(R.id.bottom_navigation_home);
        navigationBarView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_search) {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                        new SearchAuctionsFragment()).commit();
                return true;

            } else if (item.getItemId() == R.id.navigation_my_auctions) {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                        new MyAuctionsFragment()).commit();
                return true;

            } else if (item.getItemId() == R.id.navigation_create_auction) {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                        new GeneralAuctionAttributesFragment()).commit();
                return true;

            } else if (item.getItemId() == R.id.navigation_my_bids) {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                        new MyBidsFragment()).commit();
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