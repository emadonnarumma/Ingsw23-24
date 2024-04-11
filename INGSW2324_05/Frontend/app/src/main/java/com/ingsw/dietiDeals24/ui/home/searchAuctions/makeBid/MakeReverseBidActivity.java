package com.ingsw.dietiDeals24.ui.home.searchAuctions.makeBid;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MakeBidController;
import com.ingsw.dietiDeals24.controller.formstate.ReverseBidFormState;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.ui.CheckConnectionActivity;
import com.ingsw.dietiDeals24.utility.DecimalInputFilter;
import com.ingsw.dietiDeals24.utility.NumberFormatter;
import com.ingsw.dietiDeals24.utility.PopupGenerator;
import com.ingsw.dietiDeals24.utility.ToastManager;

import java.util.concurrent.ExecutionException;

public class MakeReverseBidActivity extends CheckConnectionActivity {
    private TextView currentBidTextView, bidErrorTextView;
    private TextInputLayout bidTextInputLayout;
    private EditText bidEditText;
    private CircularProgressButton sendBidButton;
    private ReverseBid currentBid = null;
    private double moneyAmount;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            MakeBidController.reverseBidInputChanged(MakeReverseBidActivity.this, s.toString(), moneyAmount);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reverse_bid);
        try {
            currentBid = MakeBidController.getCurrentReverseBid().get();
            if (currentBid == null) {
                moneyAmount = MakeBidController.getReverseAuction().getStartingPrice();
            } else {
                moneyAmount = currentBid.getMoneyAmount();
            }
        } catch (ExecutionException e) {
            ToastManager.showToast(getApplicationContext(), e.getCause().getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupTextInputLayout();
        setupEditTexts();
        setupTextViews();
        setupActionBar();
        setupBidButton();
        observeReverseBidFormState();
    }


    private void observeReverseBidFormState() {
        MakeBidController.getReverseBidFormState().observe(this, reverseBidFormState -> {
            if (reverseBidFormState == null) {
                return;
            }
            sendBidButton.setEnabled(reverseBidFormState.isDataValid());
            if (reverseBidFormState.getBidError() != null) {
                showErrorAndChangeColor(
                        reverseBidFormState,
                        bidEditText,
                        bidErrorTextView,
                        bidTextInputLayout
                );
            } else {
                hideErrorAndChangeColor(
                        bidEditText,
                        bidErrorTextView,
                        bidTextInputLayout
                );
            }
        });
    }

    private void hideErrorAndChangeColor(EditText editText, TextView errorTextView, TextInputLayout textInputLayout) {
        errorTextView.setVisibility(View.GONE);
        editText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        textInputLayout.setBoxStrokeColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
    }

    private void showErrorAndChangeColor(ReverseBidFormState reverseBidFormState, EditText editText, TextView errorTextView, TextInputLayout textInputLayout) {
        errorTextView.setText(reverseBidFormState.getBidError());
        errorTextView.setVisibility(View.VISIBLE);
        editText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        textInputLayout.setBoxStrokeColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
    }

    private void setupTextViews() {
        bidErrorTextView = findViewById(R.id.bid_error_text_view_make_reverse_bid);
    }

    private void setupEditTexts() {
        setupCurrentBidEditText();
        setupBidEditText();
    }

    private void setupTextInputLayout() {
        bidTextInputLayout = findViewById(R.id.bid_layout_make_reverse_bid);
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
        bidEditText.addTextChangedListener(textWatcher);
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
                    double amount = Double.parseDouble(bidEditText.getText().toString());
                    MakeBidController.makeReverseBid(amount).get();
                    runOnUiThread(() -> {
                        sendBidButton.revertAnimation();
                        PopupGenerator.bidSendedSuccessfullyPopup(this);
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

    private void setupCurrentBidEditText() {
        currentBidTextView = findViewById(R.id.current_bid_text_view_item_my_reverse_auction);
        if (currentBid == null) {
            currentBidTextView.setText("Offerta attuale: " + NumberFormatter.formatPrice(MakeBidController.getReverseAuction().getStartingPrice()));
        } else {
            currentBidTextView.setText("Offerta attuale: " + NumberFormatter.formatPrice(currentBid.getMoneyAmount()));
        }
    }
}
