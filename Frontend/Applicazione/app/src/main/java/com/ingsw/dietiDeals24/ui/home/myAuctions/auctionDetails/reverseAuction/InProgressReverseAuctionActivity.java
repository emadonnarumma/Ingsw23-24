package com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.reverseAuction;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MyAuctionDetailsController;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.AuctionDetailsActivity;
import com.ingsw.dietiDeals24.ui.utility.NumberFormatter;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.ui.utility.recyclerViews.auctionBids.AuctionBidAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class InProgressReverseAuctionActivity extends AuctionDetailsActivity {
    private ReverseAuction auction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auction = (ReverseAuction) MyAuctionDetailsController.getAuction();
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

        auctionStatusTextView.setText(AuctionStatus.toItalianString(auction.getStatus()));
        auctionStatusTextView.setTextColor(ContextCompat.getColor(this, R.color.yellow));
        auctionStatusTextView.setStrokeColor(R.color.black);

        wearTextView.setText(Wear.toItalianString(auction.getWear()));
        descriptionTextView.setText(auction.getDescription());
        priceTextView.setText("Prezzo iniziale: " + NumberFormatter.formatPrice(auction.getStartingPrice()));

        specificInformation1TextView.setText("Scade il: " + MyAuctionDetailsController.getFormattedExpirationDate(auction));
        specificInformation2TextView.setVisibility(View.GONE);
        specificInformation3TextView.setVisibility(View.GONE);
        specificInformation4TextView.setVisibility(View.GONE);
        setButtons();
    }

    private void setButtons() {
        setRedButton();
        setGreenButton();
    }

    private void setGreenButton() {
        greenButton.setText("VISUALIZZA OFFERTA CORRENTE");
        greenButton.setOnClickListener(v -> {

            new Thread(() -> {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.VISIBLE);
                });
                try {
                    List<ReverseBid> bids = new ArrayList<>();
                    ReverseBid reverseBid = MyAuctionDetailsController.getMinPricedReverseBidByAuctionId(auction.getIdAuction()).get();
                    if (reverseBid != null) {
                        bids.add(reverseBid);
                    }
                    runOnUiThread(() -> {
                        if (bids.isEmpty()) {
                            progressBar.setVisibility(View.GONE);
                            emptyBidsTextView.setText("Nessuna offerta disponibile");
                            emptyBidsTextView.setVisibility(View.VISIBLE);
                            bidsRecyclerView.setVisibility(View.GONE);
                            bottomSheetDialog.show();
                        } else {
                            emptyBidsTextView.setVisibility(View.GONE);
                            bidsRecyclerView.setAdapter(new AuctionBidAdapter(bids, this, true));
                            bidsRecyclerView.setLayoutManager(new LinearLayoutManager(InProgressReverseAuctionActivity.this));
                            bidsRecyclerView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            bottomSheetDialog.show();
                        }
                    });
                } catch (ExecutionException e) {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        ToastManager.showToast(getApplicationContext(), e.getCause().getMessage());
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        });
    }

    private void setRedButton() {
        redButton.setBackground(AppCompatResources.getDrawable(this, R.drawable.square_shape_red));
        redButton.setText("CANCELLA L'ASTA");
        redButton.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Conferma")
                    .setMessage("Sei sicuro di voler cancellare l'asta?")
                    .setPositiveButton("Si", (dialog, which) -> {
                        try {
                            MyAuctionDetailsController.deleteAuction(auction.getIdAuction()).get();
                            finish();
                        } catch (ExecutionException e) {
                            ToastManager.showToast(getApplicationContext(), e.getCause().getMessage());
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}
