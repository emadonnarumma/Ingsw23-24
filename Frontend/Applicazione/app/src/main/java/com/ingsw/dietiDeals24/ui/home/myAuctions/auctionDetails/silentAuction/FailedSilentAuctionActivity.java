package com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.silentAuction;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.ImageController;
import com.ingsw.dietiDeals24.controller.MyAuctionDetailsController;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder.AuctionHolder;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes.GeneralAuctionAttributesViewModel;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.AuctionDetailsActivity;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FailedSilentAuctionActivity extends AuctionDetailsActivity {
    private SilentAuction auction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auction = (SilentAuction) MyAuctionDetailsController.getAuction();
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
        categoryTextView.setText(Category.toItalianString(auction.getCategory()));
        titleTextView.setText(auction.getTitle());
        auctionStatusTextView.setText(AuctionStatus.toItalianString(auction.getStatus()));
        wearTextView.setText(Wear.toItalianString(auction.getWear()));
        descriptionTextView.setText(auction.getDescription());

        auctionStatusTextView.setText(AuctionStatus.toItalianString(auction.getStatus()));
        auctionStatusTextView.setTextColor(ContextCompat.getColor(this, R.color.red));
        auctionStatusTextView.setStrokeColor(R.color.white);

        specificInformation1TextView.setText("scade il: " + MyAuctionDetailsController.getFormattedExpirationDate(auction));
        specificInformation2TextView.setText(MyAuctionDetailsController.getWithdrawalTimeText(auction));

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

                        onNavigateToHomeActivityFragmentRequest("FailedAuctionAttributesFragment", getApplicationContext());
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
