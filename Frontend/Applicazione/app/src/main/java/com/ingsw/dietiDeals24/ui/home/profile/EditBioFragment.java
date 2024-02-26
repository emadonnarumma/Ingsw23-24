package com.ingsw.dietiDeals24.ui.home.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.ProfileController;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;

public class EditBioFragment extends FragmentOfHomeActivity {
    private ImageView doneButton;
    private TextInputEditText bioEditText;
    private User user = ProfileController.getUser();

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

        bioEditText.setText(user.getBio());
        doneButton.setOnClickListener(v -> onDoneButtonClick());
    }

    private void onDoneButtonClick() {
        if(bioEditText.getText() == null)
            ProfileController.updateBio("");
        else
            ProfileController.updateBio(bioEditText.getText().toString());
        goToEditProfileFragment();
    }

    private void goToEditProfileFragment() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new EditProfileFragment()).commit();
    }
}
