package com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.downwardAuction;

import android.content.Intent;
import android.os.Bundle;

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
import com.ingsw.dietiDeals24.ui.home.HomeActivity;
import com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder.AuctionHolder;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes.GeneralAuctionAttributesViewModel;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.AuctionDetailsActivity;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FailedDownwardAuctionActivity extends AuctionDetailsActivity {
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
        scrollViewAuctionDetails.setBackground(AppCompatResources.getDrawable(this, R.color.cyan));
        auctionTypeTextViewAuctionDetails.setText(AuctionType.toItalianString(auction.getType()));
        categoryTextViewAuctionDetails.setText(Category.toItalianString(auction.getCategory()));
        titleTextViewAuctionDetails.setText(auction.getTitle());

        auctionStatusTextViewAuctionDetails.setText(AuctionStatus.toItalianString(auction.getStatus()));
        auctionStatusTextViewAuctionDetails.setTextColor(ContextCompat.getColor(this, R.color.red));
        auctionStatusTextViewAuctionDetails.setStrokeColor(R.color.black);

        wearTextViewAuctionDetails.setText(Wear.toItalianString(auction.getWear()));
        descriptionTextViewAuctionDetails.setText(auction.getDescription());
        priceTextViewAuctionDetails.setText("Prezzo attuale: " + auction.getCurrentPrice() + "€");

        specificInformation1TextViewAuctionDetails.setText("Decremento: " + auction.getDecrementAmount() + "€");
        specificInformation2TextViewAuctionDetails.setText(MyAuctionDetailsController.getNextDecrementTimeText(auction));
        specificInformation3TextViewAuctionDetails.setText("Prezzo minimo segreto " + auction.getSecretMinimumPrice());
        setButtons();
    }

    private void setButtons() {
        setRedButton();
        setGreenButton();
    }

    private void setGreenButton() {
        greenButton.setBackground(AppCompatResources.getDrawable(this, R.drawable.square_shape_green));
        greenButton.setText("RILANCIA");
        greenButton.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Conferma")
                    .setMessage("Sei sicuro di voler rilanciare l'asta?")
                    .setPositiveButton("Si", (dialog, which) -> {
                        GeneralAuctionAttributesViewModel viewModel = GeneralAuctionAttributesViewModel.getInstance();
                        AuctionHolder auctionHolder = new AuctionHolder(
                                auction.getTitle(),
                                new ArrayList<>(ImageController.convertImageListToUriList(auction.getImages(), getApplicationContext())),
                                auction.getDescription(),
                                auction.getWear(),
                                auction.getCategory()
                        );

                        viewModel.setNewAuction(new MutableLiveData<>(auctionHolder));

                        try {
                            MyAuctionDetailsController.deleteAuction(auction.getIdAuction()).get();
                        } catch (ExecutionException e) {
                            ToastManager.showToast(getApplicationContext(), e.getCause().getMessage());
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        Intent intent = new Intent(this, HomeActivity.class);
                        intent.putExtra("openSilent", "SilentAuctionAttributesFragment");
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
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
