package com.ingsw.dietiDeals24.ui.utility.recyclerViews.auctionBids;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.model.Bid;
import com.ingsw.dietiDeals24.model.DownwardBid;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.SilentBid;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.AuctionDetailsActivity;

import java.util.List;

public class AuctionBidAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_SILENT_IN_PROGRESS = 1;
    private static final int TYPE_SILENT_SUCCESSFUL = 2;
    private static final int TYPE_REVERSE = 3;
    private static final int TYPE_DOWNWARD = 4;

    private boolean inProgress;
    private List<? extends Bid> bids;
    private AuctionDetailsActivity auctionDetailsActivity;

    public AuctionBidAdapter(List<? extends Bid> bids, AuctionDetailsActivity activity, boolean inProgress) {
        this.inProgress = inProgress;
        this.bids = bids;
        this.auctionDetailsActivity = activity;
    }


    @Override
    public int getItemViewType(int position) {
        Bid bid = bids.get(position);

        if (bid instanceof SilentBid && inProgress) {
            return TYPE_SILENT_IN_PROGRESS;
        } else if (bid instanceof SilentBid) {
            return TYPE_SILENT_SUCCESSFUL;
        } else if (bid instanceof DownwardBid) {
            return TYPE_DOWNWARD;
        }

        return TYPE_REVERSE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_SILENT_IN_PROGRESS) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auction_silent_in_progress_bid, parent, false);
            return new SilentInProgressBidHolder(view, auctionDetailsActivity);
        } else if (viewType == TYPE_SILENT_SUCCESSFUL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auction_silent_successful_bid, parent, false);
            return new SilentSuccessFulBidHolder(view, auctionDetailsActivity);
        } else if (viewType == TYPE_DOWNWARD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auction_reverse_and_downward_bid, parent, false);
            return new DownwardBidHolder(view, auctionDetailsActivity);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auction_reverse_and_downward_bid, parent, false);
        return new ReverseBidHolder(view, auctionDetailsActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Bid bid = bids.get(position);
        if (bid instanceof SilentBid && inProgress) {
            ((SilentInProgressBidHolder) holder).bind((SilentBid) bid);
        } else if (bid instanceof SilentBid) {
            ((SilentSuccessFulBidHolder) holder).bind((SilentBid) bid);
        } else if (bid instanceof DownwardBid) {
            ((DownwardBidHolder) holder).bind((DownwardBid) bid);
        } else if (bid instanceof ReverseBid) {
            ((ReverseBidHolder) holder).bind((ReverseBid) bid);
        }
    }

    @Override
    public int getItemCount() {
        return bids.size();
    }
}
