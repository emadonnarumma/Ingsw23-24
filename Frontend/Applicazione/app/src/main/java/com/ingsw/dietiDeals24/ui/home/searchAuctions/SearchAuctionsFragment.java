package com.ingsw.dietiDeals24.ui.home.searchAuctions;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.SearchAuctionsController;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.ui.utility.recyclerViews.searchAuctions.SearchAuctionAdapter;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchAuctionsFragment extends FragmentOfHomeActivity {
    private SearchAuctionsViewModel viewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SmartMaterialSpinner<String> categorySmartSpinner;
    private SwipeRefreshLayout auctionsSwipeRefreshLayout;
    private MaterialSearchBar searchBar;

    private ExecutorService executorService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            int spinnerIndex = savedInstanceState.getInt("CATEGORY_SPINNER_INDEX");
            categorySmartSpinner.setSelection(spinnerIndex);
            searchBar.setText(savedInstanceState.getString("SEARCH_BAR"));
        }

        viewModel = SearchAuctionsViewModel.getInstance();

        executorService = Executors.newSingleThreadExecutor();
        return inflater.inflate(com.ingsw.dietiDeals24.R.layout.fragment_search_auctions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(false);
        recyclerView = view.findViewById(R.id.recycler_view_fragment_search_auctions);
        progressBar = view.findViewById(R.id.progress_bar_search_auctions);
        setupAuctionsSwipeRefreshLayout(view);
        setupSearchBar(view);
        setupCategorySpinner(view);
    }

    private void setupAuctionsSwipeRefreshLayout(@NonNull View view) {
        auctionsSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_fragment_search_auctions);
        auctionsSwipeRefreshLayout.setOnRefreshListener(() -> {
            auctionsSwipeRefreshLayout.setRefreshing(false);
            SearchAuctionsController.setUpdatedAll(false);
            String keyword = searchBar.getText().toString();
            if (keyword.isEmpty()) {

                if (categorySmartSpinner.getSelectedItem() == null) {
                    updateAuctions();
                } else {

                    filterByCategory(Category.fromItalianString(categorySmartSpinner.getSelectedItem()));
                }

            } else {

                if (categorySmartSpinner.getSelectedItem() == null) {
                    filterByKeyword(keyword);
                } else {
                    filterByKeywordAndCategory(keyword, Category.fromItalianString(categorySmartSpinner.getSelectedItem()));
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        categorySmartSpinner.setSelected(false);
        searchBar.setText("");
        restoreData();
        updateAuctions();
    }

    private void restoreData() {
        viewModel.getSpinnerIndex().observe(getViewLifecycleOwner(), index -> {
            if (index != null) {
                categorySmartSpinner.setSelection(index);
            }
        });
    }

    private void updateAuctions() {
        if (!executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        executorService = Executors.newSingleThreadExecutor();
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        executorService.submit(() -> {
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
        });
    }

    private void filterByCategory(Category category) {

        if (!executorService.isShutdown()) {
            executorService.shutdownNow();
        }

        executorService = Executors.newSingleThreadExecutor();

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        executorService.submit(() -> {

            try {
                List<SilentAuction> silentAuctions = SearchAuctionsController.getAllSilentAuctionsByCategory(category).get();
                List<DownwardAuction> downwardAuctions = SearchAuctionsController.getAllDownwardAuctionsByCategory(category).get();
                List<ReverseAuction> reverseAuctions = SearchAuctionsController.getAllReverseAuctionsByCategory(category).get();

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        SearchAuctionAdapter adapter = new SearchAuctionAdapter(silentAuctions, downwardAuctions, reverseAuctions);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE
                        );
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
        });
    }

    private void filterByKeyword(String keyword) {
        if (!executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        executorService = Executors.newSingleThreadExecutor();
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        executorService.submit(() -> {
            try {
                List<SilentAuction> silentAuctions = SearchAuctionsController.getAllSilentAuctionsByKeyword(keyword).get();
                List<DownwardAuction> downwardAuctions = SearchAuctionsController.getAllDownwardAuctionsByKeyword(keyword).get();
                List<ReverseAuction> reverseAuctions = SearchAuctionsController.getAllReverseAuctionsByKeyword(keyword).get();
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
        });
    }

    private void filterByKeywordAndCategory(String keyword, Category category) {
        if (!executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        executorService = Executors.newSingleThreadExecutor();
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        executorService.submit(() -> {
            try {
                List<SilentAuction> silentAuctions = SearchAuctionsController.getAllSilentAuctionsByKeywordAndCategory(keyword, category).get();
                List<DownwardAuction> downwardAuctions = SearchAuctionsController.getAllDownwardAuctionsByKeywordAndCategory(keyword, category).get();
                List<ReverseAuction> reverseAuctions = SearchAuctionsController.getAllReverseAuctionsByKeywordAndCategory(keyword, category).get();
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
        });
    }


    private void setupCategorySpinner(View view) {
        categorySmartSpinner = view.findViewById(R.id.filter_button_search);
        categorySmartSpinner.setItem(
                Arrays.asList(
                        getResources().getStringArray(R.array.categories)
                )
        );

        categorySmartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Category category = Category.fromItalianString(categorySmartSpinner.getSelectedItem());
                if (searchBar.getText().isEmpty()) {
                    filterByCategory(category);
                } else {
                    filterByKeywordAndCategory(searchBar.getText(), category);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if (searchBar.getText().isEmpty()) {
                    updateAuctions();
                } else {
                    filterByKeyword(searchBar.getText());
                }
            }
        });
    }

    private void setupSearchBar(View view) {
        searchBar = view.findViewById(R.id.search_bar);
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                if (text.toString().isEmpty()) {

                    if (categorySmartSpinner.getSelectedItem() == null) {
                        updateAuctions();
                    } else {
                        filterByCategory(Category.fromItalianString(categorySmartSpinner.getSelectedItem()));
                    }

                } else {

                    if (categorySmartSpinner.getSelectedItem() == null) {
                        filterByKeyword(text.toString());
                    } else {
                        filterByKeywordAndCategory(text.toString(), Category.fromItalianString(categorySmartSpinner.getSelectedItem()));
                    }

                }
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });

        searchBar.setSuggestionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClickListener(int position, View v) {
                String suggestion = searchBar.getLastSuggestions().get(position).toString();
                searchBar.setText(suggestion);

                if (suggestion.isEmpty()) {

                    if (categorySmartSpinner.getSelectedItem() == null) {
                        updateAuctions();
                    } else {
                        filterByCategory(Category.fromItalianString(categorySmartSpinner.getSelectedItem()));
                    }

                } else {

                    if (categorySmartSpinner.getSelectedItem() == null) {
                        filterByKeyword(suggestion);
                    } else {
                        filterByKeywordAndCategory(suggestion, Category.fromItalianString(categorySmartSpinner.getSelectedItem()));
                    }

                }
            }

            @Override
            public void OnItemDeleteListener(int position, View v) {
                searchBar.getLastSuggestions().remove(position);
                searchBar.updateLastSuggestions(searchBar.getLastSuggestions());
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CATEGORY_SPINNER_INDEX", categorySmartSpinner.getSelectedItemPosition());
        outState.putString("SEARCH_BAR", searchBar.getText());
    }

    @Override
    public void onPause() {
        super.onPause();
        updateViewModel();
    }

    private void updateViewModel() {
        viewModel.spinnerIndexChanged(categorySmartSpinner.getSelectedItemPosition());
    }
}