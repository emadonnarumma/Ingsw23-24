package com.ingsw.dietiDeals24.ui.home.searchAuctions.searchAuctionDetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MakeBidController;
import com.ingsw.dietiDeals24.controller.SearchAuctionDetailsController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.profile.other.OtherUserProfileActivity;
import com.ingsw.dietiDeals24.ui.home.searchAuctions.makeBid.MakeReverseBidActivity;
import com.ingsw.dietiDeals24.utility.NumberFormatter;
import com.ingsw.dietiDeals24.utility.PopupGeneratorOf;
import com.ingsw.dietiDeals24.utility.ToastManager;

import java.util.concurrent.ExecutionException;

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
        specificInformation3TextView.setVisibility(View.GONE);
        specificInformation4TextView.setVisibility(View.GONE);
        setGreenButton();
    }


    private void setGreenButton() {
        greenButton.setText("OFFRI A MENO");
        greenButton.setOnClickListener(v -> {
            if (UserHolder.getUser().equals(auction.getOwner())) {
                PopupGeneratorOf.errorPopup(v.getContext(), "Non puoi fare un'offerta alla tua stessa asta!");
                return;
            }
            if (!UserHolder.isUserSeller()) {
                PopupGeneratorOf.errorPopup(v.getContext(), "Devi essere un venditore per fare un offerta all'asta!");
            } else {
                MakeBidController.setReverseAuction(auction);
                Intent intent = new Intent(v.getContext(), MakeReverseBidActivity.class);
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
        try {
            minBid = SearchAuctionDetailsController.getMinReverseBid(auction.getId()).get();
            if (minBid != null) {
                priceTextView.setText("Offerta attuale: " + NumberFormatter.formatPrice(minBid.getMoneyAmount()));
            } else {
                priceTextView.setText("Nessuna offerta");
            }
        } catch (ExecutionException e) {
            ToastManager.showToast(this, e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
