package com.ingsw.dietiDeals24.ui.home.profile.my;

import static java.lang.Thread.sleep;

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
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.utility.PopupGeneratorOf;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.saadahmedsoft.popupdialog.PopupDialog;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class EditBioFragment extends FragmentOfHomeActivity {
    private ImageView doneButton;
    private TextInputEditText bioEditText;

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

        bioEditText.setText(UserHolder.user.getBio());
        doneButton.setOnClickListener(v -> onDoneButtonClick());
    }

    private void onDoneButtonClick() {
        PopupDialog loading = PopupGeneratorOf.loadingPopup(getContext());
        new Thread(() -> {
            try {
                if(isBioChanged()) {
                    if(bioEditText.getText() == null)
                        return;
                    ProfileController.updateBio(bioEditText.getText().toString()).get();
                    sleep(500);
                    requireActivity().runOnUiThread(() -> ToastManager.showToast(getContext(), R.string.bio_updated));
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

    private boolean isBioChanged() {
        if (bioEditText.getText() == null)
            return UserHolder.user.getBio() != null && !UserHolder.user.getBio().isEmpty();
        return !Objects.equals(bioEditText.getText().toString(), UserHolder.user.getBio());
    }
}
