package com.ingsw.dietiDeals24.ui.home.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.editProfile.ProfileController;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.login.LoginActivity;

public class ProfileFragment extends FragmentOfHomeActivity {
    private TextView usernameTextView;
    private Switch sellerSwitch;
    private TextView sellerSwitchTextView;
    private TextView userBioTextView;
    private ImageView iconLinkImageView;
    private TextView linkTextView;
    private TextView andNMoreLinksTextView;
    private TextView userRegionTextView;
    private CircularProgressButton editProfileButton;
    private CircularProgressButton logoutButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(false);

        usernameTextView = view.findViewById(R.id.username_text_profile);
        sellerSwitch = view.findViewById(R.id.seller_switch_profile);
        sellerSwitchTextView = view.findViewById(R.id.seller_switch_text_profile);
        userBioTextView = view.findViewById(R.id.user_bio_profile);
        iconLinkImageView = view.findViewById(R.id.icon_link_profile);
        linkTextView = view.findViewById(R.id.link_profile);
        andNMoreLinksTextView = view.findViewById(R.id.and_n_more_links_profile);
        userRegionTextView = view.findViewById(R.id.user_region_profile);
        editProfileButton = view.findViewById(R.id.edit_profile_button_profile);
        logoutButton = view.findViewById(R.id.logout_button_profile);

        showUserData();
        putAnimationOnSellerSwitch();

        editProfileButton.setOnClickListener(v -> goToEditProfileFragment());
        logoutButton.setOnClickListener(v -> logout());

    }

    /**
     * Puts the user data in the text views of the profile fragment
     */
    private void showUserData() {
        if (ProfileController.getUser() == null)
            return;

        String username = ProfileController.getUser().getName();
        String userBio = ProfileController.getUser().getBio();
        String userRegion = " " + ProfileController.getUser().getRegion().toString() + " ";
        usernameTextView.setText(username);
        userBioTextView.setText(userBio);
        userRegionTextView.setText(userRegion);

        if(ProfileController.getUser().hasExternalLinks()) {
            String link = ProfileController.getUser().getExternalLinks().get(0).getTitle();
            String andNMoreLinks = "and " + (ProfileController.getUser().getExternalLinks().size() - 1) + " more";
            linkTextView.setText(link);
            andNMoreLinksTextView.setText(andNMoreLinks);
        } else {
            iconLinkImageView.setVisibility(View.GONE);
        }

        if (ProfileController.getUser().isSeller()) {
            sellerSwitch.performClick();
            startSellerAnimation();
        }
    }

    private void putAnimationOnSellerSwitch() {
        sellerSwitch.setOnClickListener(v -> {
            if (sellerSwitch.isChecked())
                startSellerAnimation();
            else
                startNotSellerAnimation();
        });
    }

    private void startSellerAnimation() {
        sellerSwitchTextView.setTextColor(getResources().getColor(R.color.green));

        // Load the animation from the XML file
        Animation animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.switch_button_on);

        // Start the animation on the TextView
        sellerSwitchTextView.startAnimation(animation);
    }

    private void startNotSellerAnimation() {
        sellerSwitchTextView.setTextColor(getResources().getColor(R.color.gray));

        // Load the animation from the XML file
        Animation animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.switch_button_off);

        // Start the animation on the TextView
        sellerSwitchTextView.startAnimation(animation);
    }

    private void goToEditProfileFragment() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new EditProfileFragment()).commit();
    }

    private void logout() {
        ProfileController.logout();
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        startActivity(intent);
    }
}