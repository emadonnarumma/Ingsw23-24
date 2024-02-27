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
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.AuctionDetailsActivity;

import java.util.concurrent.ExecutionException;

public class ReverseBidHolder extends RecyclerView.ViewHolder {
    AuctionDetailsActivity activity;
    CircularProgressButton profileButton;
    TextView priceTextView;

    public ReverseBidHolder(@NonNull View itemView, AuctionDetailsActivity activity) {
        super(itemView);
        this.activity = activity;
        profileButton = itemView.findViewById(R.id.profile_button_item_reverse_bid);
        priceTextView = itemView.findViewById(R.id.price_text_view_item_auction_reverse_bid);
    }

    public void bind(ReverseBid reverseBid) {
        profileButton.setText(reverseBid.getSeller().getName());
        priceTextView.setText(reverseBid.getMoneyAmount() + "€");
    }
}
