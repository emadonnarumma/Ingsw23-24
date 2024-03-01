package com.ingsw.dietiDeals24.ui.home.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.ProfileController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;

import java.util.Objects;

public class EditBioFragment extends FragmentOfHomeActivity {
    private ImageView doneButton;
    private TextInputEditText bioEditText;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_bio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(true);

        doneButton = view.findViewById(R.id.done_button_edit_bio);
        bioEditText = view.findViewById(R.id.bio_edit_text_edit_bio);
        progressBar = view.findViewById(R.id.progress_bar_edit_bio);

        bioEditText.setText(UserHolder.user.getBio());
        doneButton.setOnClickListener(v -> onDoneButtonClick());
    }

    private void onDoneButtonClick() {
        progressBar.setVisibility(View.VISIBLE);
        try {
            if(bioEditText.getText() == null)
                return;
            ProfileController.updateBio(bioEditText.getText().toString());
            ToastManager.showToast(getContext(), R.string.bio_updated);
            goToEditProfileFragment();
        } catch (ConnectionException e) {
            ToastManager.showToast(getContext(), e.getMessage());
        } finally {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void goToEditProfileFragment() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new EditProfileFragment()).commit();
    }
}
