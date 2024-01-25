package com.ingsw.dietiDeals24.ui.home.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ingsw.dietiDeals24.R;

public class ProfileFragment extends Fragment {
    private Switch sellerSwitch;
    private TextView sellerSwitchTextView;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sellerSwitch = view.findViewById(R.id.seller_switch_profile);
        sellerSwitchTextView = view.findViewById(R.id.seller_switch_text_profile);

        if(sellerSwitch.isChecked()) {
            sellerSwitchTextView.setTranslationX(16);
            sellerSwitchTextView.setTextColor(getResources().getColor(R.color.green));
        } else {
            sellerSwitchTextView.setTranslationX(8);
            sellerSwitchTextView.setTextColor(getResources().getColor(R.color.grey));
        }

        sellerSwitch.setOnClickListener(v -> {
            if (sellerSwitch.isChecked()) {
                sellerSwitchTextView.setTextColor(getResources().getColor(R.color.green));

                // Load the animation from the XML file
                Animation animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.switch_button_on);

                // Start the animation on the TextView
                sellerSwitchTextView.startAnimation(animation);
            } else {
                sellerSwitchTextView.setTextColor(getResources().getColor(R.color.grey));

                // Load the animation from the XML file
                Animation animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.switch_button_off);

                // Start the animation on the TextView
                sellerSwitchTextView.startAnimation(animation);
            }
        });
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}