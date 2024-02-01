package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.userTypeAuctionAttributes;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.enumeration.Category;
import com.ingsw.dietiDeals24.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes.ReverseAuctionAttributesFragment;

import java.util.ArrayList;

public class BuyerAuctionTypesFragment extends Fragment {

    private ImageView reverseButton;
    private Bundle bundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buyer_auction_types, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupReverseButton(view);
        if (getActivity() instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    private void setupReverseButton(@NonNull View view) {
        reverseButton = view.findViewById(R.id.reverse_auction_type_button_buyer_auction_types);

        reverseButton.setOnClickListener(v -> {
            bundle.putCharSequence("type", "Reverse Auction");

            ReverseAuctionAttributesFragment fragment = new ReverseAuctionAttributesFragment();
            fragment.setArguments(bundle);

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_home, fragment)
                    .commit();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        feedTheCollector();
    }

    private void feedTheCollector() {
        reverseButton = null;
        bundle = null;
    }
}