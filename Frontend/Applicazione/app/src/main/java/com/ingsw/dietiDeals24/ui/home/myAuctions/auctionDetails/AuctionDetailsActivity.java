package com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ScrollView;
import androidx.cardview.widget.CardView;

import com.ingsw.dietiDeals24.controller.ImageController;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.ui.utility.slider.adapter.SmallScreenSliderAdapter;
import com.smarteist.autoimageslider.SliderView;
import com.ingsw.dietiDeals24.R;

import java.util.ArrayList;

public abstract class AuctionDetailsActivity extends AppCompatActivity {

    protected ScrollView scrollViewAuctionDetails;
    protected Button greenButton, redButton;
    protected ImageButton questionMarkButtonAuctionDetails;
    protected TextView auctionTypeTextViewAuctionDetails, categoryTextViewAuctionDetails, titleTextViewAuctionDetails, auctionStatusTextViewAuctionDetails, wearTextViewAuctionDetails, descriptionTextViewAuctionDetails, priceTextViewAuctionDetails, specificInformation1TextViewAuctionDetails, specificInformation2TextViewAuctionDetails, specificInformation3TextViewAuctionDetails;
    protected CardView cardViewAuctionDetails;
    protected SliderView sliderViewAuctionDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_details);
        setupScrollView();
        setupButtons();
        setupTextViews();
        setupSliderView();
    }

    private void setupSliderView() {
        cardViewAuctionDetails = findViewById(R.id.card_view_auction_details);
        sliderViewAuctionDetails = findViewById(R.id.slider_view_auction_details);
    }

    private void setupScrollView() {
        scrollViewAuctionDetails = findViewById(R.id.scroll_view_auction_details);
    }

    private void setupTextViews() {
        auctionTypeTextViewAuctionDetails = findViewById(R.id.auction_type_text_view_auction_details);
        categoryTextViewAuctionDetails = findViewById(R.id.category_text_view_auction_details);
        titleTextViewAuctionDetails = findViewById(R.id.title_text_view_auction_details);
        auctionStatusTextViewAuctionDetails = findViewById(R.id.auction_status_text_view_auction_details);
        wearTextViewAuctionDetails = findViewById(R.id.wear_text_view_auction_details);
        descriptionTextViewAuctionDetails = findViewById(R.id.description_text_view_auction_details);
        priceTextViewAuctionDetails = findViewById(R.id.price_text_view_auction_details);
        specificInformation1TextViewAuctionDetails = findViewById(R.id.specific_information_1_text_view_auction_details);
        specificInformation2TextViewAuctionDetails = findViewById(R.id.specific_information_2_text_view_auction_details);
        specificInformation3TextViewAuctionDetails = findViewById(R.id.specific_information_3_text_view_auction_details);
    }

    private void setupButtons() {
        questionMarkButtonAuctionDetails = findViewById(R.id.iquestion_mark_button_auction_details);
        greenButton = findViewById(R.id.green_button);
        redButton = findViewById(R.id.red_button);
    }

    protected void bindImages(Auction auction) {
        SmallScreenSliderAdapter adapter = new SmallScreenSliderAdapter(getApplicationContext());
        sliderViewAuctionDetails.setSliderAdapter(adapter);

        ArrayList<Uri> images = new ArrayList<>();
        for (int i = 0; i < auction.getImages().size(); i++) {
            images.add(ImageController.base64ToUri(auction.getImages().get(i).getBase64Data(), getApplicationContext()));
        }

        adapter.renewItems(images);
    }
}