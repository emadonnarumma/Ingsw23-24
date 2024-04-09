package com.ingsw.dietiDeals24.ui.home.searchAuctions.makePayment.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.CheckConnectionActivity;
import com.ingsw.dietiDeals24.ui.home.searchAuctions.makePayment.fragments.SelectPaymentMethodFragment;

public class MakePaymentActivity extends CheckConnectionActivity {
    private Toolbar toolbar;

    private final OnBackPressedCallback callback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_make_payment);

            if (currentFragment instanceof SelectPaymentMethodFragment) {
                finish();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_make_payment, new SelectPaymentMethodFragment())
                .commit();

        setActionBar();
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setActionBar() {
        toolbar = findViewById(R.id.toolbar_make_payment);
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
}
