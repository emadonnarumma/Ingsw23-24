package com.ingsw.dietiDeals24.ui.home.profile;

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
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;

public class EditExternalLinkFragment extends FragmentOfHomeActivity {
    private TextView titleScreen;
    private EditText titleEditText;
    private EditText urlEditText;
    private ImageView doneButton;
    private ProgressBar progressBar;

    private TextWatcher externalLinkTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            ProfileController.externalLinkDataChanged(
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
        progressBar = view.findViewById(R.id.progress_bar_edit_external_link);

        initEditTexts();
        titleScreen.setText(R.string.edit_external_link_phrase);
        titleEditText.addTextChangedListener(externalLinkTextWatcher);
        urlEditText.addTextChangedListener(externalLinkTextWatcher);
        observeExternalLinkFormState();

        doneButton.setOnClickListener(v -> onDoneButtonClick());
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
        });
    }

    private void onDoneButtonClick() {
        progressBar.setVisibility(View.VISIBLE);
        try {
            ProfileController.updateLink(
                    titleEditText.getText().toString(),
                    urlEditText.getText().toString()
            );
            ToastManager.showToast(getContext(), R.string.link_added);
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                    new EditExternalLinksFragment()).commit();
        } catch (ConnectionException e) {
            ToastManager.showToast(getContext(), e.getMessage());
        } finally {
            progressBar.setVisibility(View.GONE);
        }
    }
}
