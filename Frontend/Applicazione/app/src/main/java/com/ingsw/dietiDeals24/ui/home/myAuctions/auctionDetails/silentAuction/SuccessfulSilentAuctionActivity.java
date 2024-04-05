package com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.silentAuction;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ingsw.dietiDeals24.R;

import com.ingsw.dietiDeals24.controller.MyAuctionDetailsController;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.SilentBid;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.AuctionDetailsActivity;
import com.ingsw.dietiDeals24.utility.PopupGeneratorOf;
import com.ingsw.dietiDeals24.utility.ToastManager;
import com.ingsw.dietiDeals24.utility.recyclerViews.auctionBids.AuctionBidAdapter;
import com.saadahmedsoft.popupdialog.PopupDialog;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class SuccessfulSilentAuctionActivity extends AuctionDetailsActivity {

    private SilentAuction auction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auction = (SilentAuction) MyAuctionDetailsController.getAuction();

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
        scrollView.setBackground(AppCompatResources.getDrawable(this, R.color.purple));
        auctionTypeTextView.setText(AuctionType.toItalianString(auction.getType()));
        auctionTypeTextView.setBackground(AppCompatResources.getDrawable(this, R.color.purple));

        auctionStatusTextView.setText(AuctionStatus.toItalianString(auction.getStatus()));
        auctionStatusTextView.setTextColor(ContextCompat.getColor(this, R.color.green));
        auctionStatusTextView.setStrokeColor(R.color.black);

        categoryTextView.setText(Category.toItalianString(auction.getCategory()));
        titleTextView.setText(auction.getTitle());
        wearTextView.setText(Wear.toItalianString(auction.getWear()));
        descriptionTextView.setText(auction.getDescription());
        priceTextView.setVisibility(View.GONE);

        specificInformation1TextView.setText("Scadeva il: " + MyAuctionDetailsController.getFormattedExpirationDate(auction));
        specificInformation2TextView.setText("I compratori avevano: " + MyAuctionDetailsController.getWithdrawalTimeText(auction) + " per ritirare le offerte");
        specificInformation3TextView.setVisibility(View.GONE);
        specificInformation4TextView.setVisibility(View.GONE);

        setGreenButton();
        setRedButton();
    }

    private void setGreenButton() {
        greenButton.setText("VISUALIZZA DETTAGLI DELL'AFFARE");
        greenButton.setOnClickListener(v -> {

            PopupDialog loading = PopupGeneratorOf.loadingPopup(this);
            new Thread(() -> {
                try {
                    SilentBid bid = MyAuctionDetailsController.getWinningSilentBidByAuctionId(auction.getIdAuction()).get();
                    runOnUiThread(() -> {
                        emptyBidsTextView.setVisibility(View.GONE);
                        bidsRecyclerView.setAdapter(new AuctionBidAdapter(List.of(bid), this, false));
                        bidsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        redButton.setBackground(AppCompatResources.getDrawable(this, R.drawable.square_shape_red));
        redButton.setText("RIMUOVI ASTA");
        redButton.setOnClickListener(v -> {
            PopupGeneratorOf.areYouSureToDeleteAuctionPopup(this, auction);
        });
    }

    private void setupBottomSheetDialog() {

        questionMarkAuctionType.setText(R.string.silent_auction_question);
        questionMarkExplanationAuctionType.setText(R.string.silent_auction_description);
    }
}
