package com.ingsw.dietiDeals24.activity.ui.registration.optionalInfo;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ingsw.dietiDeals24.R;

public class RegistrationOptionalInfoFragment extends Fragment {

    public static RegistrationOptionalInfoFragment newInstance() {
        return new RegistrationOptionalInfoFragment();
    }

    private RegistrationOptionalInfoViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration_optional_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegistrationOptionalInfoViewModel.class);
        // TODO: Use the ViewModel
    }

}