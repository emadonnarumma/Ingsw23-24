package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.CreateAuctionController;
import com.ingsw.dietiDeals24.controller.ImageController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.controller.formstate.DownwardAuctionAttributesFormState;
import com.ingsw.dietiDeals24.controller.formstate.ReverseAuctionAttributesFormState;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.Image;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.home.HomeActivity;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes.GeneralAuctionAttributesViewModel;
import com.ingsw.dietiDeals24.ui.utility.DecimalInputFilter;
import com.ingsw.dietiDeals24.ui.utility.PopupGeneratorOf;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder.AuctionHolder;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DownwardAuctionAttributesFragment extends FragmentOfHomeActivity {
    private HomeActivity parentActivity;
    private Context parentContext;

    private TextInputLayout initialPriceTextInputLayout, minimumPriceTextInputLayout, decrementAmountTextInputLayout;
    private BottomSheetDialog initialPriceBottomSheetDialog, minimumPriceBottomSheetDialog, decrementAmountBottomSheetDialog;

    private TextView decrementTimeTextView;
    private EditText initialPriceEditText, minimumPriceEditText, decrementAmountEditText;

    private WheelView<String> minutesWheelView, hoursWheelView, daysWheelView, monthsWheelView;
    private WheelView.WheelViewStyle wheelViewStyle;

    private List<String> hourList, minuteList, dayList, monthList;

    private CircularProgressButton createAuctionButton;

    private GeneralAuctionAttributesViewModel viewModel;

    private AuctionHolder genericAuctionAttributesHolder;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            CreateAuctionController.downwardAuctionInputChanged(
                    initialPriceEditText.getText().toString(),
                    minimumPriceEditText.getText().toString(),
                    decrementAmountEditText.getText().toString(),
                    monthsWheelView.getSelectionItem(),
                    daysWheelView.getSelectionItem(),
                    hoursWheelView.getSelectionItem(),
                    minutesWheelView.getSelectionItem(),
                    getContext()
            );
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_downaward_auction_attributes, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupWheelViews(view);
        setupEditTexts(view);
        setupDecrementTimeTextView(view);
        setupCreateAuctionButton(view);
        setupBottomSheetDialogs();
        setupTextInputLayout(view);
        observeFormState();
    }

    private void observeFormState() {
        CreateAuctionController.getDownwardAuctionFormState().observe(getViewLifecycleOwner(), formState -> {
            if (formState == null) {
                return;
            }
            createAuctionButton.setEnabled(formState.isDataValid());
            if (formState.getInitialPriceError() != null) {
                initialPriceTextInputLayout.setErrorEnabled(true);
                initialPriceTextInputLayout.setError(formState.getInitialPriceError());
            } else {
                initialPriceTextInputLayout.setErrorEnabled(false);
                initialPriceTextInputLayout.setError(null);
            }
            if (formState.getMinimalPriceError() != null) {
                minimumPriceTextInputLayout.setErrorEnabled(true);
                minimumPriceTextInputLayout.setError(formState.getMinimalPriceError());
            } else {
                minimumPriceTextInputLayout.setErrorEnabled(false);
                minimumPriceTextInputLayout.setError(null);
            }
            if (formState.getDecrementAmountError() != null) {
                decrementAmountTextInputLayout.setErrorEnabled(true);
                decrementAmountTextInputLayout.setError(formState.getDecrementAmountError());
            } else {
                decrementAmountTextInputLayout.setErrorEnabled(false);
                decrementAmountTextInputLayout.setError(null);
            }
        });
    }

    private void setupBottomSheetDialogs() {
        initialPriceBottomSheetDialog = new BottomSheetDialog(requireContext());
        initialPriceBottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_questionmark_layout);

        TextView questionMark = initialPriceBottomSheetDialog.findViewById(R.id.question_bottom_sheet_text_view);
        TextView questionMarkExplanation = initialPriceBottomSheetDialog.findViewById(R.id.question_bottom_sheet_text_view_description);

        questionMark.setText(R.string.downward_initial_price_question);
        questionMarkExplanation.setText(R.string.downward_initial_price_explanation);

        minimumPriceBottomSheetDialog = new BottomSheetDialog(requireContext());
        minimumPriceBottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_questionmark_layout);

        questionMark = minimumPriceBottomSheetDialog.findViewById(R.id.question_bottom_sheet_text_view);
        questionMarkExplanation = minimumPriceBottomSheetDialog.findViewById(R.id.question_bottom_sheet_text_view_description);

        questionMark.setText(R.string.minimum_price_question);
        questionMarkExplanation.setText(R.string.minimum_price_explanation);

        decrementAmountBottomSheetDialog = new BottomSheetDialog(requireContext());
        decrementAmountBottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_questionmark_layout);

        questionMark = decrementAmountBottomSheetDialog.findViewById(R.id.question_bottom_sheet_text_view);
        questionMarkExplanation = decrementAmountBottomSheetDialog.findViewById(R.id.question_bottom_sheet_text_view_description);

        questionMark.setText(R.string.decrement_amount_question);
        questionMarkExplanation.setText(R.string.decrement_amount_explanation);
    }

    private void setupTextInputLayout(View view) {

        initialPriceTextInputLayout = view.findViewById(R.id.initial_price_layout_downward_auction_attributes);
        initialPriceTextInputLayout.setEndIconOnClickListener(v -> initialPriceBottomSheetDialog.show());

        minimumPriceTextInputLayout = view.findViewById(R.id.minimum_price_layout_downward_auction_attributes);
        minimumPriceTextInputLayout.setEndIconOnClickListener(v -> minimumPriceBottomSheetDialog.show());

        decrementAmountTextInputLayout = view.findViewById(R.id.decrement_amount_layout_downward_auction_attributes);
        decrementAmountTextInputLayout.setEndIconOnClickListener(v -> decrementAmountBottomSheetDialog.show());

    }

    private void setupEditTexts(View view) {
        minimumPriceEditText = view.findViewById(R.id.secret_minimum_price_edit_text_downward_auction_attributes);
        decrementAmountEditText = view.findViewById(R.id.decrement_amount_edit_text_downward_auction_attributes);
        initialPriceEditText = view.findViewById(R.id.initial_price_edit_text_downward_auction_attributes);

        setupInitialPriceEditTextListner(view);
        setupDecrementAmountEditTextListner(view);
        setupMinPriceEditTextListner(view);
    }

    private void setupWheelViews(View view) {
        setupMinutesWheelView(view);
        setupHoursWheel(view);
        setupDaysWheelView(view);
        setupMonthsWheelView(view);
    }

    private void setupMinutesWheelView(@NonNull View view) {
        minutesWheelView = view.findViewById(R.id.minutes_wheel_view_downward_auction_attributes);
        minutesWheelView.setWheelAdapter(new ArrayWheelAdapter(getContext()));
        minutesWheelView.setSkin(WheelView.Skin.Holo);
        minutesWheelView.setStyle(wheelViewStyle);
        minutesWheelView.setWheelData(minuteList);
        minutesWheelView.setExtraText("Minuto/i", getResources().getColor(R.color.blue), 40, 100);
        minutesWheelView.setOnWheelItemSelectedListener((position, data) -> updateDecrementTimeTextView());
    }

    private void setupHoursWheel(@NonNull View view) {
        hoursWheelView = view.findViewById(R.id.hours_wheel_view_downward_auction_attributes);
        hoursWheelView.setWheelAdapter(new ArrayWheelAdapter(getContext()));
        hoursWheelView.setSkin(WheelView.Skin.Holo);
        hoursWheelView.setStyle(wheelViewStyle);
        hoursWheelView.setWheelData(hourList);
        hoursWheelView.setExtraText("Ora/e", getResources().getColor(R.color.blue), 40, 100);
        hoursWheelView.setSelection(1);
        hoursWheelView.setOnWheelItemSelectedListener((position, data) -> updateDecrementTimeTextView());
    }

    private void setupDaysWheelView(View view) {
        daysWheelView = view.findViewById(R.id.days_wheel_view_downward_auction_attributes);
        daysWheelView.setWheelAdapter(new ArrayWheelAdapter(getContext()));
        daysWheelView.setSkin(WheelView.Skin.Holo);
        daysWheelView.setStyle(wheelViewStyle);
        daysWheelView.setWheelData(dayList);
        daysWheelView.setExtraText("Giorno/i", getResources().getColor(R.color.blue), 40, 100);
        daysWheelView.setOnWheelItemSelectedListener((position, data) -> updateDecrementTimeTextView());
    }

    private void setupMonthsWheelView(View view) {
        monthsWheelView = view.findViewById(R.id.months_wheel_view_downward_auction_attributes);
        monthsWheelView.setWheelAdapter(new ArrayWheelAdapter(getContext()));
        monthsWheelView.setSkin(WheelView.Skin.Holo);
        monthsWheelView.setStyle(wheelViewStyle);
        monthsWheelView.setWheelData(monthList);
        monthsWheelView.setExtraText("Mese/i", getResources().getColor(R.color.blue), 40, 100);
        monthsWheelView.setOnWheelItemSelectedListener((position, data) -> updateDecrementTimeTextView());
    }

    private void updateDecrementTimeTextView() {
        String decrementTime = "Decremento ogni: ";
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
        decrementTimeTextView.setText(decrementTime);
        CreateAuctionController.downwardAuctionInputChanged(
                initialPriceEditText.getText().toString(),
                minimumPriceEditText.getText().toString(),
                decrementAmountEditText.getText().toString(),
                monthsWheelView.getSelectionItem(),
                daysWheelView.getSelectionItem(),
                hoursWheelView.getSelectionItem(),
                minutesWheelView.getSelectionItem(),
                getContext()
        );
    }

    private void setupInitialPriceEditTextListner(@NonNull View view) {
        initialPriceEditText.setFilters(new DecimalInputFilter[]{new DecimalInputFilter()});
        initialPriceEditText.addTextChangedListener(textWatcher);
    }

    private void setupMinPriceEditTextListner(@NonNull View view) {
        minimumPriceEditText.setFilters(new DecimalInputFilter[]{new DecimalInputFilter()});
        minimumPriceEditText.addTextChangedListener(textWatcher);
    }

    private void setupDecrementAmountEditTextListner(@NonNull View view) {
        decrementAmountEditText.setFilters(new DecimalInputFilter[]{new DecimalInputFilter()});
        decrementAmountEditText.addTextChangedListener(textWatcher);
    }

    private void setupCreateAuctionButton(View view) {
        createAuctionButton = view.findViewById(R.id.create_auction_button_downward_auction_attributes);
        createAuctionButton.setOnClickListener(v -> new Thread(() -> {
            parentActivity.runOnUiThread(() -> createAuctionButton.startAnimation());

            if (fieldsEmpty()) {
                parentActivity.runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Compila tutti i campi", Toast.LENGTH_SHORT).show();
                    createAuctionButton.revertAnimation();
                });
                return;
            }

            if (!CreateAuctionController.isValidDecrementAmount(Double.parseDouble(initialPriceEditText.getText().toString()), Double.parseDouble(decrementAmountEditText.getText().toString()))) {
                parentActivity.runOnUiThread(() -> {
                    decrementAmountEditText.setError("Inserisci un valore di decremento valido");
                    createAuctionButton.revertAnimation();
                });
                return;
            }

            String title = genericAuctionAttributesHolder.getTitle();
            String description = genericAuctionAttributesHolder.getDescription();
            Category category = genericAuctionAttributesHolder.getCategory();
            Wear wear = genericAuctionAttributesHolder.getWear();
            List<Uri> uriImages = genericAuctionAttributesHolder.getImages();
            double initialPrice = Double.parseDouble(initialPriceEditText.getText().toString());
            double secretMinimumPrice = Double.parseDouble(minimumPriceEditText.getText().toString());
            double decrementAmount = Double.parseDouble(decrementAmountEditText.getText().toString());
            long months = Long.parseLong(monthsWheelView.getSelectionItem());
            long days = Long.parseLong(daysWheelView.getSelectionItem());
            long hours = Long.parseLong(hoursWheelView.getSelectionItem());
            long minutes = Long.parseLong(minutesWheelView.getSelectionItem());
            DownwardAuction newDownwardAuction = new DownwardAuction(
                    UserHolder.user,
                    title,
                    description,
                    wear,
                    category,
                    AuctionStatus.IN_PROGRESS,
                    secretMinimumPrice,
                    initialPrice,
                    decrementAmount,
                    CreateAuctionController.getDecrementTime(months, days, hours, minutes),
                    CreateAuctionController.calculateNextDecrement(months, days, hours, minutes)
            );


            try {

                List<Image> images = ImageController.convertUriListToImageList(getContext(), uriImages);
                newDownwardAuction.setImages(images);
                CreateAuctionController.createAuction(newDownwardAuction).get();
                viewModel.setNewAuction(new MutableLiveData<>());
                parentActivity.runOnUiThread(() -> {
                    createAuctionButton.revertAnimation();
                    parentActivity.runOnUiThread(() -> PopupGeneratorOf.successAuctionCreationPopup(parentActivity));
                });

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
        }).start());
    }

    private boolean fieldsEmpty() {
        if (initialPriceEditText.getText() != null && minimumPriceEditText.getText() != null && decrementAmountEditText.getText() != null) {
            return initialPriceEditText.getText().toString().equals("") ||
                    minimumPriceEditText.getText().toString().equals("") ||
                    decrementAmountEditText.getText().toString().equals("");
        }

        return false;
    }

    private void setupDecrementTimeTextView(@NonNull View view) {
        decrementTimeTextView = view.findViewById(R.id.decrement_time_text_view_downward_auction_attributes);
    }
}
