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
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.utility.PopupGeneratorOf;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.saadahmedsoft.popupdialog.PopupDialog;

import java.util.concurrent.ExecutionException;

public class AddExternalLinkFragment extends FragmentOfHomeActivity {
    private TextView titleScreen;
    private EditText titleEditText;
    private EditText urlEditText;
    private ImageView doneButton;

    private TextWatcher externalLinkTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            ProfileController.externalLinkInputChanged(
                    titleEditText.getText().toString(),
                    urlEditText.getText().toString(),
                    getResources()
            );
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_external_link, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(true);

        titleScreen = view.findViewById(R.id.title_of_edit_external_link);
        titleEditText = view.findViewById(R.id.edit_title_edit_external_link);
        urlEditText = view.findViewById(R.id.edit_url_edit_external_link);
        doneButton = view.findViewById(R.id.done_button_edit_external_link);

        doneButton.setEnabled(false);
        doneButton.setColorFilter(getResources().getColor(R.color.gray, null));
        titleScreen.setText(R.string.add_external_link_phrase);
        titleEditText.addTextChangedListener(externalLinkTextWatcher);
        urlEditText.addTextChangedListener(externalLinkTextWatcher);
        observeExternalLinkFormState();

        doneButton.setOnClickListener(v -> onDoneButtonClick());
    }

    private void observeExternalLinkFormState() {
        ProfileController.getExternalLinkFormState().observe(getViewLifecycleOwner(), externalLinkFormState -> {
            if (externalLinkFormState == null) {
                return;
            }
            String titleError = externalLinkFormState.getTitleError();
            String urlError = externalLinkFormState.getUrlError();
            if (titleError != null) {
                titleEditText.setError(titleError);
            }
            if (urlError != null) {
                urlEditText.setError(urlError);
            }
            doneButton.setEnabled(externalLinkFormState.isDataValid());
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
                ProfileController.addLink(
                        titleEditText.getText().toString(),
                        urlEditText.getText().toString()
                ).get();
                sleep(500);
                requireActivity().runOnUiThread(() -> {
                    ToastManager.showToast(getContext(), R.string.link_added);
                    goToEditExternalLinksFragment();
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

    private void goToEditExternalLinksFragment() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new EditExternalLinksFragment()).commit();
    }
}