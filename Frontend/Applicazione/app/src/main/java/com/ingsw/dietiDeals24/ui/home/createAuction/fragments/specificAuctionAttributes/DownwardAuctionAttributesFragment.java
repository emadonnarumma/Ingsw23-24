package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.CreateAuctionController;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes.GeneralAuctionAttributesViewModel;
import com.ingsw.dietiDeals24.ui.utility.DecimalInputFilter;
import com.ingsw.dietiDeals24.ui.utility.KeyboardFocusManager;
import com.ingsw.dietiDeals24.ui.utility.auctionHolder.AuctionHolder;

public class DownwardAuctionAttributesFragment extends Fragment {

    private EditText initialPriceEditText, minimumPriceEditText, decrementAmountEditText;

    private Bundle bundle;

    private KeyboardFocusManager keyboardFocusManager;

    private GeneralAuctionAttributesViewModel viewModel;

    private AuctionHolder genericAuctionAttributesHolder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(GeneralAuctionAttributesViewModel.class);
        genericAuctionAttributesHolder = viewModel.getNewAuction().getValue();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_downaward_auction_attributes, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupMinPriceEditText(view);
        setupDecrementAmountEditText(view);
        setupInitialPriceEditText(view);
        setupKeyboardFocusManager(view);
    }

    private void setupMinPriceEditText(@NonNull View view) {
        minimumPriceEditText = view.findViewById(R.id.secret_minimum_price_edit_text_downward_auction_attributes);
        decrementAmountEditText.setFilters(new DecimalInputFilter[]{new DecimalInputFilter()});
        decrementAmountEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String price = decrementAmountEditText.getText().toString();
                setEuroToEndIfNotPresent(price, decrementAmountEditText);
            }
        });
    }

    private void setupDecrementAmountEditText(@NonNull View view) {
        decrementAmountEditText = view.findViewById(R.id.decrement_amount_edit_text_downward_auction_attributes);
        decrementAmountEditText.setFilters(new DecimalInputFilter[]{new DecimalInputFilter()});
        decrementAmountEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String price = decrementAmountEditText.getText().toString();
                if (CreateAuctionController.isValidDecrementAmount(Double.parseDouble(deleteMoneySimbol(initialPriceEditText.getText().toString())), Double.parseDouble(deleteMoneySimbol(price)))) {
                    setEuroToEndIfNotPresent(price, decrementAmountEditText);
                } else {
                    decrementAmountEditText.setError("invalid decrement amount");
                    setEuroToEndIfNotPresent(price, decrementAmountEditText);
                }
                }
        });
    }

    private void setupInitialPriceEditText(@NonNull View view) {
        initialPriceEditText = view.findViewById(R.id.initial_price_edit_text_downward_auction_attributes);
        initialPriceEditText.setFilters(new DecimalInputFilter[]{new DecimalInputFilter()});
        initialPriceEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String price = initialPriceEditText.getText().toString();
                setEuroToEndIfNotPresent(price, initialPriceEditText);
                if (!price.equals("") && decrementAmountEditText.getText() != null) {
                    decrementAmountEditText.setEnabled(true);
                } else {
                    decrementAmountEditText.setText("");
                    decrementAmountEditText.setEnabled(false);
                }
            }
        });
        decrementAmountEditText.setEnabled(false);
    }

    private void setEuroToEndIfNotPresent(String price, EditText decrementAmountEditText) {
        if (!price.equals("") && !price.endsWith("€")) {
            price = price + "€";
            decrementAmountEditText.setText(price);
        }
    }


    private void setupKeyboardFocusManager(View view) {
        keyboardFocusManager = new KeyboardFocusManager(this, view);
        keyboardFocusManager.closeKeyboardWhenUserClickOutside();
        keyboardFocusManager.loseFocusWhenKeyboardClose();
    }

    private String deleteMoneySimbol(String string) {
        if (string.endsWith("€"))
            return string.substring(0, string.length() - 1);

        return string;
    }
}