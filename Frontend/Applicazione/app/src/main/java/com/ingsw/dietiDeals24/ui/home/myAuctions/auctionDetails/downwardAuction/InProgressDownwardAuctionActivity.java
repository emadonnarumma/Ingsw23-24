package com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.downwardAuction;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MyAuctionDetailsController;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.AuctionDetailsActivity;
import com.ingsw.dietiDeals24.utility.NumberFormatter;
import com.ingsw.dietiDeals24.utility.PopupGeneratorOf;

public class InProgressDownwardAuctionActivity extends AuctionDetailsActivity {
    private DownwardAuction auction;

    private Handler handler = new Handler();
    private Runnable updateRemainingTimeRunnable = new Runnable() {
        @Override
        public void run() {
            specificInformation3TextView.setText(MyAuctionDetailsController.getRemainingDecrementTime(auction.getNextDecrement()));
            handler.postDelayed(this, 60000);
        }
    };

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
        handler.post(updateRemainingTimeRunnable);
        if (auction.getImages() != null) {
            bindImages(auction);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(updateRemainingTimeRunnable);
    }

    private void setAuctionDetails() {
        scrollView.setBackground(AppCompatResources.getDrawable(this, R.color.cyan));
        auctionTypeTextView.setText(AuctionType.toItalianString(auction.getType()));
        categoryTextView.setText(Category.toItalianString(auction.getCategory()));
        titleTextView.setText(auction.getTitle());

        auctionStatusTextView.setText(AuctionStatus.toItalianString(auction.getStatus()));
        auctionStatusTextView.setTextColor(ContextCompat.getColor(this, R.color.yellow));
        auctionStatusTextView.setStrokeColor(R.color.black);

        wearTextView.setText(Wear.toItalianString(auction.getWear()));
        descriptionTextView.setText(auction.getDescription());
        priceTextView.setText("Prezzo attuale: " + NumberFormatter.formatPrice(auction.getCurrentPrice()));

        specificInformation1TextView.setText("Valore di decremento: " + NumberFormatter.formatPrice(auction.getDecrementAmount()));
        specificInformation2TextView.setText(MyAuctionDetailsController.getDecrementTimeText(auction.getDecrementTime()));
        specificInformation4TextView.setText("Prezzo minimo segreto " + NumberFormatter.formatPrice(auction.getSecretMinimumPrice()));
        setButtons();
    }

    private void setButtons() {
        setRedButton();
        greenButton.setVisibility(View.VISIBLE);
    }

    private void setRedButton() {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) greenButton.getLayoutParams();
        params.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        redButton.setLayoutParams(params);
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
