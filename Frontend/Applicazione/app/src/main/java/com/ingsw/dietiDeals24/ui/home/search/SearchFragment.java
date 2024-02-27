package com.ingsw.dietiDeals24.ui.home.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.SearchAuctionsController;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.ui.utility.recyclerViews.searchAuctions.SearchAuctionAdapter;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchFragment extends FragmentOfHomeActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private SmartMaterialSpinner<String> categorySmartSpinner;

    private MaterialSearchBar searchBar;

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
        searchBar = view.findViewById(R.id.search_bar);

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
        new Thread(() -> {
            try {
                List<SilentAuction> silentAuctions = SearchAuctionsController.getAllSilentAuctions().get();
                List<DownwardAuction> downwardAuctions = SearchAuctionsController.getAllDownwardAuctions().get();
                List<ReverseAuction> reverseAuctions = SearchAuctionsController.getAllReverseAuctions().get();

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        SearchAuctionAdapter adapter = new SearchAuctionAdapter(silentAuctions, downwardAuctions, reverseAuctions);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    });
                }
            } catch (ExecutionException e) {
                requireActivity().runOnUiThread(() -> {
                    recyclerView.setAdapter(new SearchAuctionAdapter(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
                    ToastManager.showToast(getContext(), e.getCause().getMessage());
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
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