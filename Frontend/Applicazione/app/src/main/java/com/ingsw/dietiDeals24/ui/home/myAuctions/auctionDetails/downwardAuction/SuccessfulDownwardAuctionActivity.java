package com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.downwardAuction;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MyAuctionDetailsController;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.DownwardBid;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.AuctionDetailsActivity;
import com.ingsw.dietiDeals24.ui.recyclerViews.auctionBids.AuctionBidAdapter;
import com.ingsw.dietiDeals24.utility.NumberFormatter;
import com.ingsw.dietiDeals24.utility.PopupGenerator;
import com.ingsw.dietiDeals24.utility.ToastManager;
import com.saadahmedsoft.popupdialog.PopupDialog;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SuccessfulDownwardAuctionActivity extends AuctionDetailsActivity {

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
        auctionStatusTextView.setTextColor(ContextCompat.getColor(this, R.color.green));
        auctionStatusTextView.setStrokeColor(R.color.black);

        wearTextView.setText(Wear.toItalianString(auction.getWear()));
        descriptionTextView.setText(auction.getDescription());
        priceTextView.setText("Prezzo attuale: " + NumberFormatter.formatPrice(auction.getCurrentPrice()));

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
        greenButton.setText("VISUALIZZA DETTAGLI DELL'AFFARE");
        greenButton.setOnClickListener(v -> {
            PopupDialog loading = PopupGenerator.loadingPopup(this);
            new Thread(() -> {
                try {
                    List<DownwardBid> bids = List.of(MyAuctionDetailsController.getWinningDownwardBidByAuctionId(auction.getIdAuction()).get());
                    runOnUiThread(() -> {
                        emptyBidsTextView.setVisibility(View.GONE);
                        bidsRecyclerView.setAdapter(new AuctionBidAdapter(bids, this, true));
                        bidsRecyclerView.setLayoutManager(new LinearLayoutManager(SuccessfulDownwardAuctionActivity.this));
                        bidsRecyclerView.setVisibility(View.VISIBLE);
                        bottomSheetDialog.show();
                    });
                } catch (ExecutionException e) {
                    runOnUiThread(() -> {
                        ToastManager.showToast(getApplicationContext(), e.getCause().getMessage());
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    runOnUiThread(loading::dismissDialog);
                }
            }).start();
        });
    }

    private void setRedButton() {
        redButton.setText("CANCELLA L'ASTA");
        redButton.setOnClickListener(v -> {
            PopupGenerator.areYouSureToDeleteAuctionPopup(this, auction);
        });
    }

    private void setupBottomSheetDialog() {

        questionMarkAuctionType.setText(R.string.downward_auction_question);
        questionMarkExplanationAuctionType.setText(R.string.downward_auction_description);
    }
}
