package com.ingsw.dietiDeals24.activity.ui.home.myAuctions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ingsw.dietiDeals24.databinding.FragmentMyAuctionsBinding;


public class MyAuctionsFragment extends Fragment {

    private FragmentMyAuctionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyAuctionViewModel createAuctionViewModel =
                new ViewModelProvider(this).get(MyAuctionViewModel.class);

        binding = FragmentMyAuctionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMyAuction;
        createAuctionViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}