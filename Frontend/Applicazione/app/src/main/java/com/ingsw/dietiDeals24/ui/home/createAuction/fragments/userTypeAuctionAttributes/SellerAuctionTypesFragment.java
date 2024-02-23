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
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes.DownwardAuctionAttributesFragment;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes.SilentAuctionAttributesFragment;

public class SellerAuctionTypesFragment extends FragmentOfHomeActivity {

    private ImageView silentAuctionButton, downwardAuctionButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seller_auction_types, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(true);

        setupButtons(view);
    }

    private void setupButtons(View view) {
        setupDownwardAuctionButton(view);
        setupSilentAuctionButton(view);
    }

    private void setupSilentAuctionButton(View view) {
        silentAuctionButton = view.findViewById(R.id.silent_auction_type_button_seller_auction_types);
        silentAuctionButton.setOnClickListener(v ->
                getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_home, new SilentAuctionAttributesFragment())
                .commit()
        );
    }

    private void setupDownwardAuctionButton(View view) {
        downwardAuctionButton = view.findViewById(R.id.downaward_auction_type_button_seller_auction_types);
        downwardAuctionButton.setOnClickListener(v ->
                getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_home, new DownwardAuctionAttributesFragment())
                .commit()
        );
    }
}