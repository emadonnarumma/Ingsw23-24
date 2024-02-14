package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.CreateAuctionController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.Buyer;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.model.Image;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes.GeneralAuctionAttributesViewModel;
import com.ingsw.dietiDeals24.ui.home.myAuctions.MyAuctionFragment;
import com.ingsw.dietiDeals24.ui.utility.DecimalInputFilter;
import com.ingsw.dietiDeals24.ui.utility.KeyboardFocusManager;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder.AuctionHolder;
import com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder.ImageConverter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReverseAuctionAttributesFragment extends FragmentOfHomeActivity implements DatePickerDialog.OnDateSetListener {

    private KeyboardFocusManager keyboardFocusManager;

    private EditText priceEditText;
    private TextView dateTextView;

    private CircularProgressButton createAuctionButton;

    private GeneralAuctionAttributesViewModel viewModel;

    private AuctionHolder genericAuctionAttributesHolder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(GeneralAuctionAttributesViewModel.class);
        genericAuctionAttributesHolder = viewModel.getNewAuction().getValue();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reverse_auction_attributes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(true);

        setupPriceEditText(view);
        setupDatePicker(view);
        setupKeyboardFocusManager(view);
        setupCreateAuctionButton(view);
    }

    private void setupCreateAuctionButton(View view) {
        createAuctionButton = view.findViewById(
                R.id.create_auction_button_reverse_auction_attributes
        );

        createAuctionButton.setOnClickListener(v -> {

            String title = genericAuctionAttributesHolder.getTitle();
            String description = genericAuctionAttributesHolder.getDescription();
            Category category = genericAuctionAttributesHolder.getCategory();
            Wear wear = genericAuctionAttributesHolder.getWear();
            List<Uri> uriImages = genericAuctionAttributesHolder.getImages();
            String initialPrice = deleteMoneySimbol(priceEditText.getText().toString());
            String expirationDate = dateTextView.getText().toString().replace("/", "-").concat(" 00:00:00");

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

            createAuctionButton.startAnimation();

            try {
                CreateAuctionController.createAuction(newReverseAuction, images).get();

                ((Buyer) UserHolder.user).getReverseAuctions().add(newReverseAuction);

                getParentFragmentManager().beginTransaction().replace(
                        R.id.fragment_container_home,
                        new MyAuctionFragment()
                ).commit();

                createAuctionButton.revertAnimation();

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
            }
        });
    }

    private String deleteMoneySimbol(String string) {
        if (string.endsWith("€"))
            return string.substring(0, string.length() - 1);

        return string;
    }

    private void setupKeyboardFocusManager(View view) {
        keyboardFocusManager = new KeyboardFocusManager(this, view);
        keyboardFocusManager.closeKeyboardWhenUserClickOutside();
        keyboardFocusManager.loseFocusWhenKeyboardClose();
    }

    private void setupDatePicker(View view) {
        dateTextView = view.findViewById(R.id.date_text_reverse_auction_attributes);
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
        priceEditText = view.findViewById(R.id.initial_price_edit_text_reverse_auction_attributes);
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
}