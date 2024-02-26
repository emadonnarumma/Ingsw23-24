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
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.SilentBid;

import java.util.concurrent.ExecutionException;

public class AuctionBidHolder extends RecyclerView.ViewHolder {
    CircularProgressButton profileButton, acceptButton, declineButton;
    TextView priceTextView, withdrawalTimeTextView;

    public AuctionBidHolder(@NonNull View itemView) {
        super(itemView);
        profileButton = itemView.findViewById(R.id.profile_button_item_bid);
        acceptButton = itemView.findViewById(R.id.accept_button_item_auction_bid);
        declineButton = itemView.findViewById(R.id.decline_button_item_auction_bid);
        withdrawalTimeTextView = itemView.findViewById(R.id.withdrawal_time_text_item_auction_bid);
        priceTextView = itemView.findViewById(R.id.price_text_view_item_auction_bid);
    }

    public void bind(SilentBid silentBid) {
        profileButton.setText(silentBid.getBuyer().getName());
        priceTextView.setText(silentBid.getMoneyAmount() + "€");
        withdrawalTimeTextView.setText(MyAuctionDetailsController.getRemainingWithdrawalTimeText(silentBid));
    }
}
