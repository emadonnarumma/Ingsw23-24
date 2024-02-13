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
import com.ingsw.dietiDeals24.model.Buyer;
import com.ingsw.dietiDeals24.model.Seller;
import com.ingsw.dietiDeals24.ui.home.myAuctions.recyclerView.AuctionAdapter;

import java.util.ArrayList;

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

        AuctionAdapter adapter;
        if (UserHolder.user.getClass() == Seller.class) {
           Seller seller = (Seller) UserHolder.user;
           adapter = new AuctionAdapter(seller.getSilentAuctions(),
                   seller.getDownwardAuctions(),
                   new ArrayList<>());
        } else {
            Buyer buyer = (Buyer) UserHolder.user;
            adapter = new AuctionAdapter(new ArrayList<>(),
                    new ArrayList<>(),
                    buyer.getReverseAuctions());
        }

        recyclerView.setAdapter(adapter);
    }
}