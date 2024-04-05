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
import com.ingsw.dietiDeals24.controller.formstate.SilentBidFormState;
import com.ingsw.dietiDeals24.utility.CheckConnectionActivity;
import com.ingsw.dietiDeals24.utility.DecimalInputFilter;
import com.ingsw.dietiDeals24.utility.PopupGeneratorOf;
import com.ingsw.dietiDeals24.utility.ToastManager;

import java.util.concurrent.ExecutionException;

public class MakeSilentBidActivity extends CheckConnectionActivity {
    private TextInputLayout bidTextInputLayout;
    private TextView bidErrorTextView;
    private EditText bidEditText;
    private CircularProgressButton sendBidButton;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            MakeBidController.silentBidInputChanged(getApplicationContext(), s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_silent_bid);
    }

    private void observeSilentBidFormState() {
        MakeBidController.getSilentBidFormState().observe(this, silentBidFormState -> {
            if (silentBidFormState == null) {
                return;
            }
            sendBidButton.setEnabled(silentBidFormState.isDataValid());
            if (silentBidFormState.getBidError() != null) {
                showErrorAndChangeColor(
                        silentBidFormState,
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

    private void showErrorAndChangeColor(SilentBidFormState formState, EditText editText, TextView errorTextView, TextInputLayout layout) {
        errorTextView.setText(formState.getBidError());
        errorTextView.setVisibility(View.VISIBLE);
        editText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        layout.setBoxStrokeColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupBidEditText();
        setupBidButton();
        setupActionBar();
        bidErrorTextView = findViewById(R.id.bid_error_text_view_make_silent_bid);
        bidTextInputLayout = findViewById(R.id.bid_layout_make_silent_bid);
        observeSilentBidFormState();
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
        bidEditText = findViewById(R.id.bid_edit_text_make_silent_bid);
        bidEditText.setFilters(new DecimalInputFilter[]{new DecimalInputFilter()});
        bidEditText.addTextChangedListener(textWatcher);
    }

    private void setupActionBar() {
        setSupportActionBar(findViewById(R.id.toolbar_make_silent_bid));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupBidButton() {
        sendBidButton = findViewById(R.id.send_button_make_silent_bid);
        sendBidButton.setEnabled(false);
        sendBidButton.setOnClickListener(v -> {
            makeSilentBid();
        });
    }

    private void makeSilentBid() {
        sendBidButton.startAnimation();
        new Thread(() -> {
            makeSilentBidThread();
        }).start();
    }

    private void makeSilentBidThread() {
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
    }
}
