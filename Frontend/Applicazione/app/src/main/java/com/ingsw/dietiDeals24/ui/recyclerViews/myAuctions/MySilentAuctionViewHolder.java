package com.ingsw.dietiDeals24.ui.recyclerViews.myAuctions;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.controller.ImageController;
import com.ingsw.dietiDeals24.controller.MyAuctionsController;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.ui.recyclerViews.auctionImages.SmallScreenImagesAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class MySilentAuctionViewHolder extends RecyclerView.ViewHolder {

    private TextView containerTextView, titleTextView, auctionTypeTextView,
            auctionStatusTextView, expirationDateTextView;

    private ImageView auctionTypeIconImageView, auctionStatusIconImageView, expirationDateIconImageView;

    private SliderView imagesSliderView;

    private CardView cardView;


    public MySilentAuctionViewHolder(@NonNull View itemView) {
        super(itemView);
        containerTextView = itemView.findViewById(R.id.container_text_view_item_my_silent_auction);
        titleTextView = itemView.findViewById(R.id.title_text_view_item_my_silent_auction);
        auctionTypeTextView = itemView.findViewById(R.id.auction_type_text_view_item_my_silent_auction);
        auctionStatusTextView = itemView.findViewById(R.id.auction_status_text_view_item_my_silent_auction);
        expirationDateTextView = itemView.findViewById(R.id.expiration_date_text_view_item_my_silent_auction);
        auctionTypeIconImageView = itemView.findViewById(R.id.auction_type_icon_item_my_silent_auction);
        auctionStatusIconImageView = itemView.findViewById(R.id.auction_status_icon_item_my_silent_auction);
        expirationDateIconImageView = itemView.findViewById(R.id.expiration_date_icon_item_my_silent_auction);
        imagesSliderView = itemView.findViewById(R.id.slider_item_my_silent_auction);
        cardView = itemView.findViewById(R.id.card_view_item_my_silent_auction);
    }

    public void bind(SilentAuction silentAuction) {
        titleTextView.setText(silentAuction.getTitle());
        auctionTypeTextView.setText(AuctionType.toItalianString(silentAuction.getType()));

        AuctionStatus status = silentAuction.getStatus();
        switch (status) {
            case IN_PROGRESS:
                auctionStatusTextView.setText(R.string.in_progress_phrase);
                auctionStatusIconImageView.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.circular_yellow));
                break;

            case SUCCESSFUL:
                auctionStatusTextView.setText(R.string.sucessfull_phrase);
                auctionStatusIconImageView.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.circular_green));
                break;

            case FAILED:
                auctionStatusTextView.setText(R.string.failed_phrase);
                auctionStatusIconImageView.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.circular_red));
                break;

            default:
                break;
        }

        expirationDateTextView.setText(MyAuctionsController.getFormattedExpirationDate(silentAuction));

        if (silentAuction.getImages() != null) {
            bindImages(silentAuction);
        }
    }

    private void bindImages(SilentAuction silentAuction) {
        SmallScreenImagesAdapter adapter = new SmallScreenImagesAdapter(itemView.getContext());
        imagesSliderView.setSliderAdapter(adapter);

        ArrayList<Uri> images = new ArrayList<>();
        if (silentAuction.getImages() != null && !silentAuction.getImages().isEmpty()) {
            for (int i = 0; i < silentAuction.getImages().size(); i++) {
                images.add(ImageController.base64ToUri(silentAuction.getImages().get(i).getBase64Data(), itemView.getContext()));
            }
        } else {
            Uri defaultImageUri = Uri.parse("android.resource://com.ingsw.dietiDeals24/" + R.drawable.no_image_available);
            images.add(defaultImageUri);
        }

        adapter.renewItems(images);
    }
}