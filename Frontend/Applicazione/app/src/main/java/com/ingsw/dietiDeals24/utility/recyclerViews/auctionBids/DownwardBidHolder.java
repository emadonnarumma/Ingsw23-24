package com.ingsw.dietiDeals24.utility.recyclerViews.auctionBids;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.model.DownwardBid;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.AuctionDetailsActivity;
import com.ingsw.dietiDeals24.ui.home.profile.other.OtherUserProfileActivity;
import com.ingsw.dietiDeals24.utility.NumberFormatter;

public class DownwardBidHolder extends RecyclerView.ViewHolder {

    AuctionDetailsActivity activity;
    CircularProgressButton profileButton;
    TextView priceTextView;

    public DownwardBidHolder(@NonNull View itemView, AuctionDetailsActivity activity) {
        super(itemView);
        this.activity = activity;
        profileButton = itemView.findViewById(R.id.profile_button_item_auction_reverse_bid);
        priceTextView = itemView.findViewById(R.id.price_text_view_item_auction_reverse_bid);
    }

    public void bind(DownwardBid downwardBid) {
        profileButton.setText(downwardBid.getBuyer().getName());
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), OtherUserProfileActivity.class);
            intent.putExtra("otherUser", downwardBid.getBuyer());
            v.getContext().startActivity(intent);
        });

        priceTextView.setText(NumberFormatter.formatPrice(downwardBid.getMoneyAmount()));
    }
}
