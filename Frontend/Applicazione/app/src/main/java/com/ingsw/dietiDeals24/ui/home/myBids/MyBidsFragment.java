package com.ingsw.dietiDeals24.ui.home.myBids;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MyBidsController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.model.DownwardBid;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.SilentBid;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.ui.utility.recyclerViews.myBids.MyBidAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyBidsFragment extends FragmentOfHomeActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
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
    public void onResume() {
        super.onResume();
        updateBids();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(false);

        recyclerView = view.findViewById(R.id.recycler_view_my_bids);
        progressBar = view.findViewById(R.id.progress_bar_my_bids);

    }

    private void updateBids() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        new Thread(() -> {
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
        }).start();
    }
}