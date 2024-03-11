package com.ingsw.dietiDeals24.ui.home.searchAuctions.makeBid;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MakeBidController;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.ui.utility.DecimalInputFilter;
import com.ingsw.dietiDeals24.ui.utility.KeyboardFocusManager;
import com.ingsw.dietiDeals24.ui.utility.NumberFormatter;
import com.ingsw.dietiDeals24.ui.utility.PopupGeneratorOf;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;

import java.util.concurrent.ExecutionException;

public class MakeReverseBidActivity extends AppCompatActivity {
    private TextView currentBidTextView;
    private EditText bidEditText;
    private CircularProgressButton sendBidButton;
    private ReverseBid currentBid = null;
    private KeyboardFocusManager keyboardFocusManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reverse_bid);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            currentBid = MakeBidController.getCurrentReverseBid().get();
        } catch (ExecutionException e) {
            ToastManager.showToast(getApplicationContext(), e.getCause().getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        setupCurrentBidEditText();
        setupBidEditText();
        setupActionBar();
        setupBidButton();
//        setupKeyboardFocusManager();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupBidEditText() {
        bidEditText = findViewById(R.id.bid_edit_text_make_reverse_bid);
        bidEditText.setFilters(new DecimalInputFilter[]{new DecimalInputFilter()});
        bidEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (bidEditText.getText() == null || bidEditText.getText().toString().isEmpty()) {
                    return;
                }
                double bid = Double.parseDouble(bidEditText.getText().toString().replace("€", ""));
                if (currentBid != null && bid < currentBid.getMoneyAmount()) {
                    sendBidButton.setEnabled(true);
                } else if (bid < MakeBidController.getReverseAuction().getStartingPrice()) {
                    sendBidButton.setEnabled(true);
                } else {
                    bidEditText.setError("Devi fare un offerta minore di quella attuale");
                }
            }
        });
    }

    private void setupBidButton() {
        sendBidButton = findViewById(R.id.send_button_make_reverse_bid);
        sendBidButton.setEnabled(false);
        sendBidButton.setOnClickListener(v -> {
            sendBidButton.startAnimation();
            if (bidEditText.getError() != null) {
                sendBidButton.revertAnimation();
                ToastManager.showToast(this, "Devi fare un offerta minore di quella attuale");
                return;
            }
            new Thread(() -> {
                try {
                    MakeBidController.makeReverseBid(Double.parseDouble(bidEditText.getText().toString())).get();
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

    private void setupActionBar() {
        setSupportActionBar(findViewById(R.id.toolbar_make_reverse_bid));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupKeyboardFocusManager() {
        keyboardFocusManager = new KeyboardFocusManager(this, findViewById(R.id.make_reverse_bid_layout));
        keyboardFocusManager.closeKeyboardWhenUserClickOutside();
        keyboardFocusManager.loseFocusWhenKeyboardClose();
    }

    private void setupCurrentBidEditText() {
        currentBidTextView = findViewById(R.id.current_bid_text_view_item_my_reverse_auction);
        if (currentBid == null) {
            currentBidTextView.setText("Offerta attuale: " + NumberFormatter.formatPrice(MakeBidController.getReverseAuction().getStartingPrice()));
        } else {
            currentBidTextView.setText("Offerta attuale: " + NumberFormatter.formatPrice(currentBid.getMoneyAmount()));
        }
    }
}
