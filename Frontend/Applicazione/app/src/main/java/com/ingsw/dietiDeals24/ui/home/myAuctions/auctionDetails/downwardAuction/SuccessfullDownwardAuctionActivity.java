package com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.downwardAuction;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
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
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.ui.utility.recyclerViews.auctionBids.AuctionBidAdapter;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SuccessfullDownwardAuctionActivity extends AuctionDetailsActivity {

    private DownwardAuction auction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auction = (DownwardAuction) MyAuctionDetailsController.getAuction();
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
        auctionStatusTextView.setTextColor(ContextCompat.getColor(this, R.color.yellow));
        auctionStatusTextView.setStrokeColor(R.color.black);

        wearTextView.setText(Wear.toItalianString(auction.getWear()));
        descriptionTextView.setText(auction.getDescription());
        priceTextView.setText("Prezzo attuale: " + auction.getCurrentPrice() + "€");

        specificInformation1TextView.setText("Decremento: " + auction.getDecrementAmount() + "€");
        specificInformation2TextView.setText(MyAuctionDetailsController.getNextDecrementTimeText(auction));
        specificInformation3TextView.setText("Prezzo minimo segreto " + auction.getSecretMinimumPrice());
        setButtons();
    }

    private void setButtons() {
        setRedButton();
        setGreenButton();
    }

    private void setGreenButton() {
        greenButton.setText("VISUALIZZA DETTAGLI DELL'AFFARE");
        greenButton.setOnClickListener(v -> {

            new Thread(() -> {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.VISIBLE);
                });
                try {
                    List<DownwardBid> bids = List.of(MyAuctionDetailsController.getWinningDownwardBidByAuctionId(auction.getIdAuction()).get());
                    runOnUiThread(() -> {
                        emptyBidsTextView.setVisibility(View.GONE);
                        bidsRecyclerView.setAdapter(new AuctionBidAdapter(bids, this, true));
                        bidsRecyclerView.setLayoutManager(new LinearLayoutManager(SuccessfullDownwardAuctionActivity.this));
                        bidsRecyclerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        bottomSheetDialog.show();
                    });
                } catch (ExecutionException e) {
                    runOnUiThread(() -> {
                        ToastManager.showToast(getApplicationContext(), e.getCause().getMessage());
                        progressBar.setVisibility(View.GONE);
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        });
    }

    private void setRedButton() {
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
