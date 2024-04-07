package com.ingsw.dietiDeals24.ui.home.myBids.myBidDetails;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MakePaymentController;
import com.ingsw.dietiDeals24.controller.MyBidDetailsController;
import com.ingsw.dietiDeals24.controller.SearchAuctionDetailsController;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.SilentBid;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.BidStatus;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.profile.other.OtherUserProfileActivity;
import com.ingsw.dietiDeals24.ui.home.searchAuctions.makePayment.activities.MakePaymentActivity;
import com.ingsw.dietiDeals24.utility.NumberFormatter;
import com.ingsw.dietiDeals24.utility.PopupGenerator;
import com.ingsw.dietiDeals24.utility.ToastManager;

import java.util.concurrent.ExecutionException;

public class MySilentBidDetailsActivity extends MyBidDetailsActivity {

    private SilentBid bid;

    private User owner;

    private SilentAuction auction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bid = (SilentBid) MyBidDetailsController.getBid();

        auction = bid.getSilentAuction();

        owner = auction.getOwner();

        setupOwnerButton();
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

        categoryTextView.setText(Category.toItalianString(auction.getCategory()));

        titleTextView.setText(auction.getTitle());

        wearTextView.setText(Wear.toItalianString(auction.getWear()));

        descriptionTextView.setText(auction.getDescription());

        priceTextView.setVisibility(View.GONE);

        specificInformation1TextView.setText("Scade il: " + SearchAuctionDetailsController.getFormattedExpirationDate(auction));

        specificInformation2TextView.setText("I compratori hanno: " + SearchAuctionDetailsController.getWithdrawalTimeText(auction) + " per ritirare le offerte");

        specificInformation3TextView.setVisibility(View.GONE);
        specificInformation4TextView.setVisibility(View.GONE);

        setButton();

    }

    private void setButton() {
        BidStatus status = bid.getStatus();

        if (status == BidStatus.ACCEPTED) {
            setupAcceptedBidButton();

        } else if (status == BidStatus.PENDING) {
            setupPendingBidButton();

        } else if (status == BidStatus.DECLINED) {
            setupDeclinedBidButton();

        } else if (status == BidStatus.EXPIRED){
            setupExpiredBidButton();
        } else {
            setupPayedBidButton();
        }

    }

    private void setupAcceptedBidButton() {

        bidButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        bidButton.setText("Effettua il pagamento di: " + NumberFormatter.formatPrice(bid.getMoneyAmount()));

        Drawable shoppingCartIcon = ContextCompat.getDrawable(this, R.drawable.ic_cart_shopping_24dp);
        shoppingCartIcon.setBounds(0, 0, shoppingCartIcon.getIntrinsicWidth(), shoppingCartIcon.getIntrinsicHeight());
        bidButton.setCompoundDrawablesWithIntrinsicBounds(null, null, shoppingCartIcon, null);

        bidButton.setOnClickListener(v -> {

            MakePaymentController.setBid(bid);
            MakePaymentController.setAuction(bid.getSilentAuction());
            Intent intent = new Intent(v.getContext(), MakePaymentActivity.class);
            v.getContext().startActivity(intent);
        });
    }

    private void setupPendingBidButton() {

        bidButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));

        Boolean isWithdrawable;

        try {

            isWithdrawable = MyBidDetailsController.isSilentBidWithdrawable(bid.getIdBid()).get();

            if (isWithdrawable) {

                bidButton.setText("Ritira l'offerta");

                Drawable trashIcon = ContextCompat.getDrawable(this, R.drawable.ic_delete_forever);
                trashIcon.setBounds(0, 0, trashIcon.getIntrinsicWidth(), trashIcon.getIntrinsicHeight());
                bidButton.setCompoundDrawablesWithIntrinsicBounds(null, null, trashIcon, null);

                bidButton.setOnClickListener(v -> {
                    PopupGenerator.areYouSureToDeleteBidPopup(this, bid);
                });

            } else {

                bidButton.setText("Offerta non ritirabile");
            }

        } catch (ExecutionException e) {
            ToastManager.showToast(getApplicationContext(), e.getCause().getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    private void setupDeclinedBidButton() {
        bidButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        bidButton.setText("Offerta rifiutata");

        Drawable trashIcon = ContextCompat.getDrawable(this, R.drawable.ic_delete_forever);
        trashIcon.setBounds(0, 0, trashIcon.getIntrinsicWidth(), trashIcon.getIntrinsicHeight());
        bidButton.setCompoundDrawablesWithIntrinsicBounds(null, null, trashIcon, null);


        bidButton.setOnClickListener(v -> {
            PopupGenerator.areYouSureToDeleteBidPopup(this, bid);
        });
    }

    private void setupExpiredBidButton() {
        bidButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        bidButton.setText("Asta scaduta");

        Drawable trashIcon = ContextCompat.getDrawable(this, R.drawable.ic_delete_forever);
        trashIcon.setBounds(0, 0, trashIcon.getIntrinsicWidth(), trashIcon.getIntrinsicHeight());
        bidButton.setCompoundDrawablesWithIntrinsicBounds(null, null, trashIcon, null);

        bidButton.setOnClickListener(v -> {
            PopupGenerator.areYouSureToDeleteBidPopup(this, bid);
        });
    }

    private void setupPayedBidButton() {

        Drawable shoppingCartIcon = ContextCompat.getDrawable(this, R.drawable.ic_cart_shopping_24dp);
        shoppingCartIcon.setBounds(0, 0, shoppingCartIcon.getIntrinsicWidth(), shoppingCartIcon.getIntrinsicHeight());
        bidButton.setCompoundDrawablesWithIntrinsicBounds(null, null, shoppingCartIcon, null);

        bidButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        bidButton.setText("Acquistato per: " + NumberFormatter.formatPrice(bid.getMoneyAmount()));
    }

    private void setupOwnerButton() {

        ownerButton = findViewById(R.id.auction_owner_button_my_bid_details);
        ownerButton.setText("Annuncio di: " + owner.getName());
        ownerButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), OtherUserProfileActivity.class);
            intent.putExtra("otherUser", owner);
            v.getContext().startActivity(intent);
        });
    }

    private void setupBottomSheetDialog() {

        questionMarkAuctionType.setText(R.string.silent_auction_question);
        questionMarkExplanationAuctionType.setText(R.string.silent_auction_description);
    }
}
