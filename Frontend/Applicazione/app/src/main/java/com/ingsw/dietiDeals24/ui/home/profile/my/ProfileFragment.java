package com.ingsw.dietiDeals24.ui.home.profile.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.ProfileController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.login.LoginActivity;
import com.ingsw.dietiDeals24.ui.utility.PopupGeneratorOf;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.ui.utility.recyclerViews.externalLinks.EditableExternalLinksAdapter;
import com.ingsw.dietiDeals24.ui.utility.recyclerViews.externalLinks.ExternalLinksAdapter;
import com.saadahmedsoft.popupdialog.PopupDialog;

import java.util.concurrent.ExecutionException;

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
    private BottomSheetDialog bottomSheetDialog;


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

        userBioTextView.setMovementMethod(new ScrollingMovementMethod());
        showUserData();

        setupBottomSheetDialog();
        andNMoreLinksTextView.setOnClickListener(v -> bottomSheetDialog.show());

        sellerSwitch.setOnClickListener(v -> setupSellerSwitch());
        editProfileButton.setOnClickListener(v -> goToEditProfileFragment());
        logoutButton.setOnClickListener(v -> PopupGeneratorOf.areYouSureToLogoutPopup(getContext()));
    }

    private void setupBottomSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);

        RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.recycler_view_bottom_sheet);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        ExternalLinksAdapter adapter = new ExternalLinksAdapter(UserHolder.user.getExternalLinks());
        recyclerView.setAdapter(adapter);
    }

    /**
     * Puts the user data in the text views of the profile fragment
     */
    private void showUserData() {
        if (UserHolder.user == null)
            return;

        String username = UserHolder.user.getName();
        String userBio = UserHolder.user.getBio();
        String userRegion = " " + UserHolder.user.getRegion().toString() + " ";
        usernameTextView.setText(username);
        userBioTextView.setText(userBio);
        userRegionTextView.setText(userRegion);

        if (UserHolder.user.hasExternalLinks()) {
            String link = UserHolder.user.getExternalLinks().get(0).getTitle();
            String andNMoreLinks = " e altri " + (UserHolder.user.getExternalLinks().size() - 1);
            linkTextView.setText(link);
            andNMoreLinksTextView.setText(andNMoreLinks);
        } else {
            iconLinkImageView.setVisibility(View.GONE);
        }

        if (UserHolder.user.isSeller()) {
            sellerSwitch.performClick();
            startSellerAnimation();
        }
    }

    private void setupSellerSwitch() {
        PopupDialog loading = PopupGeneratorOf.loadingPopup(getContext());
        new Thread(() -> {
            if (sellerSwitch.isChecked()) {
                try {
                    if (!ProfileController.hasBankAccount().get()) {
                        requireActivity().runOnUiThread(this::goToAddBankAccountFragment);
                        return;
                    }
                    ProfileController.switchAccountType().get();
                    requireActivity().runOnUiThread(this::startSellerAnimation);
                } catch (ExecutionException e) {
                    requireActivity().runOnUiThread(() -> {
                        sellerSwitch.setChecked(false);
                        ToastManager.showToast(getContext(), e.getCause().getMessage());
                    });
                } catch (InterruptedException e) {
                    requireActivity().runOnUiThread(() -> {
                        sellerSwitch.setChecked(false);
                        ToastManager.showToast(getContext(), "Operazione interrotta, riprovare");
                    });
                } finally {
                    requireActivity().runOnUiThread(loading::dismissDialog);
                }
            } else {
                try {
                    ProfileController.switchAccountType().get();
                    requireActivity().runOnUiThread(this::startNotSellerAnimation);
                } catch (ExecutionException e) {
                    requireActivity().runOnUiThread(() -> {
                        sellerSwitch.setChecked(true);
                        ToastManager.showToast(getContext(), e.getCause().getMessage());
                    });
                } catch (InterruptedException e) {
                    requireActivity().runOnUiThread(() -> {
                        sellerSwitch.setChecked(true);
                        ToastManager.showToast(getContext(), "Operazione interrotta, riprovare");
                    });
                } finally {
                    requireActivity().runOnUiThread(loading::dismissDialog);
                }
            }
        }).start();
    }


    private void startSellerAnimation() {
        sellerSwitchTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.green));

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

    private void goToAddBankAccountFragment() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new AddBankAccountFragment()).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }
}