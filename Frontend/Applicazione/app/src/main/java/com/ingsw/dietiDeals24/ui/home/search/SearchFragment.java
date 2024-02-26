package com.ingsw.dietiDeals24.ui.home.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;

import java.util.Arrays;

public class SearchFragment extends FragmentOfHomeActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private SmartMaterialSpinner<String> categorySmartSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(com.ingsw.dietiDeals24.R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(false);

        recyclerView = view.findViewById(R.id.auctions_search);
        progressBar = view.findViewById(R.id.progress_bar_search_auctions);
        setupCategorySpinner(view);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAuctions();
    }

    private void updateAuctions() {

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void setupCategorySpinner(View view) {
        categorySmartSpinner = view.findViewById(R.id.filter_button_search);
        categorySmartSpinner.setItem(
                Arrays.asList(
                        getResources().getStringArray(R.array.categories)
                )
        );
    }
}