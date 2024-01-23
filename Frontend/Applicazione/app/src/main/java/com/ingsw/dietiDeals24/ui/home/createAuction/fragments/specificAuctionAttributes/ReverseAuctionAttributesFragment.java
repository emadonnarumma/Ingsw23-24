package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.utility.DecimalInputFilter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

public class ReverseAuctionAttributesFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private EditText priceEditText, dateEditText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reverse_auction_attributes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupPriceEditText(view);

        dateEditText = view.findViewById(R.id.date_edit_text_reverse_auction_attributes);
        dateEditText.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                    ReverseAuctionAttributesFragment.this,
                    2021,
                    0,
                    1
            );
            datePickerDialog.show(getParentFragmentManager(), "datePicker");
        });
    }

    private void setupPriceEditText(@NonNull View view) {
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }
}