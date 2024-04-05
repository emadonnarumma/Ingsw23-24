package com.ingsw.dietiDeals24.ui.home.searchAuctions.searchAuctionDetails;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MakePaymentController;
import com.ingsw.dietiDeals24.controller.SearchAuctionDetailsController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.profile.other.OtherUserProfileActivity;
import com.ingsw.dietiDeals24.ui.home.searchAuctions.makePayment.activities.MakePaymentActivity;
import com.ingsw.dietiDeals24.utility.NumberFormatter;
import com.ingsw.dietiDeals24.utility.PopupGeneratorOf;

public class SearchDownwardAuctionDetailsActivity extends SearchAuctionDetailsActivity {
    private DownwardAuction auction;
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
        greenButton.setText("COMPRA ORA A: " + NumberFormatter.formatPrice(auction.getCurrentPrice()));
        greenButton.setOnClickListener(v -> {
            if (UserHolder.getUser().equals(auction.getOwner())) {
                PopupGeneratorOf.errorPopup(v.getContext(), "Non puoi comprare la tua stessa asta!");
                return;
            }
            if (!UserHolder.isUserBuyer()) {
                PopupGeneratorOf.errorPopup(v.getContext(), "Devi essere un compratore per comprare un'asta!");
            } else {
                MakePaymentController.setBid(null);
                MakePaymentController.setAuction(auction);
                Intent intent = new Intent(v.getContext(), MakePaymentActivity.class);
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
        questionMarkAuctionType.setText(R.string.downward_auction_question);
        questionMarkExplanationAuctionType.setText(R.string.downward_auction_description);
    }
}
