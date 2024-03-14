package com.ingsw.dietiDeals24.ui.home.myAuctions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MyAuctionsController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.ui.utility.recyclerViews.myAuctions.MyAuctionAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyAuctionsFragment extends Fragment {
    private RecyclerView myAuctionsRecyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout auctionsSwipeRefreshLayout;

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
        setupMyAuctionsRecyclerView(view);
        setupProgresBar(view);
        setupSwipeAuctionRefreshLayout(view);
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
        myAuctionsRecyclerView.setVisibility(View.GONE);

        executorService.submit(() -> {

            try {
                List<SilentAuction> silentAuctions = MyAuctionsController.getSilentAuctions(UserHolder.user.getEmail()).get();
                List<DownwardAuction> downwardAuctions = MyAuctionsController.getDownwardAuctions(UserHolder.user.getEmail()).get();
                List<ReverseAuction> reverseAuctions = MyAuctionsController.getReverseAuctions(UserHolder.user.getEmail()).get();

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        myAuctionsRecyclerView.setAdapter(new MyAuctionAdapter(silentAuctions, downwardAuctions, reverseAuctions));
                        myAuctionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        progressBar.setVisibility(View.GONE);
                        myAuctionsRecyclerView.setVisibility(View.VISIBLE);
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