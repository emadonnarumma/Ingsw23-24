package com.ingsw.dietiDeals24.ui.home.searchAuctions.searchAuctionDetails;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MakeBidController;
import com.ingsw.dietiDeals24.controller.SearchAuctionDetailsController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.profile.other.OtherUserProfileActivity;
import com.ingsw.dietiDeals24.ui.home.searchAuctions.makeBid.MakeSilentBidActivity;
import com.ingsw.dietiDeals24.utility.PopupGeneratorOf;

public class SearchSilentAuctionDetailsActivity extends SearchAuctionDetailsActivity {
    private SilentAuction auction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auction = (SilentAuction) SearchAuctionDetailsController.getAuction();

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

        setGreenButton();

    }

    private void setGreenButton() {
        greenButton.setOnClickListener(v -> {
            if (UserHolder.getUser().equals(auction.getOwner())) {
                PopupGeneratorOf.errorPopup(v.getContext(), "Non puoi fare un'offerta alla tua stessa asta!");
                return;
            }
            if (!UserHolder.isUserBuyer()) {
                PopupGeneratorOf.errorPopup(v.getContext(), "Devi essere un compratore per fare un'offerta all'asta!");
            } else {
                MakeBidController.setSilentAuction(auction);
                Intent intent = new Intent(v.getContext(), MakeSilentBidActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    private void setupOwnerButton() {
        ownerButton = findViewById(R.id.auction_owner_button_search_auction_details);
        ownerButton.setText("Annuncio di: " + auction.getOwner().getName());
        ownerButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), OtherUserProfileActivity.class);
            intent.putExtra("otherUser", auction.getOwner());
            v.getContext().startActivity(intent);
        });
    }

    private void setupBottomSheetDialog() {

        questionMarkAuctionType.setText(R.string.silent_auction_question);
        questionMarkExplanationAuctionType.setText(R.string.silent_auction_description);
    }
}
