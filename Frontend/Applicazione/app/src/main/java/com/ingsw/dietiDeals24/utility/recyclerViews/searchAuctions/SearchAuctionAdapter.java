package com.ingsw.dietiDeals24.utility.recyclerViews.searchAuctions;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.SearchAuctionDetailsController;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.ui.home.searchAuctions.searchAuctionDetails.SearchDownwardAuctionDetailsActivity;
import com.ingsw.dietiDeals24.ui.home.searchAuctions.searchAuctionDetails.SearchReverseAuctionDetailsActivity;
import com.ingsw.dietiDeals24.ui.home.searchAuctions.searchAuctionDetails.SearchSilentAuctionDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchAuctionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Auction> auctions;

    private static final int TYPE_SILENT = 1;
    private static final int TYPE_DOWNWARD = 2;
    private static final int TYPE_REVERSE = 3;

    public SearchAuctionAdapter(List<SilentAuction> silentAuctions,
                                List<DownwardAuction> downwardAuctions,
                                List<ReverseAuction> reverseAuctions) {

        auctions = new ArrayList<>();

        auctions.addAll(silentAuctions);
        auctions.addAll(downwardAuctions);
        auctions.addAll(reverseAuctions);
    }

    public SearchAuctionAdapter(List<Auction> auctions) {
        this.auctions = auctions;
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_silent_auction, parent, false);
            return new SearchSilentAuctionViewHolder(view);
        } else if (viewType == TYPE_DOWNWARD) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_downward_auction, parent, false);
            return new SearchDownwardAuctionViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_reverse_auction, parent, false);
            return new SearchReverseAuctionViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Auction auction = auctions.get(position);

        if (holder instanceof SearchSilentAuctionViewHolder) {
            ((SearchSilentAuctionViewHolder) holder).bind((SilentAuction) auction);
        } else if (holder instanceof SearchDownwardAuctionViewHolder) {
            ((SearchDownwardAuctionViewHolder) holder).bind((DownwardAuction) auction);
        } else if (holder instanceof SearchReverseAuctionViewHolder) {
            ((SearchReverseAuctionViewHolder) holder).bind((ReverseAuction) auction);
        }

        holder.itemView.setOnClickListener(v -> startSearchAuctionDetailsActivity(holder, auction, v));
    }


    @Override
    public int getItemCount() {
        return auctions.size();
    }

    private void startSearchAuctionDetailsActivity(RecyclerView.ViewHolder holder, Auction auction, View v) {

        Intent intent;

        if (auction instanceof SilentAuction) {
            intent = new Intent(v.getContext(), SearchSilentAuctionDetailsActivity.class);

        } else if (auction instanceof DownwardAuction) {
            intent = new Intent(v.getContext(), SearchDownwardAuctionDetailsActivity.class);

        } else {
            intent = new Intent(v.getContext(), SearchReverseAuctionDetailsActivity.class);
        }

        SearchAuctionDetailsController.setAuction(auction);

        v.getContext().startActivity(intent);

    }
}
