package com.ingsw.dietiDeals24.ui.home.searchAuctions.makePayment.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MakeBidController;
import com.ingsw.dietiDeals24.ui.utility.ExpirationDateTextWatcher;
import com.ingsw.dietiDeals24.ui.utility.FourDigitCardFormatWatcher;

public class CreditCardAttributesFragment extends FragmentOfMakePaymentActivity {
    private EditText cardNumberEditText, cardOwnerNameEditText,
            cardOwnerSurnameEditText, cvvEditText, expirationDateEditText;
    private CircularProgressButton goToSummaryButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackButtonEnabled(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_credit_card_attributes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardNumberEditText = view.findViewById(R.id.card_number_edit_text_card_attributes);
        cardNumberEditText.addTextChangedListener(new FourDigitCardFormatWatcher(cardNumberEditText));
        cardOwnerNameEditText = view.findViewById(R.id.card_owner_name_edit_text_card_attributes);
        cardOwnerSurnameEditText = view.findViewById(R.id.card_owner_surname_edit_text_card_attributes);
        cvvEditText = view.findViewById(R.id.cvv_edit_text_credit_card_attributes);
        setupGoToSummaryButton(view);
        setupExpirationDateEditText(view);
    }

    private void setupGoToSummaryButton(@NonNull View view) {
        goToSummaryButton = view.findViewById(R.id.buy_button_fragment_summary);
        goToSummaryButton.setOnClickListener(v -> {

            String ownerName = cardOwnerNameEditText.getText().toString();
            String ownerSurname = cardOwnerSurnameEditText.getText().toString();
            String cardNumber = cardNumberEditText.getText().toString();
            String cvv = cvvEditText.getText().toString();
            String expirationDate = expirationDateEditText.getText().toString();
            MakeBidController.setCreditCard(cardNumber, ownerName, ownerSurname, cvv, expirationDate);

            getParentFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container_make_payment, new SummaryFragment())
                    .commit();
        });
    }

    private void setupExpirationDateEditText(@NonNull View view) {
        expirationDateEditText = view.findViewById(R.id.expiration_date_edit_text_card_attributes);
        expirationDateEditText.addTextChangedListener(new ExpirationDateTextWatcher(expirationDateEditText));
    }
}
