package com.ingsw.dietiDeals24.ui.home.searchAuctions.makePayment.fragments;

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

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MakePaymentController;
import com.ingsw.dietiDeals24.controller.formstate.MakePaymentFormState;
import com.ingsw.dietiDeals24.utility.ExpirationDateTextWatcher;
import com.ingsw.dietiDeals24.utility.FourDigitCardFormatWatcher;

public class CreditCardAttributesFragment extends FragmentOfMakePaymentActivity {
    private EditText cardNumberEditText, cardOwnerNameEditText,
            cardOwnerSurnameEditText, cvvEditText, expirationDateEditText;
    private CircularProgressButton goToSummaryButton;

    private BottomSheetDialog cvvBottomSheetDialog;

    private TextInputLayout cvvTextInputLayout, cardNumberTextInputLayout, cardOwnerNameTextInputLayout, cardOwnerSurnameTextInputLayout, expirationDateTextInputLayout;

    private TextView cardNumberErrorTextView, cardOwnerNameErrorTextView, cardOwnerSurnameErrorTextView, expirationDateErrorTextView, cvvErrorTextView;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            MakePaymentController.makePaymentDataChanged(
                    cardNumberEditText.getText().toString(),
                    cardOwnerNameEditText.getText().toString(),
                    cardOwnerSurnameEditText.getText().toString(),
                    expirationDateEditText.getText().toString(),
                    cvvEditText.getText().toString(),
                    getContext()
            );
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackButtonEnabled(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_credit_card_attributes, container, false);
    }

    private void setupTextViews(View view) {

        cardNumberErrorTextView = view.findViewById(R.id.card_number_error_text_view_credit_card_attributes);
        cardOwnerNameErrorTextView = view.findViewById(R.id.card_owner_name_error_text_view_credit_card_attributes);
        cardOwnerSurnameErrorTextView = view.findViewById(R.id.card_owner_surname_error_text_view_credit_card_attributes);
        expirationDateErrorTextView = view.findViewById(R.id.expiration_date_error_text_view_credit_card_attributes);
        cvvErrorTextView = view.findViewById(R.id.cvv_error_text_view_credit_card_attributes);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupEditTextViews(view);
        setupTextViews(view);
        setupGoToSummaryButton(view);

        setupBottomSheetDialog();
        setupTextInputLayouts(view);
        observeMakePaymentFormState();

        goToSummaryButton.setEnabled(false);
    }

    private void setupEditTextViews(@NonNull View view) {
        setupCardNumberEditText(view);
        setupCardOwnerNameEditText(view);
        setupCardOwnerSurnameEditText(view);
        setupCvvEditText(view);
        setupExpirationDateEditText(view);
    }

    private void setupCvvEditText(@NonNull View view) {
        cvvEditText = view.findViewById(R.id.cvv_edit_text_credit_card_attributes);
        cvvEditText.addTextChangedListener(textWatcher);
    }

    private void setupCardOwnerSurnameEditText(@NonNull View view) {
        cardOwnerSurnameEditText = view.findViewById(R.id.card_owner_surname_edit_text_card_attributes);
        cardOwnerSurnameEditText.addTextChangedListener(textWatcher);
    }

    private void setupCardOwnerNameEditText(@NonNull View view) {
        cardOwnerNameEditText = view.findViewById(R.id.card_owner_name_edit_text_card_attributes);
        cardOwnerNameEditText.addTextChangedListener(textWatcher);
    }

    private void setupCardNumberEditText(@NonNull View view) {
        cardNumberEditText = view.findViewById(R.id.card_number_edit_text_card_attributes);
        cardNumberEditText.addTextChangedListener(new FourDigitCardFormatWatcher(cardNumberEditText));
        cardNumberEditText.addTextChangedListener(textWatcher);
    }

    private void setupTextInputLayouts(@NonNull View view) {
        cardNumberTextInputLayout = view.findViewById(R.id.card_number_layout_credit_card_attributes);
        cardOwnerNameTextInputLayout = view.findViewById(R.id.card_owner_name_layout_credit_card_attributes);
        cardOwnerSurnameTextInputLayout = view.findViewById(R.id.card_owner_surname_layout_credit_card_attributes);
        expirationDateTextInputLayout = view.findViewById(R.id.expiration_date_layout_credit_card_attributes);
        setupCvvTextInputLayout(view);
    }

    private void setupCvvTextInputLayout(@NonNull View view) {
        cvvTextInputLayout = view.findViewById(R.id.cvv_layout_credit_card_attributes);
        cvvTextInputLayout.setEndIconOnClickListener(v -> cvvBottomSheetDialog.show());
    }

    private void observeMakePaymentFormState() {

        MakePaymentController.getMakePaymentFormState().observe(getViewLifecycleOwner(), makePaymentFormState -> {
            if (makePaymentFormState == null) {
                return;
            }

            goToSummaryButton.setEnabled(makePaymentFormState.isDataValid());

            if (makePaymentFormState.getCardCodeError() != null) {
                showErrorAndChangeColor(
                        makePaymentFormState,
                        cardNumberEditText,
                        cardNumberErrorTextView,
                        cardNumberTextInputLayout
                );
            } else {
                hideErrorAndChangeColor(
                        cardNumberEditText,
                        cardNumberErrorTextView,
                        cardNumberTextInputLayout
                );
            }

            if (makePaymentFormState.getNameError() != null) {

                showErrorAndChangeColor(
                        makePaymentFormState,
                        cardOwnerNameEditText,
                        cardOwnerNameErrorTextView,
                        cardOwnerNameTextInputLayout
                );
            } else {
                hideErrorAndChangeColor(
                        cardOwnerNameEditText,
                        cardOwnerNameErrorTextView,
                        cardOwnerNameTextInputLayout
                );
            }

            if (makePaymentFormState.getSurnameError() != null) {
                showErrorAndChangeColor(
                        makePaymentFormState,
                        cardOwnerSurnameEditText,
                        cardOwnerSurnameErrorTextView,
                        cardOwnerSurnameTextInputLayout
                );
            } else {
                hideErrorAndChangeColor(
                        cardOwnerSurnameEditText,
                        cardOwnerSurnameErrorTextView,
                        cardOwnerSurnameTextInputLayout
                );
            }

            if (makePaymentFormState.getExpirationDateError() != null) {

                showErrorAndChangeColor(
                        makePaymentFormState,
                        expirationDateEditText,
                        expirationDateErrorTextView,
                        expirationDateTextInputLayout
                );
            } else {
                hideErrorAndChangeColor(
                        expirationDateEditText,
                        expirationDateErrorTextView,
                        expirationDateTextInputLayout
                );
            }

            if (makePaymentFormState.getCvvError() != null) {

                showErrorAndChangeColor(
                        makePaymentFormState,
                        cvvEditText,
                        cvvErrorTextView,
                        cvvTextInputLayout
                );
            } else {
                hideErrorAndChangeColor(
                        cvvEditText,
                        cvvErrorTextView,
                        cvvTextInputLayout
                );
            }
        });
    }

    private void hideErrorAndChangeColor(EditText editText, TextView errorTextView, TextInputLayout textInputLayout) {
        errorTextView.setVisibility(View.GONE);
        editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
        textInputLayout.setBoxStrokeColor(ContextCompat.getColor(requireContext(), R.color.green));
    }

    private void showErrorAndChangeColor(MakePaymentFormState formState, EditText editText, TextView errorTextView, TextInputLayout layout) {


        if (errorTextView.equals(cardNumberErrorTextView)) {
            errorTextView.setText(formState.getCardCodeError());
        } else if (errorTextView.equals(cardOwnerNameErrorTextView)) {
            errorTextView.setText(formState.getNameError());
        } else if (errorTextView.equals(cardOwnerSurnameErrorTextView)) {
            errorTextView.setText(formState.getSurnameError());
        } else if (errorTextView.equals(expirationDateErrorTextView)) {
            errorTextView.setText(formState.getExpirationDateError());
        } else if (errorTextView.equals(cvvErrorTextView)) {
            errorTextView.setText(formState.getCvvError());
        }

        errorTextView.setVisibility(View.VISIBLE);
        editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
        layout.setBoxStrokeColor(ContextCompat.getColor(requireContext(), R.color.red));
    }

    private void setupGoToSummaryButton(@NonNull View view) {
        goToSummaryButton = view.findViewById(R.id.buy_button_fragment_summary);
        goToSummaryButton.setOnClickListener(v -> {

            String ownerName = cardOwnerNameEditText.getText().toString();
            String ownerSurname = cardOwnerSurnameEditText.getText().toString();
            String cardNumber = cardNumberEditText.getText().toString();
            String cvv = cvvEditText.getText().toString();
            String expirationDate = expirationDateEditText.getText().toString();
            MakePaymentController.setCreditCard(cardNumber, ownerName, ownerSurname, cvv, expirationDate);

            getParentFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container_make_payment, new SummaryFragment())
                    .commit();
        });
    }

    private void setupExpirationDateEditText(@NonNull View view) {
        expirationDateEditText = view.findViewById(R.id.expiration_date_edit_text_card_attributes);
        expirationDateEditText.addTextChangedListener(new ExpirationDateTextWatcher(expirationDateEditText));
        expirationDateEditText.addTextChangedListener(textWatcher);
    }

    private void setupBottomSheetDialog() {

        cvvBottomSheetDialog = new BottomSheetDialog(requireContext());
        cvvBottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_questionmark_layout);

        TextView questionMark = cvvBottomSheetDialog.findViewById(R.id.question_bottom_sheet_text_view);
        TextView questionMarkExplanation = cvvBottomSheetDialog.findViewById(R.id.question_bottom_sheet_text_view_description);

        questionMark.setText(R.string.cvv_question);
        questionMarkExplanation.setText(R.string.cvv_explanation);


    }


}
