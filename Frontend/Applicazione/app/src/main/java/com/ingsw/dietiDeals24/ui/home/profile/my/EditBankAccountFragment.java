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
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.ProfileController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.utility.PopupGeneratorOf;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.saadahmedsoft.popupdialog.PopupDialog;

import java.util.concurrent.ExecutionException;

public class EditBankAccountFragment extends FragmentOfHomeActivity {
    private TextView titleScreen;
    private EditText ibanEditText;
    private EditText ivaEditText;
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

        initEditTexts();
        titleScreen.setText(R.string.edit_bank_account_phrase);
        ibanEditText.addTextChangedListener(bankAccountTextWatcher);
        ivaEditText.addTextChangedListener(bankAccountTextWatcher);
        observeBankAccountFormState();

        doneButton.setOnClickListener(v -> onDoneButtonClick());
    }

    private void initEditTexts() {
        ibanEditText.setText(UserHolder.getSeller().getBankAccount().getIban());
        ivaEditText.setText(UserHolder.getSeller().getBankAccount().getIva());
    }

    private void observeBankAccountFormState() {
        ProfileController.getBankAccountFormState().observe(getViewLifecycleOwner(), bankAccountFormState -> {
            if (bankAccountFormState == null) {
                return;
            }
            String ibanError = bankAccountFormState.getIbanError();
            String ivaError = bankAccountFormState.getIvaError();
            if (ibanError != null) {
                ibanEditText.setError(ibanError);
            }
            if (ivaError != null) {
                ivaEditText.setError(ivaError);
            }
            doneButton.setEnabled(bankAccountFormState.isDataValid());
            if (doneButton.isEnabled()) {
                doneButton.setColorFilter(getResources().getColor(R.color.green, null));
            } else {
                doneButton.setColorFilter(getResources().getColor(R.color.gray, null));
            }
        });
    }

    private void onDoneButtonClick() {
        PopupDialog loading = PopupGeneratorOf.loadingPopup(getContext());
        new Thread(() -> {
            try {
                if(isBankAccountChanged()) {
                    ProfileController.updateBankAccount(
                            ibanEditText.getText().toString(),
                            ivaEditText.getText().toString()
                    ).get();
                    sleep(500);
                    requireActivity().runOnUiThread(() -> ToastManager.showToast(getContext(), R.string.bank_account_updated));
                }
                requireActivity().runOnUiThread(this::goToProfileFragment);
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

    private boolean isBankAccountChanged() {
        String userIban = UserHolder.getSeller().getBankAccount().getIban().replaceAll("\\s", "");
        String newIban = ibanEditText.getText().toString().replaceAll("\\s", "");

        String userIva = UserHolder.getSeller().getBankAccount().getIva();
        String newIva = ivaEditText.getText().toString();

        return !newIban.equals(userIban) || !newIva.equals(userIva);
    }
}