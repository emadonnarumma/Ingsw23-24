package com.ingsw.dietiDeals24.ui.home.myAuctions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.enumeration.Category;
import com.ingsw.dietiDeals24.enumeration.Wear;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.ui.home.myBids.MyBidsFragment;
import com.ingsw.dietiDeals24.ui.utility.recyclerView.myAuction.AuctionAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        List<Auction> auctionList = new ArrayList<>();
        auctionList.add(new SilentAuction(
                UserHolder.user,
                "Title",
                "Description",
                Wear.BAD_CONDITION,
                Category.ART,
                AuctionStatus.IN_PROGRESS,
                "23/10/2000",
                10000000L,
                new ArrayList<>()
        ));

        AuctionAdapter adapter = new AuctionAdapter(auctionList);
        recyclerView.setAdapter(adapter);
    }
}