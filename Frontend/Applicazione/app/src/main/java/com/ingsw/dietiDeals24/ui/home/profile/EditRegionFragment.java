package com.ingsw.dietiDeals24.ui.home.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.editProfile.ProfileController;
import com.ingsw.dietiDeals24.model.enumeration.Region;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;

import java.util.Arrays;

public class EditRegionFragment extends FragmentOfHomeActivity {
    private ImageView doneButton;
    private SmartMaterialSpinner<String> regionSmartSpinner;
    private User user = ProfileController.getUser();

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
        int index = Arrays.asList(regions).indexOf(user.getRegion().toString());
        if (index != -1)
            regionSmartSpinner.setSelection(index);
    }

    private void onDoneButtonClick() {
        ProfileController.updateRegion(Region.fromItalianString(regionSmartSpinner.getSelectedItem()));
        goToEditProfileFragment();
    }

    private void goToEditProfileFragment() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new EditProfileFragment()).commit();
    }
}
