package com.ingsw.dietiDeals24.ui.home.searchAuctions.searchAuctionDetails;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.ImageController;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.OnNavigateToHomeActivityFragmentListener;
import com.ingsw.dietiDeals24.ui.utility.slider.adapter.SmallScreenSliderAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public abstract class SearchAuctionDetailsActivity extends AppCompatActivity implements OnNavigateToHomeActivityFragmentListener {

    protected ScrollView scrollView;
    protected Button greenButton;
    protected ImageButton questionMarkButton;
    protected CircularProgressButton ownerButton;

    protected TextView auctionTypeTextView, categoryTextView,
            titleTextView, wearTextView,
            descriptionTextView, priceTextView,
            specificInformation1TextView, specificInformation2TextView,
            specificInformation3TextView, specificInformation4TextView;

    protected CardView cardView;
    protected SliderView sliderView;

    protected ProgressBar progressBar;

    protected BottomSheetDialog bottomSheetQuestionMarkDialog;
    protected TextView questionMarkAuctionType, questionMarkExplanationAuctionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_auction_details);

        setupBottomSheetDialog();
        setupProgressBar();
        setupActionBar();
        setupSliderView();
        setupScrollView();
        setupButtons();
        setupTextViews();

    }

    private void setupProgressBar() {
        progressBar = findViewById(R.id.progress_bar_search_auction_details);
        progressBar.setVisibility(View.GONE);
    }

    private void setupActionBar() {
        setSupportActionBar(findViewById(R.id.toolbar_search_auction_details));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupSliderView() {
        cardView = findViewById(R.id.card_view_search_auction_details);
        sliderView = findViewById(R.id.slider_view_search_auction_details);
    }

    private void setupScrollView() {
        scrollView = findViewById(R.id.scroll_view_search_auction_details);
    }

    private void setupButtons() {
        questionMarkButton = findViewById(R.id.question_mark_button_search_auction_details);

        questionMarkButton.setOnClickListener(v -> bottomSheetQuestionMarkDialog.show());

        greenButton = findViewById(R.id.search_green_button);
        ownerButton = findViewById(R.id.auction_owner_button_search_auction_details);
    }

    private void setupBottomSheetDialog() {
        bottomSheetQuestionMarkDialog = new BottomSheetDialog(this);
        bottomSheetQuestionMarkDialog.setContentView(R.layout.bottom_sheet_dialog_questionmark_layout);

        questionMarkAuctionType = bottomSheetQuestionMarkDialog.findViewById(R.id.question_bottom_sheet_text_view);
        questionMarkExplanationAuctionType = bottomSheetQuestionMarkDialog.findViewById(R.id.question_bottom_sheet_text_view_description);
    }

    private void setupTextViews() {
        auctionTypeTextView = findViewById(R.id.auction_type_text_view_search_auction_details);
        categoryTextView = findViewById(R.id.category_text_view_search_auction_details);
        titleTextView = findViewById(R.id.title_text_view_search_auction_details);
        wearTextView = findViewById(R.id.wear_text_view_search_auction_details);
        descriptionTextView = findViewById(R.id.description_text_view_search_auction_details);
        priceTextView = findViewById(R.id.price_text_view_search_auction_details);
        specificInformation1TextView = findViewById(R.id.specific_information_1_text_view_search_auction_details);
        specificInformation2TextView = findViewById(R.id.specific_information_2_text_view_search_auction_details);
        specificInformation3TextView = findViewById(R.id.specific_information_3_text_view_search_auction_details);
        specificInformation4TextView = findViewById(R.id.specific_information_4_text_view_search_auction_details);
    }



    protected void bindImages(Auction auction) {
        SmallScreenSliderAdapter adapter = new SmallScreenSliderAdapter(getApplicationContext());
        sliderView.setSliderAdapter(adapter);

        ArrayList<Uri> images = new ArrayList<>();
        for (int i = 0; i < auction.getImages().size(); i++) {
            images.add(ImageController.base64ToUri(auction.getImages().get(i).getBase64Data(), getApplicationContext()));
        }

        adapter.renewItems(images);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
