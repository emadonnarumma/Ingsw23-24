package com.ingsw.dietiDeals24.ui.utility.recyclerViews.myAuctions;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MyAuctionDetailsController;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.downwardAuction.FailedDownwardAuctionActivity;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.downwardAuction.InProgressDownwardAuctionActivity;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.downwardAuction.SuccessfullDownwardAuctionActivity;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.reverseAuction.FailedReverseAuctionActivity;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.reverseAuction.InProgressReverseAuctionActivity;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.reverseAuction.SuccessfullReverseAuctionActivity;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.silentAuction.FailedSilentAuctionActivity;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.silentAuction.InProgressSilentAuctionActivity;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.silentAuction.SuccessfullSilentAuctionActivity;

import java.util.ArrayList;
import java.util.List;

public class MyAuctionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_SILENT = 1;
    private static final int TYPE_DOWNWARD = 2;
    private static final int TYPE_REVERSE = 3;

    private List<Auction> auctions;

    public MyAuctionAdapter(List<SilentAuction> silentAuctions,
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_silent_auction, parent, false);
            return new MySilentAuctionViewHolder(view);
        } else if (viewType == TYPE_DOWNWARD) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_downward_auction, parent, false);
            return new MyDownwardAuctionViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_reverse_auction, parent, false);
            return new MyReverseAuctionViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Auction auction = auctions.get(position);
        if (holder instanceof MySilentAuctionViewHolder) {
            ((MySilentAuctionViewHolder) holder).bind((SilentAuction) auction);
        } else if (holder instanceof MyDownwardAuctionViewHolder) {
            ((MyDownwardAuctionViewHolder) holder).bind((DownwardAuction) auction);
        } else if (holder instanceof MyReverseAuctionViewHolder) {
            ((MyReverseAuctionViewHolder) holder).bind((ReverseAuction) auction);
        }

        holder.itemView.setOnClickListener(v -> startAuctionActivity(holder, auction, v));
    }

    private void startAuctionActivity(RecyclerView.ViewHolder holder, Auction auction, View v) {
        Class<?> activityClass = null;
        if (holder instanceof MySilentAuctionViewHolder) {
            activityClass = getActivityClassForAuctionStatus(auction.getStatus(),
                    SuccessfullSilentAuctionActivity.class,
                    FailedSilentAuctionActivity.class,
                    InProgressSilentAuctionActivity.class);
        } else if (holder instanceof MyDownwardAuctionViewHolder) {
            activityClass = getActivityClassForAuctionStatus(auction.getStatus(),
                    SuccessfullDownwardAuctionActivity.class,
                    FailedDownwardAuctionActivity.class,
                    InProgressDownwardAuctionActivity.class);
        } else if (holder instanceof MyReverseAuctionViewHolder) {
            activityClass = getActivityClassForAuctionStatus(auction.getStatus(),
                    SuccessfullReverseAuctionActivity.class,
                    FailedReverseAuctionActivity.class,
                    InProgressReverseAuctionActivity.class);
        }

        if (activityClass != null) {
            Intent intent = new Intent(v.getContext(), activityClass);
            MyAuctionDetailsController.setAuction(auction);
            v.getContext().startActivity(intent);
        }
    }

    private Class<?> getActivityClassForAuctionStatus(AuctionStatus status,
                                                      Class<?> successfulClass,
                                                      Class<?> failedClass,
                                                      Class<?> inProgressClass) {
        if (status == AuctionStatus.SUCCESSFUL) {
            return successfulClass;
        } else if (status == AuctionStatus.FAILED) {
            return failedClass;
        } else if (status == AuctionStatus.IN_PROGRESS) {
            return inProgressClass;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return auctions.size();
    }
}