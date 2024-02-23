package com.ingsw.dietiDeals24.ui.home.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;

public class SearchFragment extends FragmentOfHomeActivity {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(com.ingsw.dietiDeals24.R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(false);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}