package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.CreateAuctionController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.enumeration.Category;
import com.ingsw.dietiDeals24.enumeration.Wear;
import com.ingsw.dietiDeals24.model.Image;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.ui.utility.DecimalInputFilter;
import com.ingsw.dietiDeals24.ui.utility.KeyboardFocusManager;
import com.ingsw.dietiDeals24.ui.utility.auctionHolder.ImageAuctionBinder;
import com.ingsw.dietiDeals24.ui.utility.auctionHolder.ImageConverter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class ReverseAuctionAttributesFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private Bundle bundle;
    private KeyboardFocusManager keyboardFocusManager;

    private EditText priceEditText;
    private TextView dateTextView;

    private Button createAuctionButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

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
        setupKeyboardFocusManager(view);
        setupCreateAuctionButton(view);
    }

    private void setupCreateAuctionButton(View view) {
        createAuctionButton = view.findViewById(
                R.id.create_reverse_auction_button_reverse_auction_attributes
        );

        createAuctionButton.setOnClickListener(v -> {

            String title = bundle.getString("title");
            String description = bundle.getString("description");
            Category category = (Category) bundle.getSerializable("category");
            Wear wear = (Wear) bundle.getSerializable("wear");
            List<Uri> uriImages = bundle.getParcelableArrayList("images");
            String initialPrice = deleteMoneySimbol(priceEditText.getText().toString());
            String expirationDate = dateTextView.getText().toString();

            List<Image> images;
            try {
                images = ImageConverter.convertUriListToImageList(getContext(), uriImages);
            } catch (IOException e) {
                throw new RuntimeException("Error while converting images to byte array");
            }

            ReverseAuction newReverseAuction;
            newReverseAuction = new ReverseAuction(
                    UserHolder.user,
                    title,
                    description,
                    wear,
                    category,
                    AuctionStatus.IN_PROGRESS,
                    Double.parseDouble(initialPrice),
                    expirationDate
            );

            CreateAuctionController.createAuction(newReverseAuction, images);
        });
    }

    private String deleteMoneySimbol(String string) {
        return string.substring(0, string.length() - 1);
    }

    private void setupKeyboardFocusManager(View view) {
        keyboardFocusManager = new KeyboardFocusManager(this, view);
        keyboardFocusManager.closeKeyboardWhenUserClickOutside();
        keyboardFocusManager.loseFocusWhenKeyboardClose();
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

            setTheMinumumToday(datePickerDialog);
        });
    }

    private void setTheMinumumToday(DatePickerDialog datePickerDialog) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        datePickerDialog.setMinDate(calendar);
        datePickerDialog.show(getParentFragmentManager(), "datePicker");
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

    private void setupPriceEditText(@NonNull View view) {
        priceEditText = view.findViewById(R.id.initial_price_layout_reverse_auction_attributes);
        priceEditText.setFilters(new DecimalInputFilter[]{new DecimalInputFilter()});
        priceEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String price = priceEditText.getText().toString();
                if (!price.equals("") && !price.endsWith("€")) {
                    price = price + "€";
                    priceEditText.setText(price);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        feedTheCollector();
    }

    private void feedTheCollector() {
        priceEditText = null;
        dateTextView = null;
    }
}