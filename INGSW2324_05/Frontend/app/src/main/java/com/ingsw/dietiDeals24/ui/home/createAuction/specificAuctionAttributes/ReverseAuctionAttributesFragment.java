package com.ingsw.dietiDeals24.ui.home.createAuction.specificAuctionAttributes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.AuctionHolder;
import com.ingsw.dietiDeals24.controller.CreateAuctionController;
import com.ingsw.dietiDeals24.controller.ImageController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.controller.formstate.ReverseAuctionAttributesFormState;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.Image;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.home.HomeActivity;
import com.ingsw.dietiDeals24.ui.home.createAuction.generalAuctionAttributes.GeneralAuctionAttributesViewModel;
import com.ingsw.dietiDeals24.utility.DecimalInputFilter;
import com.ingsw.dietiDeals24.utility.PopupGenerator;
import com.ingsw.dietiDeals24.utility.ToastManager;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReverseAuctionAttributesFragment extends FragmentOfHomeActivity implements DatePickerDialog.OnDateSetListener {
    private HomeActivity parentActivity;
    private Context parentContext;
    private TextInputLayout initialPriceTextInputLayout;
    private BottomSheetDialog initialPriceBottomSheetDialog;
    private EditText initialPriceEditText;
    private TextView dateTextView, initialPriceErrorTextView;

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
            CreateAuctionController.reverseAuctionInputChanged(
                    initialPriceEditText.getText().toString(),
                    !dateTextView.getText().toString().isEmpty(),
                    getContext()
            );
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = GeneralAuctionAttributesViewModel.getInstance();
        genericAuctionAttributesHolder = viewModel.getNewAuction().getValue();
        parentActivity = (HomeActivity) requireActivity();
        parentContext = parentActivity.getApplicationContext();
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
        observeFormState();
        setupTextViews(view);
        setBackButtonEnabled(true);
        setupEditTexts(view);
        setupDatePicker(view);
        setupCreateAuctionButton(view);
        setupBottomSheetDialogs();
        setupTextInputLayout(view);
    }

    private void setupEditTexts(@NonNull View view) {
        setupPriceEditText(view);
    }

    private void setupTextViews(@NonNull View view) {
        initialPriceErrorTextView = view.findViewById(R.id.initial_price_error_text_view_reverse_auction_attributes);
    }

    private void observeFormState() {
        CreateAuctionController.getReverseAuctionFormState().observe(getViewLifecycleOwner(), formState -> {
            if (formState == null) {
                return;
            }
            createAuctionButton.setEnabled(formState.isDataValid());
            if (formState.getInitialPriceError() != null) {
                showErrorAndChangeColor(
                        formState,
                        initialPriceEditText,
                        initialPriceErrorTextView,
                        initialPriceTextInputLayout
                );
            } else {
                hideErrorAndChangeColor(
                        initialPriceEditText,
                        initialPriceErrorTextView,
                        initialPriceTextInputLayout
                );
            }
            if (formState.getExpirationDateError() != null) {
                dateTextView.setError(formState.getExpirationDateError());
            } else {
                dateTextView.setError(null);
            }
        });
    }

    private void hideErrorAndChangeColor(EditText editText, TextView errorTextView, TextInputLayout textInputLayout) {
        errorTextView.setVisibility(View.GONE);
        editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
        textInputLayout.setBoxStrokeColor(ContextCompat.getColor(requireContext(), R.color.green));
    }

    private void showErrorAndChangeColor(ReverseAuctionAttributesFormState formState, EditText editText, TextView errorTextView, TextInputLayout layout) {
        if (errorTextView.equals(initialPriceErrorTextView)) {
            errorTextView.setText(formState.getInitialPriceError());
        }
        errorTextView.setVisibility(View.VISIBLE);
        editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
        layout.setBoxStrokeColor(ContextCompat.getColor(requireContext(), R.color.red));
    }

    private void setupBottomSheetDialogs() {
        initialPriceBottomSheetDialog = new BottomSheetDialog(requireContext());
        initialPriceBottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_questionmark_layout);

        TextView questionMark = initialPriceBottomSheetDialog.findViewById(R.id.question_bottom_sheet_text_view);
        TextView questionMarkExplanation = initialPriceBottomSheetDialog.findViewById(R.id.question_bottom_sheet_text_view_description);

        questionMark.setText(R.string.reverse_initial_price_question);
        questionMarkExplanation.setText(R.string.reverse_initial_price_explanation);
    }

    private void setupTextInputLayout(View view) {

        initialPriceTextInputLayout = view.findViewById(R.id.initial_price_layout_reverse_auction_attributes);
        initialPriceTextInputLayout.setEndIconOnClickListener(v -> initialPriceBottomSheetDialog.show());
    }

    private void setupCreateAuctionButton(View view) {
        createAuctionButton = view.findViewById(R.id.create_auction_button_reverse_auction_attributes);
        createAuctionButton.setEnabled(false);
        createAuctionButton.setOnClickListener(v -> creteReverseAuction());
    }

    private void creteReverseAuction() {
        new Thread(this::createReverseAuctionThread).start();
    }

    private void createReverseAuctionThread() {
        parentActivity.runOnUiThread(() -> createAuctionButton.startAnimation());
        String title = genericAuctionAttributesHolder.getTitle();
        String description = genericAuctionAttributesHolder.getDescription();
        Category category = genericAuctionAttributesHolder.getCategory();
        Wear wear = genericAuctionAttributesHolder.getWear();
        List<Uri> uriImages = genericAuctionAttributesHolder.getImages();
        String initialPrice = initialPriceEditText.getText().toString();
        String expirationDate = dateTextView.getText().toString().replace("/", "-").concat(" 00:00:00");

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


        try {

            List<Image> images = ImageController.convertUriListToImageList(getContext(), uriImages);
            newReverseAuction.setImages(images);
            CreateAuctionController.createAuction(newReverseAuction).get();
            parentActivity.runOnUiThread(() -> createAuctionButton.revertAnimation());
            viewModel.setNewAuction(new MutableLiveData<>());
            parentActivity.runOnUiThread(() -> PopupGenerator.successAuctionCreationPopup(parentActivity));

        } catch (ExecutionException e) {

            if (e.getCause() instanceof AuthenticationException) {
                parentActivity.runOnUiThread(() -> createAuctionButton.revertAnimation());
                parentActivity.runOnUiThread(() -> ToastManager.showToast(getContext(), "Sessione scaduta, effettua nuovamente il login"));
            } else if (e.getCause() instanceof ConnectionException) {
                parentActivity.runOnUiThread(() -> createAuctionButton.revertAnimation());
                parentActivity.runOnUiThread(() -> ToastManager.showToast(getContext(), "Errore di connessione"));
            }

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
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
            dateTextView.addTextChangedListener(textWatcher);
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
        initialPriceEditText = view.findViewById(R.id.initial_price_edit_text_reverse_auction_attributes);
        initialPriceEditText.setFilters(new DecimalInputFilter[]{new DecimalInputFilter()});
        initialPriceEditText.addTextChangedListener(textWatcher);
    }
}