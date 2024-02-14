package com.ingsw.dietiDeals24.ui.home.myBids;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;

public class MyBidsFragment extends FragmentOfHomeActivity {

    private MyBidsViewModel mViewModel;

    public static MyBidsFragment newInstance() {
        return new MyBidsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_bids, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(false);

    }

}