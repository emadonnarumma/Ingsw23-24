package com.ingsw.dietiDeals24.ui.home.searchAuctions;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MakeBidController;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;

import java.util.concurrent.ExecutionException;

public class MakeSilentBidActivity extends AppCompatActivity {
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
        sendBidButton = findViewById(R.id.send_button_make_silent_bid);
        sendBidButton.setOnClickListener(v -> {
            sendBidButton.startAnimation();

            new Thread(() -> {
                try {
                    MakeBidController.makeSilentBid(Double.parseDouble(bidEditText.getText().toString())).get();
                    runOnUiThread(() -> {
                        new AlertDialog.Builder(this)
                                .setTitle("Offerta inviata")
                                .setPositiveButton("OK", null)
                                .show();
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

            sendBidButton.revertAnimation();
        });
    }


}
