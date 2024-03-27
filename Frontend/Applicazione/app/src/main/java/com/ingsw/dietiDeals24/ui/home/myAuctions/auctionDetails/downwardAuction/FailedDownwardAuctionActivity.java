package com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.downwardAuction;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.ImageController;
import com.ingsw.dietiDeals24.controller.MyAuctionDetailsController;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder.AuctionHolder;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes.GeneralAuctionAttributesViewModel;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.AuctionDetailsActivity;
import com.ingsw.dietiDeals24.ui.utility.NumberFormatter;
import com.ingsw.dietiDeals24.ui.utility.OnNavigateToHomeActivityFragmentListener;
import com.ingsw.dietiDeals24.ui.utility.PopupGeneratorOf;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FailedDownwardAuctionActivity extends AuctionDetailsActivity {
    private DownwardAuction auction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auction = (DownwardAuction) MyAuctionDetailsController.getAuction();

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
        scrollView.setBackground(AppCompatResources.getDrawable(this, R.color.cyan));
        auctionTypeTextView.setText(AuctionType.toItalianString(auction.getType()));
        categoryTextView.setText(Category.toItalianString(auction.getCategory()));
        titleTextView.setText(auction.getTitle());

        auctionStatusTextView.setText(AuctionStatus.toItalianString(auction.getStatus()));
        auctionStatusTextView.setTextColor(ContextCompat.getColor(this, R.color.red));
        auctionStatusTextView.setStrokeColor(R.color.black);

        wearTextView.setText(Wear.toItalianString(auction.getWear()));
        descriptionTextView.setText(auction.getDescription());
        priceTextView.setText("Prezzo attuale: " + auction.getCurrentPrice() + "€");

        specificInformation1TextView.setText("Valore di decremento: " + NumberFormatter.formatPrice(auction.getDecrementAmount()));
        specificInformation2TextView.setText(MyAuctionDetailsController.getDecrementTimeText(auction.getDecrementTime()));
        specificInformation3TextView.setText("Prezzo minimo segreto " + NumberFormatter.formatPrice(auction.getSecretMinimumPrice()));
        specificInformation4TextView.setVisibility(View.GONE);
        setButtons();
    }

    private void setButtons() {
        setRedButton();
        setGreenButton();
    }

    private void setGreenButton() {
        greenButton.setBackground(AppCompatResources.getDrawable(this, R.drawable.square_shape_green));
        greenButton.setText("RILANCIA");
        greenButton.setOnClickListener(v -> PopupGeneratorOf.areYouSureToRelaunchAuctionPopup(this, auction));
    }

    private void setRedButton() {
        redButton.setBackground(AppCompatResources.getDrawable(this, R.drawable.square_shape_red));
        redButton.setText("CANCELLA L'ASTA");
        redButton.setOnClickListener(v -> {
            PopupGeneratorOf.areYouSureToDeleteAuctionPopup(this, auction);
        });
    }

    private void setupBottomSheetDialog() {

        questionMarkAuctionType.setText(R.string.downward_auction_question);
        questionMarkExplanationAuctionType.setText(R.string.downward_auction_description);
    }
}
