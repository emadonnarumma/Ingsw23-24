package com.ingsw.dietiDeals24.ui.home.myAuctions;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MyAuctionsController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.ui.utility.recyclerViews.myAuctions.AuctionAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyAuctionFragment extends Fragment {
    RecyclerView recyclerView;

    public static MyAuctionFragment newInstance() {
        return new MyAuctionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_auctions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_my_auctions);

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Caricamento...");
        progressDialog.show();

        Thread updateAuctions = new Thread(() -> {
            try {
                List<SilentAuction> silentAuctions = MyAuctionsController.getSilentAuctions(UserHolder.user.getEmail()).get();
                List<DownwardAuction> downwardAuctions = MyAuctionsController.getDownwardAuctions(UserHolder.user.getEmail()).get();
                List<ReverseAuction> reverseAuctions = MyAuctionsController.getReverseAuctions(UserHolder.user.getEmail()).get();

                requireActivity().runOnUiThread(() -> {
                    recyclerView.setAdapter(new AuctionAdapter(silentAuctions, downwardAuctions, reverseAuctions));
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    progressDialog.dismiss();
                });
            } catch (ExecutionException e) {
                requireActivity().runOnUiThread(() -> {
                    recyclerView.setAdapter(new AuctionAdapter(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
                    ToastManager.showToast(getContext(), e.getCause().getMessage());
                    progressDialog.dismiss();
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        updateAuctions.start();
    }
}