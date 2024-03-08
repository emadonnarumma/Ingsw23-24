package com.ingsw.dietiDeals24.ui.home.searchAuctions.makeBid;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MakeBidController;
import com.ingsw.dietiDeals24.controller.MyAuctionDetailsController;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;

import java.util.concurrent.ExecutionException;

public class MakeReverseBidActivity extends AppCompatActivity {
    private TextView currentBidEditText;
    private EditText bidEditText;
    private CircularProgressButton sendBidButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reverse_bid);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupCurrentBidEditText();
        bidEditText = findViewById(R.id.bid_edit_text_make_reverse_bid);
        sendBidButton = findViewById(R.id.send_button_make_reverse_bid);

    }

    private void setupCurrentBidEditText() {
        ReverseBid currentBid = null;
        try {
            currentBid = MakeBidController.getCurrentReverseBid().get();
        } catch (ExecutionException e) {
            ToastManager.showToast(getApplicationContext(), e.getCause().getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        currentBidEditText = findViewById(R.id.current_bid_text_view_item_my_reverse_auction);
        currentBidEditText.setText("Offerta attuale: " + currentBid.getMoneyAmount());
    }
}
