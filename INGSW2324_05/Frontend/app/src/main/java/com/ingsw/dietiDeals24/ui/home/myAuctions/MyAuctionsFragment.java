package com.ingsw.dietiDeals24.ui.home.myAuctions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MyAuctionsController;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.recyclerViews.myAuctions.MyAuctionAdapter;
import com.ingsw.dietiDeals24.utility.ToastManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyAuctionsFragment extends FragmentOfHomeActivity {
    private RecyclerView myAuctionsRecyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout auctionsSwipeRefreshLayout;
    private TextView noAuctionTextView;

    private ExecutorService executorService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        executorService = Executors.newSingleThreadExecutor();

        return inflater.inflate(R.layout.fragment_my_auctions, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAuctions();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(false);
        setupMyAuctionsRecyclerView(view);
        setupProgresBar(view);
        setupSwipeAuctionRefreshLayout(view);
        setupNoAuctionTextView(view);
    }

    private void setupNoAuctionTextView(View view) {
        noAuctionTextView = view.findViewById(R.id.no_auction_text_view_my_auctions);
        noAuctionTextView.setVisibility(View.GONE);
    }

    private void setupProgresBar(@NonNull View view) {
        progressBar = view.findViewById(R.id.progress_bar_my_auctions);
    }

    private void setupMyAuctionsRecyclerView(@NonNull View view) {
        myAuctionsRecyclerView = view.findViewById(R.id.recycler_view_fragment_my_auctions);
    }

    private void setupSwipeAuctionRefreshLayout(@NonNull View view) {
        auctionsSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_fragment_my_auctions);
        auctionsSwipeRefreshLayout.setOnRefreshListener(() -> {
            auctionsSwipeRefreshLayout.setRefreshing(false);
            MyAuctionsController.setUpdatedAll(false);
            updateAuctions();
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

    private void updateAuctions() {
        if (!executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        executorService = Executors.newSingleThreadExecutor();
        progressBar.setVisibility(View.VISIBLE);
        noAuctionTextView.setVisibility(View.GONE);
        myAuctionsRecyclerView.setVisibility(View.GONE);
        executorService.submit(() -> {

            try {
                List<SilentAuction> silentAuctions = MyAuctionsController.getSilentAuctions().get();
                List<DownwardAuction> downwardAuctions = MyAuctionsController.getDownwardAuctions().get();
                List<ReverseAuction> reverseAuctions = MyAuctionsController.getReverseAuctions().get();

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        myAuctionsRecyclerView.setAdapter(new MyAuctionAdapter(silentAuctions, downwardAuctions, reverseAuctions));
                        myAuctionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        progressBar.setVisibility(View.GONE);
                        myAuctionsRecyclerView.setVisibility(View.VISIBLE);

                        if (silentAuctions.isEmpty() && downwardAuctions.isEmpty() && reverseAuctions.isEmpty()) {
                            noAuctionTextView.setVisibility(View.VISIBLE);
                        } else {
                            noAuctionTextView.setVisibility(View.GONE);
                        }
                    });
                }
            } catch (ExecutionException e) {
                requireActivity().runOnUiThread(() -> {
                    myAuctionsRecyclerView.setAdapter(new MyAuctionAdapter(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
                    ToastManager.showToast(getContext(), e.getCause().getMessage());
                    progressBar.setVisibility(View.GONE);
                    myAuctionsRecyclerView.setVisibility(View.VISIBLE);
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}