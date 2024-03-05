package com.ingsw.dietiDeals24.ui.utility.recyclerViews.auctionBids;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MyAuctionDetailsController;
import com.ingsw.dietiDeals24.model.SilentBid;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.AuctionDetailsActivity;
import com.ingsw.dietiDeals24.ui.utility.NumberFormatter;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class SilentInProgressBidHolder extends RecyclerView.ViewHolder {
    AuctionDetailsActivity activity;
    CircularProgressButton profileButton, acceptButton, declineButton;
    TextView priceTextView, withdrawalTimeTextView;

    public SilentInProgressBidHolder(@NonNull View itemView, AuctionDetailsActivity activity) {
        super(itemView);
        this.activity = activity;
        profileButton = itemView.findViewById(R.id.profile_button_item_auction_in_progress_silent_bid);
        acceptButton = itemView.findViewById(R.id.accept_button_item_auction_in_progress_silent_bid);
        declineButton = itemView.findViewById(R.id.decline_button_item_auction_in_progress_silent_bid);
        withdrawalTimeTextView = itemView.findViewById(R.id.withdrawal_time_text_item_auction_in_progress_silent_bid);
        priceTextView = itemView.findViewById(R.id.price_text_view_item_auction_in_progress_silent_bid);
    }

    public void bind(SilentBid silentBid) {
        profileButton.setText(silentBid.getBuyer().getName());
        priceTextView.setText(NumberFormatter.formatPrice(silentBid.getMoneyAmount()));
        withdrawalTimeTextView.setText(MyAuctionDetailsController.getRemainingWithdrawalTimeText(silentBid));
        acceptButton.setOnClickListener(v -> new AlertDialog.Builder(v.getContext())
                .setTitle("Conferma")
                .setMessage("Sei sicuro di voler accettare?")
                .setPositiveButton("Si", (dialog, which) -> new Thread(() -> {
                    try {
                        boolean isAccepted = MyAuctionDetailsController.acceptBid(silentBid.getIdBid()).get();
                        if (isAccepted) {
                            activity.onNavigateToHomeActivityFragmentRequest("MyAuctionFragment", activity.getApplicationContext());
                        }
                    } catch (ExecutionException e) {
                        v.post(() -> Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start())
                .setNegativeButton("No", null)
                .show());

        declineButton.setOnClickListener(v -> new AlertDialog.Builder(v.getContext())
                .setTitle("Conferma")
                .setMessage("Sei sicuro di voler rifiutare?")
                .setPositiveButton("Si", (dialog, which) -> new Thread(() -> {
                    try {
                        boolean isRefused = MyAuctionDetailsController.deleteBid(silentBid.getIdBid()).get();
                        if (isRefused) {
                            AuctionBidAdapter adapter = (AuctionBidAdapter) activity.getBidsRecyclerView().getAdapter();
                            int position = adapter.getBids().indexOf(silentBid);
                            adapter.getBids().remove(silentBid);
                            v.post(() -> {
                                activity.getBidsRecyclerView().getAdapter().notifyItemRemoved(position);

                                if (adapter.getBids().isEmpty()) {
                                    activity.getBidsRecyclerView().setVisibility(View.GONE);
                                    activity.getEmptyBidsTextView().setText("Nessuna offerta disponibile");
                                    activity.getEmptyBidsTextView().setVisibility(View.VISIBLE);
                                    }
                            });
                        }
                    } catch (ExecutionException e) {
                        v.post(() -> Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start())
                .setNegativeButton("No", null)
                .show());
    }
}
