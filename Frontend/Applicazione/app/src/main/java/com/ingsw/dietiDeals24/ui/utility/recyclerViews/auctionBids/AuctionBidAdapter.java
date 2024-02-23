package com.ingsw.dietiDeals24.ui.utility.recyclerViews.auctionBids;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.model.SilentBid;

import java.util.List;

public class AuctionBidAdapter extends RecyclerView.Adapter<AuctionBidHolder>{
    private List<SilentBid> silentBids;

    public AuctionBidAdapter(List<SilentBid> silentBids) {
        this.silentBids = silentBids;
    }

    @NonNull
    @Override
    public AuctionBidHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auction_bid, parent, false);
        return new AuctionBidHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuctionBidHolder holder, int position) {
        SilentBid silentBid = silentBids.get(position);
        holder.bind(silentBid);
    }

    @Override
    public int getItemCount() {
        return silentBids.size();
    }
}
