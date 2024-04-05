package com.ingsw.dietiDeals24.ui.home.profile.my;

import static java.lang.Thread.sleep;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.ProfileController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.model.enumeration.Region;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.utility.PopupGeneratorOf;
import com.ingsw.dietiDeals24.utility.ToastManager;
import com.saadahmedsoft.popupdialog.PopupDialog;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class EditRegionFragment extends FragmentOfHomeActivity {
    private ImageView doneButton;
    private SmartMaterialSpinner<String> regionSmartSpinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_region, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(true);

        doneButton = view.findViewById(R.id.done_button_edit_region);
        regionSmartSpinner = view.findViewById(R.id.region_spinner_edit_region);

        initRegionSmartSpinner();
        putUserRegionOnSpinner();
        doneButton.setOnClickListener(v -> onDoneButtonClick());

    }

    private void initRegionSmartSpinner() {
        regionSmartSpinner.setItem(
                Arrays.asList(
                        getResources().getStringArray(R.array.italian_regions)
                )
        );
    }

    private void putUserRegionOnSpinner() {
        String[] regions = getResources().getStringArray(R.array.italian_regions);
        int index = Arrays.asList(regions).indexOf(UserHolder.user.getRegion().toString());
        if (index != -1)
            regionSmartSpinner.setSelection(index);
    }

    private void onDoneButtonClick() {
        PopupDialog loading = PopupGeneratorOf.loadingPopup(getContext());
        new Thread(() -> {
            try {
                if(regionSmartSpinner.getSelectedItem() != null) {
                    if(isRegionChanged()) {
                        ProfileController.updateRegion(Region.fromItalianString(regionSmartSpinner.getSelectedItem())).get();
                        sleep(500);
                        requireActivity().runOnUiThread(() -> ToastManager.showToast(getContext(), R.string.region_updated));
                    }
                }
                requireActivity().runOnUiThread(this::goToProfileFragment);
            } catch (InterruptedException e) {
                requireActivity().runOnUiThread(() -> ToastManager.showToast(getContext(), "Operazione interrotta"));
            } catch (ExecutionException e) {
                requireActivity().runOnUiThread(() -> ToastManager.showToast(getContext(), e.getCause().getMessage()));
            } finally {
                requireActivity().runOnUiThread(loading::dismissDialog);
            }
        }).start();
    }

    private boolean isRegionChanged() {
        return !Region.fromItalianString(regionSmartSpinner.getSelectedItem()).equals(UserHolder.user.getRegion());
    }

    private void goToProfileFragment() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new ProfileFragment()).commit();
    }
}
