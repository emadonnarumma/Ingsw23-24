package com.ingsw.dietiDeals24.ui.utility.recyclerViews.myAuctions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;

import java.util.ArrayList;
import java.util.List;

public class AuctionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_SILENT = 1;
    private static final int TYPE_DOWNWARD = 2;
    private static final int TYPE_REVERSE = 3;

    private List<Auction> auctions;

    public AuctionAdapter(List<SilentAuction> silentAuctions,
                          List<DownwardAuction> downwardAuctions,
                          List<ReverseAuction> reverseAuctions) {

        auctions = new ArrayList<>();
        auctions.addAll(silentAuctions);
        auctions.addAll(downwardAuctions);
        auctions.addAll(reverseAuctions);
    }

    @Override
    public int getItemViewType(int position) {
        Auction auction = auctions.get(position);
        if (auction instanceof SilentAuction) {
            return TYPE_SILENT;
        } else if (auction instanceof DownwardAuction) {
            return TYPE_DOWNWARD;
        } else if (auction instanceof ReverseAuction) {
            return TYPE_REVERSE;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_SILENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_silent_auction, parent, false);
            return new SilentAuctionViewHolder(view);
        } else if (viewType == TYPE_DOWNWARD) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_downward_auction, parent, false);
            return new DownwardAuctionViewHolder(view);
        } else if (viewType == TYPE_REVERSE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reverse_auction, parent, false);
            return new ReverseAuctionViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Auction auction = auctions.get(position);
        if (holder instanceof SilentAuctionViewHolder) {
            ((SilentAuctionViewHolder) holder).bind((SilentAuction) auction);
        } else if (holder instanceof DownwardAuctionViewHolder) {
            ((DownwardAuctionViewHolder) holder).bind((DownwardAuction) auction);
        } else if (holder instanceof ReverseAuctionViewHolder) {
            ((ReverseAuctionViewHolder) holder).bind((ReverseAuction) auction);
        }
    }

    @Override
    public int getItemCount() {
        return auctions.size();
    }
}