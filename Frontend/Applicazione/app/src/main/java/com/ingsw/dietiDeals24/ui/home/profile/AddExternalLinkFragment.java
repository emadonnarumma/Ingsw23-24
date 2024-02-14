package com.ingsw.dietiDeals24.ui.home.profile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.editProfile.ProfileController;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;

public class AddExternalLinkFragment extends FragmentOfHomeActivity {
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
            ProfileController.dataChanged(
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

        titleEditText = view.findViewById(R.id.edit_title_edit_external_link);
        urlEditText = view.findViewById(R.id.edit_url_edit_external_link);
        doneButton = view.findViewById(R.id.doneButton);

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
        });
    }

    private void onDoneButtonClick() {
        ProfileController.addLink(
                titleEditText.getText().toString(),
                urlEditText.getText().toString()
        );
        ToastManager.showToast(getContext(), getString(R.string.link_added));
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new EditExternalLinksFragment()).commit();
    }
}