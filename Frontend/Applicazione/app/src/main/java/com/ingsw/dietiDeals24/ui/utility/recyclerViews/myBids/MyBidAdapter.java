package com.ingsw.dietiDeals24.ui.utility.recyclerViews.myBids;

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

import java.util.ArrayList;
import java.util.List;

public class MyBidAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Bid> bids;

    private static final int TYPE_SILENT = 1;
    private static final int TYPE_DOWNWARD = 2;
    private static final int TYPE_REVERSE = 3;

    public MyBidAdapter(List<SilentBid> silentBids,
                            List<DownwardBid> downwardBids,
                            List<ReverseBid> reverseBids) {

        bids = new ArrayList<>();
        bids.addAll(silentBids);
        bids.addAll(downwardBids);
        bids.addAll(reverseBids);
    }

    @Override
    public int getItemViewType(int position) {
        Bid bid = bids.get(position);
        if (bid instanceof SilentBid) {
            return TYPE_SILENT;
        } else if (bid instanceof DownwardBid) {
            return TYPE_DOWNWARD;
        } else if (bid instanceof ReverseBid) {
            return TYPE_REVERSE;
        }
        return -1;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_SILENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_silent_bid, parent, false);
            return new MySilentBidViewHolder(view);
        } else if (viewType == TYPE_DOWNWARD) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_downward_bid, parent, false);
            return new MyDownwardBidViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_reverse_bid, parent, false);
            return new MyReverseBidViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Bid bid = bids.get(position);
        if (holder instanceof MySilentBidViewHolder) {
            ((MySilentBidViewHolder) holder).bind((SilentBid) bid);
        } else if (holder instanceof MyDownwardBidViewHolder) {
            ((MyDownwardBidViewHolder) holder).bind((DownwardBid) bid);
        } else if (holder instanceof MyReverseBidViewHolder) {
            ((MyReverseBidViewHolder) holder).bind((ReverseBid) bid);
        }
    }

    @Override
    public int getItemCount() {
        return bids.size();
    }
}
