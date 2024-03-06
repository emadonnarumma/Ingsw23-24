package com.ingsw.dietiDeals24.ui.home.searchAuctions.searchAuctionDetails;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.SearchAuctionDetailsController;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.utility.NumberFormatter;

public class SearchDownwardAuctionDetailsActivity extends SearchAuctionDetailsActivity {

    private DownwardAuction auction;

    private CircularProgressButton ownerButton;

    private Handler handler = new Handler();
    private Runnable updateRemainingTimeRunnable = new Runnable() {
        @Override
        public void run() {
            specificInformation3TextView.setText(SearchAuctionDetailsController.getRemainingDecrementTime(auction.getNextDecrement()));
            handler.postDelayed(this, 60000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auction = (DownwardAuction) SearchAuctionDetailsController.getAuction();

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
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(updateRemainingTimeRunnable); // Interrompi l'aggiornamento quando l'activity viene messa in pausa
    }

    private void setAuctionDetails() {

        scrollView.setBackground(AppCompatResources.getDrawable(this, R.color.cyan));

        auctionTypeTextView.setText(AuctionType.toItalianString(auction.getType()));

        categoryTextView.setText(Category.toItalianString(auction.getCategory()));

        titleTextView.setText(auction.getTitle());

        wearTextView.setText(Wear.toItalianString(auction.getWear()));

        descriptionTextView.setText(auction.getDescription());

        priceTextView.setText("Prezzo attuale: " + NumberFormatter.formatPrice(auction.getCurrentPrice()));

        specificInformation1TextView.setText("Valore di decremento: " + NumberFormatter.formatPrice(auction.getDecrementAmount()));

        specificInformation2TextView.setText(SearchAuctionDetailsController.getDecrementTimeText(auction.getDecrementTime()));

        specificInformation4TextView.setVisibility(View.GONE);

        setGreenButton();
    }

    private void setGreenButton() {
        greenButton.setText("COMPRA ORA A: " + auction.getCurrentPrice() + "€");

        //TODO: Implement the onClickListener for the greenButton
    }

    private void setupOwnerButton() {
        ownerButton = findViewById(R.id.auction_owner_button_search_auction_details);
        ownerButton.setText("Annuncio di: " + auction.getOwner().getName());
    }

    private void setupBottomSheetDialog() {

        questionMarkAuctionType.setText(R.string.downward_auction_question);
        questionMarkExplanationAuctionType.setText(R.string.downward_auction_description);
    }
}
