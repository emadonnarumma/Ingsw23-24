package com.ingsw.dietiDeals24.ui.home.searchAuctions.searchAuctionDetails;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.SearchAuctionDetailsController;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
public class SearchReverseAuctionDetailsActivity extends SearchAuctionDetailsActivity {

    private ReverseAuction auction;

    private CircularProgressButton ownerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auction = (ReverseAuction) SearchAuctionDetailsController.getAuction();

        setupOwnerButton();
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

        scrollView.setBackground(AppCompatResources.getDrawable(this, R.color.brown));

        auctionTypeTextView.setText(AuctionType.toItalianString(auction.getType()));

        categoryTextView.setText(Category.toItalianString(auction.getCategory()));

        titleTextView.setText(auction.getTitle());


        wearTextView.setText(Wear.toItalianString(auction.getWear()));

        descriptionTextView.setText(auction.getDescription());

        priceTextView.setText("Offerta attuale: " + auction.getStartingPrice() + "€");

        specificInformation1TextView.setText("Scade il: " + SearchAuctionDetailsController.getFormattedExpirationDate(auction));

        specificInformation2TextView.setVisibility(View.GONE);

        setGreenButton();
    }


    private void setGreenButton() {
        greenButton.setText("OFFRI A MENO");

        //TODO: Implement the onClickListener for the greenButton
    }

    private void setupOwnerButton() {
        ownerButton = findViewById(R.id.auction_owner_button_search_auction_details);
        ownerButton.setText("Annuncio di: " + auction.getOwner().getName());
    }
}
