package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.CreateAuctionController;
import com.ingsw.dietiDeals24.controller.ImageController;
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
import com.ingsw.dietiDeals24.utility.PopupGenerator;
import com.ingsw.dietiDeals24.utility.ToastManager;
import com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder.AuctionHolder;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SilentAuctionAttributesFragment extends FragmentOfHomeActivity implements DatePickerDialog.OnDateSetListener {
    private HomeActivity parentActivity;
    private Context parentContext;

    private ImageView questionMark;
    private BottomSheetDialog bottomSheetDialog;
    private TextView expirationDateTextView, withdrawalTimeTextView;
    private WheelView<String> minutesWheelView, hoursWheelView, daysWheelView, monthsWheelView;
    private WheelView.WheelViewStyle wheelViewStyle;

    private List<String> hourList, minuteList, dayList, monthList;

    private GeneralAuctionAttributesViewModel viewModel;

    private AuctionHolder genericAuctionAttributesHolder;

    private CircularProgressButton createAuctionButton;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            CreateAuctionController.silentAuctionInputChanged(
                    !expirationDateTextView.getText().toString().isEmpty(),
                    getContext()
            );
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackButtonEnabled(true);

        viewModel = GeneralAuctionAttributesViewModel.getInstance();
        genericAuctionAttributesHolder = viewModel.getNewAuction().getValue();
        parentActivity = (HomeActivity) requireActivity();
        parentContext = parentActivity.getApplicationContext();
        setupLists();
        setupWheelViewStyle();
    }

    private void setupBottomSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_questionmark_layout);

        TextView questionMark = bottomSheetDialog.findViewById(R.id.question_bottom_sheet_text_view);
        TextView questionMarkExplanation = bottomSheetDialog.findViewById(R.id.question_bottom_sheet_text_view_description);

        questionMark.setText(R.string.withdrawal_time_question);
        questionMarkExplanation.setText(R.string.withdrawal_time_explanation);
    }

    private void setupQuestionMark(@NonNull View view) {

        questionMark = view.findViewById(R.id.withdrawal_time_question_mark);

        questionMark.setOnClickListener(v -> bottomSheetDialog.show());
    }

    private void setupWheelViewStyle() {
        wheelViewStyle = new WheelView.WheelViewStyle();
        wheelViewStyle.selectedTextColor = ContextCompat.getColor(requireContext(), R.color.blue);
        wheelViewStyle.textColor = ContextCompat.getColor(requireContext(), R.color.gray);
        wheelViewStyle.backgroundColor = ContextCompat.getColor(requireContext(), R.color.white);
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
        observeFormState();
        setupDatePicker(view);
        setupWheelViews(view);
        setupWithdrawalTimeTextView(view);
        setupCreateAuctionButton(view);
        setupBottomSheetDialog();
        setupQuestionMark(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        createAuctionButton.setEnabled(false);
    }

    private void observeFormState() {
        CreateAuctionController.getSilentAuctionFormState().observe(getViewLifecycleOwner(), formState -> {
            if (formState == null) {
                return;
            }
            createAuctionButton.setEnabled(formState.isDataValid());
            if (formState.getExpirationDateError() != null) {
                expirationDateTextView.setError(formState.getExpirationDateError());
            } else {
                expirationDateTextView.setError(null);
            }
        });
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
        createAuctionButton.setOnClickListener(v -> createSilentAuction());
    }

    private void createSilentAuction() {
        new Thread(this::createSilentAuctionThread).start();
    }

    private void createSilentAuctionThread() {
        if (isWithdrawalBidTimeNotValid()) {
            requireActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Il tempo di scadenza delle offerte non puo essere zero", Toast.LENGTH_SHORT).show());
            return;
        }

        String title = genericAuctionAttributesHolder.getTitle();
        String description = genericAuctionAttributesHolder.getDescription();
        Category category = genericAuctionAttributesHolder.getCategory();
        Wear wear = genericAuctionAttributesHolder.getWear();
        String expirationDate = expirationDateTextView.getText().toString().replace("/", "-").concat(" 00:00:00");

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

        parentActivity.runOnUiThread(() -> createAuctionButton.startAnimation());

        try {
            List<Image> images = ImageController.convertUriListToImageList(getContext(), genericAuctionAttributesHolder.getImages());
            newSilentAuction.setImages(images);
            CreateAuctionController.createAuction(newSilentAuction).get();
            parentActivity.runOnUiThread(() -> createAuctionButton.revertAnimation());
            viewModel.setNewAuction(new MutableLiveData<>());
            parentActivity.runOnUiThread(() -> PopupGenerator.successAuctionCreationPopup(parentActivity));
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

    private boolean isWithdrawalBidTimeNotValid() {
        return minutesWheelView.getSelectionItem().equals("0") &&
                hoursWheelView.getSelectionItem().equals("0") &&
                daysWheelView.getSelectionItem().equals("0") &&
                monthsWheelView.getSelectionItem().equals("0");
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
        expirationDateTextView = view.findViewById(R.id.expiration_date_text_view_credit_card_attributes);
        expirationDateTextView.setFocusable(false);
        expirationDateTextView.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                    SilentAuctionAttributesFragment.this,
                    2021,
                    0,
                    1
            );
            setTheMinumumToday(datePickerDialog);
        });
        expirationDateTextView.addTextChangedListener(textWatcher);
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
        expirationDateTextView.setText(sb.toString());
    }
}