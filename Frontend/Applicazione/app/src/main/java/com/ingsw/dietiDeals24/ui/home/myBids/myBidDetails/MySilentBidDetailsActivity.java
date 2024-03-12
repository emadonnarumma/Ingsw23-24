package com.ingsw.dietiDeals24.ui.home.myBids.myBidDetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MyBidDetailsController;
import com.ingsw.dietiDeals24.controller.SearchAuctionDetailsController;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.SilentBid;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.BidStatus;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.profile.other.OtherUserProfileActivity;
import com.ingsw.dietiDeals24.ui.utility.NumberFormatter;

public class MySilentBidDetailsActivity extends MyBidDetailsActivity {

    private SilentBid bid;

    private User owner;

    private SilentAuction auction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bid = (SilentBid) MyBidDetailsController.getBid();

        auction = bid.getSilentAuction();

        owner = auction.getOwner();

        setupOwnerButton();
        setupBottomSheetDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAuctionDetails();
        if (auction.getImages() != null) {
            bindImages(auction);
        }
    }

    private void setAuctionDetails() {

        scrollView.setBackground(AppCompatResources.getDrawable(this, R.color.purple));

        auctionTypeTextView.setText(AuctionType.toItalianString(auction.getType()));
        auctionTypeTextView.setBackground(AppCompatResources.getDrawable(this, R.color.purple));

        categoryTextView.setText(Category.toItalianString(auction.getCategory()));

        titleTextView.setText(auction.getTitle());

        wearTextView.setText(Wear.toItalianString(auction.getWear()));

        descriptionTextView.setText(auction.getDescription());

        priceTextView.setVisibility(View.GONE);

        specificInformation1TextView.setText("Scade il: " + SearchAuctionDetailsController.getFormattedExpirationDate(auction));

        specificInformation2TextView.setText("I compratori hanno: " + SearchAuctionDetailsController.getWithdrawalTimeText(auction) + " per ritirare le offerte");

        specificInformation3TextView.setVisibility(View.GONE);
        specificInformation4TextView.setVisibility(View.GONE);

        setButton();

    }

    private void setButton() {
        BidStatus status = bid.getStatus();

        if (status == BidStatus.ACCEPTED) {
            setupAcceptedBidButton();

        } else if (status == BidStatus.PENDING) {
            setupPendingBidButton();

        } else if (status == BidStatus.DECLINED) {
            setupDeclinedBidButton();

        } else {
            setupExpiredBidButton();
        }

    }

    private void setupAcceptedBidButton() {

        bidButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        bidButton.setText("Effettua il pagamento di: " + NumberFormatter.formatPrice(bid.getMoneyAmount()));
    }

    private void setupPendingBidButton() {

        bidButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        bidButton.setText("Ritira offerta");
    }

    private void setupDeclinedBidButton() {
        bidButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        bidButton.setText("Offerta rifiutata");
    }

    private void setupExpiredBidButton() {
        bidButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        bidButton.setText("Asta scaduta");
    }

    private void setupOwnerButton() {

        ownerButton = findViewById(R.id.auction_owner_button_my_bid_details);
        ownerButton.setText("Annuncio di: " + owner.getName());
        ownerButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), OtherUserProfileActivity.class);
            intent.putExtra("otherUser", owner);
            v.getContext().startActivity(intent);
        });
    }

    private void setupBottomSheetDialog() {

        questionMarkAuctionType.setText(R.string.silent_auction_question);
        questionMarkExplanationAuctionType.setText(R.string.silent_auction_description);
    }
}
