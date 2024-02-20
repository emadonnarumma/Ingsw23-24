package com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.silentAuction;

import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.ingsw.dietiDeals24.R;

import com.ingsw.dietiDeals24.controller.ImageController;
import com.ingsw.dietiDeals24.controller.MyAuctionDetailsController;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.AuctionDetailsActivity;
import com.ingsw.dietiDeals24.ui.utility.slider.adapter.SmallScreenSliderAdapter;

import java.util.ArrayList;

public class SuccessfullSilentAuctionActivity extends AuctionDetailsActivity {

    private SilentAuction auction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auction = (SilentAuction) getIntent().getSerializableExtra("auction");
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
        auctionTypeTextViewAuctionDetails.setText(AuctionType.toItalianString(auction.getType()));
        categoryTextViewAuctionDetails.setText(Category.toItalianString(auction.getCategory()));
        titleTextViewAuctionDetails.setText(auction.getTitle());
        auctionStatusTextViewAuctionDetails.setText(AuctionStatus.toItalianString(auction.getStatus()));
        wearTextViewAuctionDetails.setText(Wear.toItalianString(auction.getWear()));
        descriptionTextViewAuctionDetails.setText(auction.getDescription());
        specificInformation1TextViewAuctionDetails.setText("scade il: " + auction.getExpirationDate());
        specificInformation2TextViewAuctionDetails.setText(MyAuctionDetailsController.getWithdrawalTimeText(auction));
        redButton.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        redButton.requestLayout();
        redButton.setBackground(AppCompatResources.getDrawable(this, R.drawable.square_shape_red));
        redButton.setText("RIMUOVI");
    }
}
