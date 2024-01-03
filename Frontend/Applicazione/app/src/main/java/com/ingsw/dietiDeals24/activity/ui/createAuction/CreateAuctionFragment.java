package com.ingsw.dietiDeals24.activity.ui.createAuction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ingsw.dietiDeals24.databinding.FragmentCreateAuctionBinding;


public class CreateAuctionFragment extends Fragment {

    private FragmentCreateAuctionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CreateAuctionViewModel createAuctionViewModel =
                new ViewModelProvider(this).get(CreateAuctionViewModel.class);

        binding = FragmentCreateAuctionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCreateAuction;
        createAuctionViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}