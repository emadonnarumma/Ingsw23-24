package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.utility.DecimalInputFilter;

public class ReverseAuctionAttributesFragment extends Fragment {
    private EditText priceEditText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reverse_auction_attributes, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        priceEditText = view.findViewById(R.id.price_edit_text_reverse_auction_attributes);
        priceEditText.setFilters(new InputFilter[] { new DecimalInputFilter() });
        priceEditText.setOnFocusChangeListener((v, hasFocus) -> {
            String price = priceEditText.getText().toString();
            if (!hasFocus && price.isEmpty()) {
                StringBuilder sb = new StringBuilder(price);
                sb.append("€");
                priceEditText.setText(sb.toString());
            }
        });

    }
}