package com.ingsw.dietiDeals24.ui.home.searchAuctions.makePayment.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.utility.CreditCardTextWatcher;

public class CreditCardAttributesFragment extends FragmentOfMakePaymentActivity {
    private EditText cardNumberEditText, cardOwnerNameEditText, cardOwnerSurnameEditText, cvvEditText;
    private TextView expirationDateTextView;
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
        cardNumberEditText.addTextChangedListener(new CreditCardTextWatcher());
        cardOwnerNameEditText = view.findViewById(R.id.card_owner_name_edit_text_card_attributes);
        cardOwnerSurnameEditText = view.findViewById(R.id.card_owner_surname_edit_text_card_attributes);
        cvvEditText = view.findViewById(R.id.cvv_edit_text_credit_card_attributes);
        expirationDateTextView = view.findViewById(R.id.expiration_date_text_view_credit_card_attributes);
        goToSummaryButton = view.findViewById(R.id.go_to_summary_button_credit_card_attributes);
    }
}
