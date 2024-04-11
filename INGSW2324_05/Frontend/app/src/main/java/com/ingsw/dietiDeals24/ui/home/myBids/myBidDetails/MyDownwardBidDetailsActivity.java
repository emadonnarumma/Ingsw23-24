package com.ingsw.dietiDeals24.ui.home.myBids.myBidDetails;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MyBidDetailsController;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.DownwardBid;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.profile.other.OtherUserProfileActivity;
import com.ingsw.dietiDeals24.utility.NumberFormatter;

public class MyDownwardBidDetailsActivity extends MyBidDetailsActivity{

    private DownwardBid bid;

    private DownwardAuction auction;

    private User owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bid = (DownwardBid)  MyBidDetailsController.getBid();

        auction = bid.getDownwardAuction();

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

        scrollView.setBackground(AppCompatResources.getDrawable(this, R.color.blue));

        auctionTypeTextView.setText(AuctionType.toItalianString(auction.getType()));
        auctionTypeTextView.setBackground(AppCompatResources.getDrawable(this, R.color.blue));

        categoryTextView.setText(Category.toItalianString(auction.getCategory()));

        titleTextView.setText(auction.getTitle());

        wearTextView.setText(Wear.toItalianString(auction.getWear()));

        descriptionTextView.setText(auction.getDescription());

        priceTextView.setVisibility(View.GONE);

        specificInformation1TextView.setVisibility(View.GONE);
        specificInformation2TextView.setVisibility(View.GONE);
        specificInformation3TextView.setVisibility(View.GONE);
        specificInformation4TextView.setVisibility(View.GONE);

        setButton();

    }

    private void setButton() {
        Drawable shoppingCartIcon = ContextCompat.getDrawable(this, R.drawable.ic_cart_shopping_24dp);
        shoppingCartIcon.setBounds(0, 0, shoppingCartIcon.getIntrinsicWidth(), shoppingCartIcon.getIntrinsicHeight());
        bidButton.setCompoundDrawablesWithIntrinsicBounds(null, null, shoppingCartIcon, null);

        bidButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        bidButton.setText("Acquistato per: " + NumberFormatter.formatPrice(bid.getMoneyAmount()));
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

        questionMarkAuctionType.setText(R.string.downward_auction_question);
        questionMarkExplanationAuctionType.setText(R.string.downward_auction_description);
    }
}
