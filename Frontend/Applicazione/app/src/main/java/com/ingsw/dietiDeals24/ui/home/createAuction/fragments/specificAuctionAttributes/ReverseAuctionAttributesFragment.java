package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ingsw.dietiDeals24.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.NumberFormat;
import java.util.Calendar;

public class ReverseAuctionAttributesFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private EditText priceEditText;
    private TextView dateTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reverse_auction_attributes, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupPriceEditText(view);
        setupDatePicker(view);
    }

    private void setupDatePicker(View view) {
        dateTextView = view.findViewById(R.id.date_edit_text_reverse_auction_attributes);
        dateTextView.setFocusable(false);
        dateTextView.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                    ReverseAuctionAttributesFragment.this,
                    2021,
                    0,
                    1
            );

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            datePickerDialog.setMinDate(calendar);
            datePickerDialog.show(getParentFragmentManager(), "datePicker");
        });
    }


    private void setupPriceEditText(@NonNull View view) {
        priceEditText = view.findViewById(R.id.initial_price_layout_reverse_auction_attributes);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        StringBuilder sb = new StringBuilder();
        sb.append(dayOfMonth);
        sb.append("/");
        sb.append(monthOfYear + 1);
        sb.append("/");
        sb.append(year);
        dateTextView.setText(sb.toString());
    }
}