package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.CreateAuctionController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.model.Image;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.home.HomeActivity;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes.GeneralAuctionAttributesViewModel;
import com.ingsw.dietiDeals24.ui.home.myAuctions.MyAuctionFragment;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder.AuctionHolder;
import com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder.ImageConverter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SilentAuctionAttributesFragment extends FragmentOfHomeActivity implements DatePickerDialog.OnDateSetListener {

    private TextView dateTextView, withdrawalTimeTextView;
    private WheelView<String> minutesWheelView, hoursWheelView, daysWheelView, monthsWheelView;

    private WheelView.WheelViewStyle wheelViewStyle;

    private List<String> hourList, minuteList, dayList, monthList;

    private GeneralAuctionAttributesViewModel viewModel;

    private AuctionHolder genericAuctionAttributesHolder;

    private CircularProgressButton createAuctionButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackButtonEnabled(true);

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
        hourList = new ArrayList<>();
        for (int i = 0; i <= 24; i++) {
            hourList.add(String.valueOf(i));

        }

        minuteList = new ArrayList<>();
        for (int i = 0; i <= 60; i++) {
            minuteList.add(String.valueOf(i));
        }

        dayList = new ArrayList<>();
        for (int i = 0; i <= 31; i++) {
            dayList.add(String.valueOf(i));
        }

        monthList = new ArrayList<>();
        for (int i = 0; i <= 12; i++) {
            monthList.add(String.valueOf(i));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_silent_auction_attributes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupDatePicker(view);
        setupWheelViews(view);
        setupWithdrawalTimeTextView(view);
        setupCreateAuctionButton(view);
    }

    private void setupWheelViews(View view) {
        setupMinutesWheelView(view);
        setupHoursWheel(view);
        setupDaysWheelView(view);
        setupMonthsWheelView(view);
    }

    private void setupMinutesWheelView(@NonNull View view) {
        minutesWheelView = view.findViewById(R.id.minutes_wheel_view_silent_auction_attributes);
        minutesWheelView.setWheelAdapter(new ArrayWheelAdapter(getContext()));
        minutesWheelView.setSkin(WheelView.Skin.Holo);
        minutesWheelView.setStyle(wheelViewStyle);
        minutesWheelView.setWheelData(minuteList);
        minutesWheelView.setExtraText("Minuto/i", getResources().getColor(R.color.blue), 40, 100);
        minutesWheelView.setOnWheelItemSelectedListener((position, data) -> updateWithdrawalTimeTextView());
    }

    private void setupHoursWheel(@NonNull View view) {
        hoursWheelView = view.findViewById(R.id.hours_wheel_view_silent_auction_attributes);
        hoursWheelView.setWheelAdapter(new ArrayWheelAdapter(getContext()));
        hoursWheelView.setSkin(WheelView.Skin.Holo);
        hoursWheelView.setStyle(wheelViewStyle);
        hoursWheelView.setWheelData(hourList);
        hoursWheelView.setExtraText("Ora/e", getResources().getColor(R.color.blue), 40, 100);
        hoursWheelView.setSelection(1);
        hoursWheelView.setOnWheelItemSelectedListener((position, data) -> updateWithdrawalTimeTextView());
    }

    private void setupDaysWheelView(View view) {
        daysWheelView = view.findViewById(R.id.days_wheel_view_silent_auction_attributes);
        daysWheelView.setWheelAdapter(new ArrayWheelAdapter(getContext()));
        daysWheelView.setSkin(WheelView.Skin.Holo);
        daysWheelView.setStyle(wheelViewStyle);
        daysWheelView.setWheelData(dayList);
        daysWheelView.setExtraText("Giorno/i", getResources().getColor(R.color.blue), 40, 100);
        daysWheelView.setOnWheelItemSelectedListener((position, data) -> updateWithdrawalTimeTextView());
    }

    private void setupMonthsWheelView(View view) {
        monthsWheelView = view.findViewById(R.id.months_wheel_view_silent_auction_attributes);
        monthsWheelView.setWheelAdapter(new ArrayWheelAdapter(getContext()));
        monthsWheelView.setSkin(WheelView.Skin.Holo);
        monthsWheelView.setStyle(wheelViewStyle);
        monthsWheelView.setWheelData(monthList);
        monthsWheelView.setExtraText("Mese/i", getResources().getColor(R.color.blue), 40, 100);
        monthsWheelView.setOnWheelItemSelectedListener((position, data) -> updateWithdrawalTimeTextView());
    }

    private void setupWithdrawalTimeTextView(View view) {
        withdrawalTimeTextView = view.findViewById(R.id.withdrawal_time_text_view_silent_auction_attributes);
    }

    private void setupCreateAuctionButton(View view) {
        createAuctionButton = view.findViewById(R.id.create_auction_button_downward_auction_attributes);
        createAuctionButton.setOnClickListener(v -> {

            if (fieldsEmpty()) {
                Toast.makeText(getContext(), "Inserisci una data di scadenza", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = genericAuctionAttributesHolder.getTitle();
            String description = genericAuctionAttributesHolder.getDescription();
            Category category = genericAuctionAttributesHolder.getCategory();
            Wear wear = genericAuctionAttributesHolder.getWear();
            String expirationDate = dateTextView.getText().toString().replace("/", "-").concat(" 00:00:00");


            SilentAuction newSilentAuction = new SilentAuction(
                    UserHolder.user,
                    title,
                    description,
                    wear,
                    category,
                    AuctionStatus.IN_PROGRESS,
                    expirationDate,
                    getWithDrawalTime()
            );

            createAuctionButton.startAnimation();

            try {
                List<Image> images = ImageConverter.convertUriListToImageList(getContext(), genericAuctionAttributesHolder.getImages());
                newSilentAuction.setImages(images);
                CreateAuctionController.createAuction(newSilentAuction).get();
                createAuctionButton.revertAnimation();
                viewModel.setNewAuction(new MutableLiveData<>());

                getParentFragmentManager().beginTransaction().replace(
                        R.id.fragment_container_home,
                        new MyAuctionFragment()
                ).commit();

                ((HomeActivity) requireActivity()).getNavigationBarView().setSelectedItemId(R.id.navigation_my_auctions);

            } catch (ExecutionException e) {

                if (e.getCause() instanceof AuthenticationException) {
                    requireActivity().runOnUiThread(() -> createAuctionButton.revertAnimation());
                    requireActivity().runOnUiThread(() -> ToastManager.showToast(getContext(), "Sessione scaduta, effettua nuovamente il login"));
                } else if (e.getCause() instanceof ConnectionException) {
                    requireActivity().runOnUiThread(() -> createAuctionButton.revertAnimation());
                    requireActivity().runOnUiThread(() -> ToastManager.showToast(getContext(), "Errore di connessione"));
                }

            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    private long getWithDrawalTime() {
        long months = Integer.parseInt(monthsWheelView.getSelectionItem());
        long days = Integer.parseInt(daysWheelView.getSelectionItem());
        long hours = Integer.parseInt(hoursWheelView.getSelectionItem());
        int minutes = Integer.parseInt(minutesWheelView.getSelectionItem());

        long totalSeconds = 0;

        totalSeconds += (long) months * 30 * 24 * 60 * 60;
        totalSeconds += (long) days * 24 * 60 * 60;
        totalSeconds += (long) hours * 60 * 60;
        totalSeconds += minutes * 60L;

        return totalSeconds;
    }

    private boolean fieldsEmpty() {
        if (dateTextView.getText() != null) {
            return dateTextView.getText().toString().equals("");
        }

        return false;
    }

    private void updateWithdrawalTimeTextView() {
        String decrementTime = "Offerte ritirabili entro: ";
        if (!monthsWheelView.getSelectionItem().equals("0")) {
            decrementTime += monthsWheelView.getSelectionItem() + "M ";
        }
        if (!daysWheelView.getSelectionItem().equals("0")) {
            decrementTime += daysWheelView.getSelectionItem() + "G ";
        }
        if (!hoursWheelView.getSelectionItem().equals("0")) {
            decrementTime += hoursWheelView.getSelectionItem() + "H ";
        }
        if (!minutesWheelView.getSelectionItem().equals("0")) {
            decrementTime += minutesWheelView.getSelectionItem() + "Min";
        }
        withdrawalTimeTextView.setText(decrementTime);
    }

    private void setupDatePicker(View view) {
        dateTextView = view.findViewById(R.id.date_text_silent_auction_attributes);
        dateTextView.setFocusable(false);
        dateTextView.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                    SilentAuctionAttributesFragment.this,
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
}