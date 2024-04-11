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
import com.ingsw.dietiDeals24.controller.formstate.ExternalLinkFormState;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.utility.PopupGenerator;
import com.ingsw.dietiDeals24.utility.ToastManager;
import com.saadahmedsoft.popupdialog.PopupDialog;

import java.util.concurrent.ExecutionException;

public class EditExternalLinkFragment extends FragmentOfHomeActivity {
    private TextView titleScreen;

    private TextInputLayout titleTextInputLayout;
    private EditText titleEditText;
    private TextView titleErrorText;
    private TextInputLayout urlTextInputLayout;
    private EditText urlEditText;
    private TextView urlErrorText;
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

        initEditTexts();
        titleScreen.setText(R.string.edit_external_link_phrase);
        titleEditText.addTextChangedListener(externalLinkTextWatcher);
        urlEditText.addTextChangedListener(externalLinkTextWatcher);

        setupTextInputLayout(view);
        setupErrorTextViews(view);
        observeFormState();

        doneButton.setOnClickListener(v -> onDoneButtonClick());
    }

    private void setupTextInputLayout(View view) {

        titleTextInputLayout = view.findViewById(R.id.external_link_title_text_layout);
        urlTextInputLayout = view.findViewById(R.id.external_link_url_text_layout);
    }

    private void setupErrorTextViews(View view) {

        titleErrorText = view.findViewById(R.id.external_link_title_error_text_view);
        urlErrorText = view.findViewById(R.id.external_link_url_error_text_view);
    }

    private void hideErrorAndChangeColor(EditText editText, TextView errorTextView, TextInputLayout textInputLayout) {
        errorTextView.setVisibility(View.GONE);
        editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
        textInputLayout.setBoxStrokeColor(ContextCompat.getColor(requireContext(), R.color.green));
    }

    private void showErrorAndChangeColor(ExternalLinkFormState formState, EditText editText, TextView errorTextView, TextInputLayout layout) {
        if (errorTextView.equals(titleErrorText)) {
            errorTextView.setText(formState.getTitleError());
        } else if (errorTextView.equals(urlErrorText)) {
            errorTextView.setText(formState.getUrlError());
        }

        errorTextView.setVisibility(View.VISIBLE);
        editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
        layout.setBoxStrokeColor(ContextCompat.getColor(requireContext(), R.color.red));
    }

    private void observeFormState() {
        ProfileController.getExternalLinkFormState().observe(getViewLifecycleOwner(), formState -> {
            if (formState == null) {
                return;
            }

            doneButton.setEnabled(formState.isDataValid());
            if (doneButton.isEnabled()) {
                doneButton.setColorFilter(getResources().getColor(R.color.green, null));
            } else {
                doneButton.setColorFilter(getResources().getColor(R.color.gray, null));
            }

            if (formState.getTitleError() != null) {
                showErrorAndChangeColor(
                        formState,
                        titleEditText,
                        titleErrorText,
                        titleTextInputLayout
                );
            } else {
                hideErrorAndChangeColor(
                        titleEditText,
                        titleErrorText,
                        titleTextInputLayout
                );
            }
            if (formState.getUrlError() != null) {
                showErrorAndChangeColor(
                        formState,
                        urlEditText,
                        urlErrorText,
                        urlTextInputLayout
                );
            } else {
                hideErrorAndChangeColor(
                        urlEditText,
                        urlErrorText,
                        urlTextInputLayout
                );
            }
        });
    }

    private void initEditTexts() {
        titleEditText.setText(ProfileController.getSelectedLink().getTitle());
        urlEditText.setText(ProfileController.getSelectedLink().getUrl());
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
        PopupDialog loading = PopupGenerator.loadingPopup(getContext());
        new Thread(() -> {
            changeLinkThread(loading);
        }).start();
    }

    private void changeLinkThread(PopupDialog loading) {
        try {
            if(isExternalLinkChanged()) {
                ProfileController.updateLink(
                        titleEditText.getText().toString(),
                        urlEditText.getText().toString()
                ).get();
                sleep(500);
                requireActivity().runOnUiThread(() -> ToastManager.showToast(getContext(), R.string.link_updated));
            }
            requireActivity().runOnUiThread(() -> goToEditExternalLinksFragment());
        } catch (InterruptedException e) {
            requireActivity().runOnUiThread(() -> ToastManager.showToast(getContext(), "Operazione interrotta, riprovare"));
        } catch (ExecutionException e) {
            requireActivity().runOnUiThread(() -> ToastManager.showToast(getContext(), e.getCause().getMessage()));
        } finally {
            requireActivity().runOnUiThread(() -> loading.dismissDialog());
        }
    }

    private void goToEditExternalLinksFragment() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new EditExternalLinksFragment()).commit();
    }

    private boolean isExternalLinkChanged() {
        return !titleEditText.getText().toString().equals(ProfileController.getSelectedLink().getTitle()) ||
                !urlEditText.getText().toString().equals(ProfileController.getSelectedLink().getUrl());
    }
}
