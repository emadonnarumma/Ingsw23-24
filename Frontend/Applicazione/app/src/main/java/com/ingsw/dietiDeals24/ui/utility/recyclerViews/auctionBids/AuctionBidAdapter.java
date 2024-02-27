package com.ingsw.dietiDeals24.ui.utility.recyclerViews.auctionBids;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.model.Bid;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.SilentBid;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.AuctionDetailsActivity;

import java.util.List;

public class AuctionBidAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_SILENT = 1;
    private static final int TYPE_REVERSE = 2;

    private List<? extends Bid> bids;
    private AuctionDetailsActivity auctionDetailsActivity;

    public AuctionBidAdapter(List<? extends Bid> bids, AuctionDetailsActivity activity) {
        this.bids = bids;
        this.auctionDetailsActivity = activity;
    }


    @Override
    public int getItemViewType(int position) {
        Bid bid = bids.get(position);
        if (bid instanceof SilentBid) {
            return TYPE_SILENT;
        } else if (bid instanceof ReverseBid) {
            return TYPE_REVERSE;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auction_bid, parent, false);
        if (viewType == TYPE_SILENT)
            return new SilentBidHolder(view, auctionDetailsActivity);
        else
            return new ReverseBidHolder(view, auctionDetailsActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Bid bid = bids.get(position);
        if (bid instanceof SilentBid) {
            ((SilentBidHolder) holder).bind((SilentBid) bid);
        } else if (bid instanceof ReverseBid) {
            ((ReverseBidHolder) holder).bind((ReverseBid) bid);
        }
    }

    @Override
    public int getItemCount() {
        return bids.size();
    }
}
