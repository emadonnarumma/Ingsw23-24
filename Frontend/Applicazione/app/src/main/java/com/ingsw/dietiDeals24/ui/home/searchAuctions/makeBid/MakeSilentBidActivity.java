package com.ingsw.dietiDeals24.ui.home.searchAuctions.makeBid;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MakeBidController;
import com.ingsw.dietiDeals24.ui.utility.OnNavigateToHomeActivityFragmentListener;
import com.ingsw.dietiDeals24.ui.utility.PopupGeneratorOf;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;

import java.util.concurrent.ExecutionException;

public class MakeSilentBidActivity extends AppCompatActivity implements OnNavigateToHomeActivityFragmentListener {
    private EditText bidEditText;
    private CircularProgressButton sendBidButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_silent_bid);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bidEditText = findViewById(R.id.bid_edit_text_make_silent_bid);
        setupBidButton();
        setupActionBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar() {
        setSupportActionBar(findViewById(R.id.toolbar_make_silent_bid));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupBidButton() {
        sendBidButton = findViewById(R.id.send_button_make_silent_bid);
        sendBidButton.setOnClickListener(v -> {
            sendBidButton.startAnimation();

            new Thread(() -> {
                try {
                    MakeBidController.makeSilentBid(Double.parseDouble(bidEditText.getText().toString())).get();
                    runOnUiThread(() -> {
                        sendBidButton.revertAnimation();
                        PopupGeneratorOf.bidSendedSuccessfullyPopup(this);
                    });

                } catch (ExecutionException e) {
                    runOnUiThread(() -> {
                        sendBidButton.revertAnimation();
                        ToastManager.showToast(this, e.getCause().getMessage());
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        });
    }
}
