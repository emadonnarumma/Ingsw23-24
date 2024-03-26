package com.ingsw.dietiDeals24.ui.utility.recyclerViews.auctionBids;

import android.content.Intent;
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
import com.ingsw.dietiDeals24.ui.home.profile.other.OtherUserProfileActivity;
import com.ingsw.dietiDeals24.ui.utility.NumberFormatter;
import com.ingsw.dietiDeals24.ui.utility.OnNavigateToHomeActivityFragmentListener;
import com.ingsw.dietiDeals24.ui.utility.PopupGeneratorOf;

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
        priceTextView.setText(NumberFormatter.formatPrice(silentBid.getMoneyAmount()));
        withdrawalTimeTextView.setText(MyAuctionDetailsController.getRemainingWithdrawalTimeText(silentBid));
        profileButton.setText(silentBid.getBuyer().getName());
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), OtherUserProfileActivity.class);
            intent.putExtra("otherUser", silentBid.getBuyer());
            v.getContext().startActivity(intent);
        });

        acceptButton.setOnClickListener(v -> PopupGeneratorOf.areYouSureToAcceptBidPopup(activity, silentBid));
        declineButton.setOnClickListener(v -> PopupGeneratorOf.areYouSureToDeclineBidPopup(activity, silentBid));
    }
}
