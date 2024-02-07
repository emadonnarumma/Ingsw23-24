package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes;

import android.graphics.Color;
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
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

public class DownwardAuctionAttributesFragment extends Fragment {

    private EditText initialPriceEditText, minimumPriceEditText, decrementAmountEditText;
    private WheelView<String> unitWheelView, numberWheelView;
    private WheelView.WheelViewStyle wheelViewStyle;

    private List<String> hourList, minuteList, dayList, monthList, unitList;

    private KeyboardFocusManager keyboardFocusManager;

    private GeneralAuctionAttributesViewModel viewModel;

    private AuctionHolder genericAuctionAttributesHolder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(GeneralAuctionAttributesViewModel.class);
        genericAuctionAttributesHolder = viewModel.getNewAuction().getValue();
        setupLists();
        setupWheelViewStyle();
    }

    private void setupWheelViewStyle() {
        wheelViewStyle = new WheelView.WheelViewStyle();
        wheelViewStyle.selectedTextColor = getResources().getColor(R.color.blue);
        wheelViewStyle.textColor = getResources().getColor(R.color.gray);
        wheelViewStyle.backgroundColor = getResources().getColor(R.color.white);
        wheelViewStyle.selectedTextSize = 20;
    }

    private void setupLists() {
        unitList = new ArrayList<>();
        unitList.add("Ore");
        unitList.add("Minuti");
        unitList.add("Giorni");
        unitList.add("Mesi");

        hourList = new ArrayList<>();
        for (int i = 1; i <= 24; i++) {
            hourList.add(String.valueOf(i));
        }

        minuteList = new ArrayList<>();
        for (int i = 1; i <= 60; i++) {
            minuteList.add(String.valueOf(i));
        }

        dayList = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            dayList.add(String.valueOf(i));
        }

        monthList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            monthList.add(String.valueOf(i));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_downaward_auction_attributes, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupNumberWheelView(view);
        setupUnitWheelView(view);
        setupMinPriceEditText(view);
        setupDecrementAmountEditText(view);
        setupInitialPriceEditText(view);
        setupKeyboardFocusManager(view);
    }

    private void setupUnitWheelView(@NonNull View view) {
        unitWheelView = view.findViewById(R.id.unit_wheel_view_downward_auction_attributes);
        unitWheelView.setWheelAdapter(new ArrayWheelAdapter(getContext()));
        unitWheelView.setSkin(WheelView.Skin.Holo);
        unitWheelView.setWheelData(unitList);
        unitWheelView.setStyle(wheelViewStyle);

        unitWheelView.setOnWheelItemSelectedListener((position, item) -> {
            switch (item) {
                case "Ore":
                    numberWheelView.setWheelData(hourList);
                    break;

                case "Minuti":
                    numberWheelView.setWheelData(minuteList);
                    break;

                case "Giorni":
                    numberWheelView.setWheelData(dayList);
                    break;

                case "Mesi":
                    numberWheelView.setWheelData(monthList);
                    break;
            }
        });
    }

    private void setupNumberWheelView(@NonNull View view) {
        numberWheelView = view.findViewById(R.id.number_wheel_view_downward_auction_attributes);
        numberWheelView.setWheelAdapter(new ArrayWheelAdapter(getContext()));
        numberWheelView.setSkin(WheelView.Skin.Holo);
        numberWheelView.setStyle(wheelViewStyle);
        numberWheelView.setWheelData(hourList);
    }

    private void setupMinPriceEditText(@NonNull View view) {
        minimumPriceEditText = view.findViewById(R.id.secret_minimum_price_edit_text_downward_auction_attributes);
        minimumPriceEditText.setFilters(new DecimalInputFilter[]{new DecimalInputFilter()});
        minimumPriceEditText.setOnFocusChangeListener((v, hasFocus) -> {
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
                    decrementAmountEditText.setError("Valore non valido");
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