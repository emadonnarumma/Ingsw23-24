package com.ingsw.dietiDeals24.ui.home.myBids;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MyBidsController;
import com.ingsw.dietiDeals24.controller.SearchAuctionsController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.DownwardBid;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.SilentBid;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.ui.utility.recyclerViews.myBids.MyBidAdapter;
import com.ingsw.dietiDeals24.ui.utility.recyclerViews.searchAuctions.SearchAuctionAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyBidsFragment extends FragmentOfHomeActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout bidsSwipeRefreshLayout;
    private ExecutorService executorService;

    private TextView noBidsTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        executorService = Executors.newSingleThreadExecutor();
        return inflater.inflate(R.layout.fragment_my_bids, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateBids();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(false);
        recyclerView = view.findViewById(R.id.recycler_view_fragment_my_bids);
        progressBar = view.findViewById(R.id.progress_bar_my_bids);
        setupBidsSwipeRefreshLayout(view);
        setupNoAuctionTextView(view);
    }

    private void setupBidsSwipeRefreshLayout(@NonNull View view) {
        bidsSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_fragment_my_bids);
        bidsSwipeRefreshLayout.setOnRefreshListener(() -> {
            bidsSwipeRefreshLayout.setRefreshing(false);
            MyBidsController.setUpdatedAll(false);
            updateBids();
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

    private void updateBids() {
        if (!executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        executorService = Executors.newSingleThreadExecutor();
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        noBidsTextView.setVisibility(View.GONE);
        executorService.submit(() -> {
            try {
                List<SilentBid> silentBids = MyBidsController.getSilentBids(UserHolder.user.getEmail()).get();
                List<DownwardBid> downwardBids = MyBidsController.getDownwardBids(UserHolder.user.getEmail()).get();
                List<ReverseBid> reverseBids = MyBidsController.getReverseBids(UserHolder.user.getEmail()).get();

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        recyclerView.setAdapter(new MyBidAdapter(silentBids, downwardBids, reverseBids));
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        if (silentBids.isEmpty() && downwardBids.isEmpty() && reverseBids.isEmpty()) {
                            noBidsTextView.setVisibility(View.VISIBLE);
                        } else {
                            noBidsTextView.setVisibility(View.GONE);
                        }
                    });
                }

            } catch (ExecutionException e) {
                requireActivity().runOnUiThread(() -> {
                    recyclerView.setAdapter(new MyBidAdapter(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
                    ToastManager.showToast(getContext(), e.getCause().getMessage());
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setupNoAuctionTextView(View view) {
        noBidsTextView = view.findViewById(R.id.no_bids_text_view_my_auctions);
        noBidsTextView.setVisibility(View.GONE);
    }
}