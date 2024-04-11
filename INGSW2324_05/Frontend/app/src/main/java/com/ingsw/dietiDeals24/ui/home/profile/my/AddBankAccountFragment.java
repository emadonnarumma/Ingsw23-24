package com.ingsw.dietiDeals24.ui.home.profile.my;

import static java.lang.Thread.sleep;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.ProfileController;
import com.ingsw.dietiDeals24.controller.formstate.BankAccountFormState;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.utility.PopupGenerator;
import com.ingsw.dietiDeals24.utility.ToastManager;
import com.saadahmedsoft.popupdialog.PopupDialog;

import java.util.concurrent.ExecutionException;

public class AddBankAccountFragment extends FragmentOfHomeActivity {
    private TextView titleScreen;
    private TextInputLayout ibanTextInputLayout;
    private EditText ibanEditText;
    private TextView ibanErrorText;

    private TextInputLayout ivaTextInputLayout;
    private EditText ivaEditText;
    private TextView ivaErrorText;
    private ImageView doneButton;

    private TextWatcher bankAccountTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            ProfileController.bankAccountInputChanged(
                    ibanEditText.getText().toString(),
                    ivaEditText.getText().toString(),
                    getResources()
            );
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_bank_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(true);

        titleScreen = view.findViewById(R.id.title_of_edit_bank_account);
        ibanEditText = view.findViewById(R.id.edit_iban_edit_bank_account);
        ivaEditText = view.findViewById(R.id.edit_iva_edit_bank_account);
        doneButton = view.findViewById(R.id.done_button_edit_bank_account);

        doneButton.setEnabled(false);
        doneButton.setColorFilter(getResources().getColor(R.color.gray, null));
        titleScreen.setText(R.string.add_bank_account_phrase);
        ibanEditText.addTextChangedListener(bankAccountTextWatcher);
        ivaEditText.addTextChangedListener(bankAccountTextWatcher);

        setupTextInputLayout(view);
        setupErrorTextViews(view);
        observeFormState();

        doneButton.setOnClickListener(v -> unlockSellerMode());
    }

    private void setupTextInputLayout(View view) {

        ibanTextInputLayout = view.findViewById(R.id.iban_text_layout);
        ivaTextInputLayout = view.findViewById(R.id.iva_text_layout);
    }

    private void setupErrorTextViews(View view) {

        ibanErrorText = view.findViewById(R.id.iban_error_text_view);
        ivaErrorText = view.findViewById(R.id.iva_error_text_view);
    }

    private void hideErrorAndChangeColor(EditText editText, TextView errorTextView, TextInputLayout textInputLayout) {
        errorTextView.setVisibility(View.GONE);
        editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
        textInputLayout.setBoxStrokeColor(ContextCompat.getColor(requireContext(), R.color.green));
    }

    private void showErrorAndChangeColor(BankAccountFormState formState, EditText editText, TextView errorTextView, TextInputLayout layout) {
        if (errorTextView.equals(ibanErrorText)) {
            errorTextView.setText(formState.getIbanError());
        } else if (errorTextView.equals(ivaErrorText)) {
            errorTextView.setText(formState.getIvaError());
        }

        errorTextView.setVisibility(View.VISIBLE);
        editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
        layout.setBoxStrokeColor(ContextCompat.getColor(requireContext(), R.color.red));
    }

    private void observeFormState() {
        ProfileController.getBankAccountFormState().observe(getViewLifecycleOwner(), formState -> {
            if (formState == null) {
                return;
            }
            doneButton.setEnabled(formState.isDataValid());
            if (doneButton.isEnabled()) {
                doneButton.setColorFilter(getResources().getColor(R.color.green, null));
            } else {
                doneButton.setColorFilter(getResources().getColor(R.color.gray, null));
            }

            if (formState.getIbanError() != null) {
                showErrorAndChangeColor(
                        formState,
                        ibanEditText,
                        ibanErrorText,
                        ibanTextInputLayout
                );
            } else {
                hideErrorAndChangeColor(
                        ibanEditText,
                        ibanErrorText,
                        ibanTextInputLayout
                );
            }
            if (formState.getIvaError() != null) {
                showErrorAndChangeColor(
                        formState,
                        ivaEditText,
                        ivaErrorText,
                        ivaTextInputLayout
                );
            } else {
                hideErrorAndChangeColor(
                        ivaEditText,
                        ivaErrorText,
                        ivaTextInputLayout
                );
            }
        });
    }


    private void unlockSellerMode() {
        PopupDialog loading = PopupGenerator.loadingPopup(getContext());
        new Thread(() -> {
            try {
                ProfileController.addSellerAccount().get();
                sleep(500);
                ProfileController.addBankAccount(
                        ibanEditText.getText().toString(),
                        ivaEditText.getText().toString()
                ).get();
                sleep(500);
                requireActivity().runOnUiThread(() -> {
                    PopupGenerator.successPopup(getContext(), getString(R.string.seller_mode_unlocked));
                    goToProfileFragment();
                });
            } catch (InterruptedException e) {
                requireActivity().runOnUiThread(() -> ToastManager.showToast(getContext(), "Operazione interrotta, riprovare"));
            } catch (ExecutionException e) {
                requireActivity().runOnUiThread(() -> ToastManager.showToast(getContext(), e.getCause().getMessage()));
            } finally {
                requireActivity().runOnUiThread(loading::dismissDialog);
            }
        }).start();
    }

    private void goToProfileFragment() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new ProfileFragment()).commit();
    }
}
