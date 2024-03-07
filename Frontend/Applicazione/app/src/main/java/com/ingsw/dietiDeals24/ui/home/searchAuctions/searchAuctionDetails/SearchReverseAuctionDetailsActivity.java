package com.ingsw.dietiDeals24.ui.home.searchAuctions.searchAuctionDetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.SearchAuctionDetailsController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.profile.other.OtherUserProfileActivity;
import com.ingsw.dietiDeals24.ui.home.searchAuctions.MakeSilentBidActivity;
import com.ingsw.dietiDeals24.ui.utility.NumberFormatter;

public class SearchReverseAuctionDetailsActivity extends SearchAuctionDetailsActivity {
    private ReverseAuction auction;

    private ReverseBid minBid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auction = (ReverseAuction) SearchAuctionDetailsController.getAuction();

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

        scrollView.setBackground(AppCompatResources.getDrawable(this, R.color.brown));

        auctionTypeTextView.setText(AuctionType.toItalianString(auction.getType()));

        categoryTextView.setText(Category.toItalianString(auction.getCategory()));

        titleTextView.setText(auction.getTitle());


        wearTextView.setText(Wear.toItalianString(auction.getWear()));

        descriptionTextView.setText(auction.getDescription());

        getMinBid();

        specificInformation1TextView.setText("Scade il: " + SearchAuctionDetailsController.getFormattedExpirationDate(auction));

        specificInformation2TextView.setVisibility(View.GONE);

        setGreenButton();
    }


    private void setGreenButton() {
        greenButton.setText("OFFRI A MENO");
        greenButton.setOnClickListener(v -> {
            if (!UserHolder.isUserSeller()) {
                if (UserHolder.getBuyer().equals(auction.getOwner())) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Attenzione")
                            .setMessage("Non puoi fare offerte al tuo stesso annuncio!")
                            .setPositiveButton("OK", null)
                            .show();
                } else {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Attenzione")
                            .setMessage("Devi essere un venditore per fare un offerta!")
                            .setPositiveButton("OK", null)
                            .show();
                }
            } else {
                Intent intent = new Intent(v.getContext(), MakeSilentBidActivity.class);
                intent.putExtra("auction", auction);
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

        questionMarkAuctionType.setText(R.string.reverse_auction_question);
        questionMarkExplanationAuctionType.setText(R.string.reverse_auction_description);
    }

    private void getMinBid() {

        SearchAuctionDetailsController.getMinReverseBid(auction.getId()).thenAccept(bid -> {
            minBid = bid;
            if (minBid != null) {
                priceTextView.setText("Offerta attuale: " + NumberFormatter.formatPrice(minBid.getPrice()));

            } else {
                priceTextView.setText("Offerta attuale: Nessun offerta");
            }
        });
    }
}
