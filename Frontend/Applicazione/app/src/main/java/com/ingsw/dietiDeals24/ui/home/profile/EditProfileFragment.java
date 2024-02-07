package com.ingsw.dietiDeals24.ui.home.profile;

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

public class EditProfileFragment extends Fragment {
    private ConstraintLayout editRegionButton;
    private ConstraintLayout editBioButton;
    private ConstraintLayout editExternalLinksButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setBackButtonEnabled(false);
    }

    private void setBackButtonEnabled(boolean enabled) {
        if (getActivity() instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(enabled);
            }
        }
    }

    public void onEditRegionClick(View view) {
        //TODO
    }
}
