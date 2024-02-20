package com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.silentAuction;

import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;

import com.ingsw.dietiDeals24.controller.MyAuctionDetailsController;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder.ImageConverter;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.AuctionDetailsActivity;
import com.ingsw.dietiDeals24.ui.utility.slider.adapter.SmallScreenSliderAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        bindImages(auction);
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
    }

    private void bindImages(SilentAuction silentAuction) {
        SmallScreenSliderAdapter adapter = new SmallScreenSliderAdapter(getApplicationContext());
        sliderViewAuctionDetails.setSliderAdapter(adapter);

        ArrayList<Uri> images = new ArrayList<>();
        for (int i = 0; i < silentAuction.getImages().size(); i++) {
            images.add(ImageConverter.base64ToUri(silentAuction.getImages().get(i).getBase64Data(), getApplicationContext()));
        }

        adapter.renewItems(images);
    }
}
