package com.ingsw.dietiDeals24.ui.home.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.ProfileController;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;

public class EditProfileFragment extends FragmentOfHomeActivity {
    private ConstraintLayout editRegionButton;
    private ConstraintLayout editBioButton;
    private ConstraintLayout editExternalLinksButton;
    private ConstraintLayout editBankAccountButton;
    private ConstraintLayout unlockSellerModeButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(true);

        editRegionButton = view.findViewById(R.id.edit_region_button_edit_profile);
        editBioButton = view.findViewById(R.id.edit_bio_button_edit_profile);
        editExternalLinksButton = view.findViewById(R.id.edit_external_links_button_edit_profile);
        editBankAccountButton = view.findViewById(R.id.edit_bank_account_button_edit_profile);
        unlockSellerModeButton = view.findViewById(R.id.unlock_seller_mode_button_edit_profile);

        if(ProfileController.getUser().isSeller()) {
            unlockSellerModeButton.setVisibility(View.GONE);
            editBankAccountButton.setVisibility(View.VISIBLE);
        } else {
            editBankAccountButton.setVisibility(View.GONE);
            unlockSellerModeButton.setVisibility(View.VISIBLE);
        }

        editRegionButton.setOnClickListener(v -> goToEditRegion());
        editBioButton.setOnClickListener(v -> goToEditBio());
        editExternalLinksButton.setOnClickListener(v -> goToEditExternalLinks());
        editBankAccountButton.setOnClickListener(v -> goToEditBankAccount());
        unlockSellerModeButton.setOnClickListener(v -> onUnlockSellerModeClick());
    }

    private void goToEditRegion() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new EditRegionFragment()).commit();
    }

    private void goToEditBio() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new EditBioFragment()).commit();
    }

    private void goToEditExternalLinks() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new EditExternalLinksFragment()).commit();
    }

    private void goToEditBankAccount() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new EditBankAccountFragment()).commit();
    }

    private void onUnlockSellerModeClick() {
        //TODO:
    }
}
