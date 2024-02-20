package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.CreateAuctionController;
import com.ingsw.dietiDeals24.controller.MyAuctionsController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.Seller;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.Image;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.home.HomeActivity;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes.GeneralAuctionAttributesViewModel;
import com.ingsw.dietiDeals24.ui.home.myAuctions.MyAuctionFragment;
import com.ingsw.dietiDeals24.ui.utility.DecimalInputFilter;
import com.ingsw.dietiDeals24.ui.utility.KeyboardFocusManager;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder.AuctionHolder;
import com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder.ImageConverter;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DownwardAuctionAttributesFragment extends FragmentOfHomeActivity {

    private TextView decrementTimeTextView;
    private EditText initialPriceEditText, minimumPriceEditText, decrementAmountEditText;

    private WheelView<String> minutesWheelView, hoursWheelView, daysWheelView, monthsWheelView;
    private WheelView.WheelViewStyle wheelViewStyle;

    private List<String> hourList, minuteList, dayList, monthList;

    private CircularProgressButton createAuctionButton;

    private KeyboardFocusManager keyboardFocusManager;

    private GeneralAuctionAttributesViewModel viewModel;

    private AuctionHolder genericAuctionAttributesHolder;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_downaward_auction_attributes, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupWheelViews(view);
        setupEditTexts(view);
        setupKeyboardFocusManager(view);
        setupDecrementTimeTextView(view);
        setupCreateAuctionButton(view);
    }


    private void setupEditTexts(View view) {
        setupMinPriceEditText(view);
        setupDecrementAmountEditText(view);
        setupInitialPriceEditText(view);
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
    }

    private void setupInitialPriceEditText(@NonNull View view) {
        initialPriceEditText = view.findViewById(R.id.initial_price_edit_text_downward_auction_attributes);
        initialPriceEditText.setFilters(new DecimalInputFilter[]{new DecimalInputFilter()});
        initialPriceEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String price = initialPriceEditText.getText().toString();
                setEuroToEndIfNotPresent(price, initialPriceEditText);
                if (!price.equals("") && decrementAmountEditText.getText() != null) {

                    minimumPriceEditText.setEnabled(true);
                    decrementAmountEditText.setEnabled(true);

                } else {
                    minimumPriceEditText.setText("");
                    minimumPriceEditText.setEnabled(false);

                    decrementAmountEditText.setText("");
                    decrementAmountEditText.setEnabled(false);
                }
            } else {
                if (!initialPriceEditText.getText().toString().equals("")) {
                    initialPriceEditText.setText(deleteEuroSimbol(initialPriceEditText.getText().toString()));
                }
            }
        });

        decrementAmountEditText.setEnabled(false);
    }

    private void setupMinPriceEditText(@NonNull View view) {
        minimumPriceEditText = view.findViewById(R.id.secret_minimum_price_edit_text_downward_auction_attributes);
        minimumPriceEditText.setFilters(new DecimalInputFilter[]{new DecimalInputFilter()});
        minimumPriceEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {

                String minPrice = minimumPriceEditText.getText().toString();
                String initialPrice = initialPriceEditText.getText().toString();

                if (!minPrice.equals("") && !initialPrice.equals("")) {

                    double minPriceValue = Double.parseDouble(deleteEuroSimbol(minPrice));
                    double initialPriceValue = Double.parseDouble(deleteEuroSimbol(initialPrice));

                    if (minPriceValue > initialPriceValue) {
                        setEuroToEndIfNotPresent(minPrice, minimumPriceEditText);
                        minimumPriceEditText.setError("Il prezzo minimo non può essere maggiore del prezzo iniziale");
                        return;
                    }
                }
                setEuroToEndIfNotPresent(minPrice, minimumPriceEditText);
            } else {
                if (!minimumPriceEditText.getText().toString().equals("")) {
                    minimumPriceEditText.setText(deleteEuroSimbol(minimumPriceEditText.getText().toString()));
                }
            }
        });
    }

    private void setupDecrementAmountEditText(@NonNull View view) {
        decrementAmountEditText = view.findViewById(R.id.decrement_amount_edit_text_downward_auction_attributes);
        decrementAmountEditText.setFilters(new DecimalInputFilter[]{new DecimalInputFilter()});
        decrementAmountEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String price = decrementAmountEditText.getText().toString();
                if (CreateAuctionController.isValidDecrementAmount(Double.parseDouble(deleteEuroSimbol(initialPriceEditText.getText().toString())), Double.parseDouble(deleteEuroSimbol(price)))) {
                    setEuroToEndIfNotPresent(price, decrementAmountEditText);
                } else {
                    decrementAmountEditText.setError("Valore non valido");
                    setEuroToEndIfNotPresent(price, decrementAmountEditText);
                }
            } else {
                if (!decrementAmountEditText.getText().toString().equals("")) {
                    decrementAmountEditText.setText(deleteEuroSimbol(decrementAmountEditText.getText().toString()));
                }
            }
        });
    }


    private void setupCreateAuctionButton(View view) {
        createAuctionButton = view.findViewById(R.id.create_auction_button_downward_auction_attributes);
        createAuctionButton.setOnClickListener(v -> {

            if (fieldsEmpty()) {
                Toast.makeText(getContext(), "Compila tutti i campi", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = genericAuctionAttributesHolder.getTitle();
            String description = genericAuctionAttributesHolder.getDescription();
            Category category = genericAuctionAttributesHolder.getCategory();
            Wear wear = genericAuctionAttributesHolder.getWear();
            List<Uri> uriImages = genericAuctionAttributesHolder.getImages();
            double initialPrice = Double.parseDouble(deleteEuroSimbol(initialPriceEditText.getText().toString()));
            double secretMinimumPrice = Double.parseDouble(deleteEuroSimbol(minimumPriceEditText.getText().toString()));
            double decrementAmount = Double.parseDouble(deleteEuroSimbol(decrementAmountEditText.getText().toString()));

            if (!auctionIsSetWell(initialPrice, secretMinimumPrice, decrementAmount)) {
                return;
            }

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
                    getDecrementTime(),
                    calculateNextDecrement()
            );

            createAuctionButton.startAnimation();

            try {
                List<Image> images = ImageConverter.convertUriListToImageList(getContext(), uriImages);
                newDownwardAuction.setImages(images);
                CreateAuctionController.createAuction(newDownwardAuction).get();
                viewModel.setNewAuction(new MutableLiveData<>());
                createAuctionButton.revertAnimation();

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

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private boolean auctionIsSetWell(double initialPrice, double secretMinimumPrice, double decrementAmount) {
        if (decrementAmountEditText.getError() != null) {

            Toast.makeText(getContext(), "Valore di decremento non valido", Toast.LENGTH_SHORT).show();
            return false;

        } else if (minimumPriceEditText.getError() != null) {

            Toast.makeText(getContext(), "Valore minimo non valido", Toast.LENGTH_SHORT).show();
            return false;

        } else {
            return !auctionDurationLessThen30Minutes(initialPrice, secretMinimumPrice, decrementAmount);
        }
    }

    private boolean fieldsEmpty() {
        if (initialPriceEditText.getText() != null && minimumPriceEditText.getText() != null && decrementAmountEditText.getText() != null) {
            return initialPriceEditText.getText().toString().equals("") ||
                    minimumPriceEditText.getText().toString().equals("") ||
                    decrementAmountEditText.getText().toString().equals("");
        }

        return false;
    }

    private boolean auctionDurationLessThen30Minutes(double initialPrice, double secretMinimumPrice, double decrementAmount) {
        double totalTime = (initialPrice - secretMinimumPrice) / decrementAmount * getDecrementTime() / 60.0;
        if (totalTime < 30) {
            Toast.makeText(getContext(), "L'asta non può terminare in meno di 30 minuti", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private long getDecrementTime() {
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

    private String calculateNextDecrement() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, (int) getDecrementTime());
        Timestamp nextDecrementDate = new Timestamp(calendar.getTimeInMillis());

        return formatTimestamp(nextDecrementDate.toString());
    }

    public String formatTimestamp(String timestamp) {
        String[] parts = timestamp.split(" ");
        String datePart = parts[0];
        String timePart = parts[1].substring(0, 8); // remove milliseconds

        String[] dateParts = datePart.split("-");
        String formattedDate = dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0];

        return formattedDate + " " + timePart;
    }

    private void setupDecrementTimeTextView(@NonNull View view) {
        decrementTimeTextView = view.findViewById(R.id.decrement_time_text_view_downward_auction_attributes);
    }

    private void setupKeyboardFocusManager(View view) {
        keyboardFocusManager = new KeyboardFocusManager(this, view);
        keyboardFocusManager.closeKeyboardWhenUserClickOutside();
        keyboardFocusManager.loseFocusWhenKeyboardClose();
    }

    private void setEuroToEndIfNotPresent(String price, EditText decrementAmountEditText) {
        if (!price.equals("") && !price.endsWith("€")) {
            price = price + "€";
            decrementAmountEditText.setText(price);
        }
    }

    private String deleteEuroSimbol(String string) {
        if (string.endsWith("€"))
            return string.substring(0, string.length() - 1);

        return string;
    }
}
