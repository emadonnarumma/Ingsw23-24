package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.userTypeAuctionAttributes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes.ReverseAuctionAttributesFragment;

public class BuyerAuctionTypesFragment extends FragmentOfHomeActivity {

    private ImageView reverseAuctionButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buyer_auction_types, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(true);

        setupReverseButton(view);
    }

    private void setupReverseButton(@NonNull View view) {
        reverseAuctionButton = view.findViewById(R.id.reverse_auction_type_button_buyer_auction_types);
        reverseAuctionButton.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_home, new ReverseAuctionAttributesFragment())
                    .commit();
        });
    }
}