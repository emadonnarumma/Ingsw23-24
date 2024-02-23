package com.ingsw.dietiDeals24.ui.utility.recyclerViews.myAuctions;

import android.net.Uri;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.controller.MyAuctionsController;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.ui.utility.slider.adapter.SmallScreenSliderAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SilentAuctionViewHolder extends RecyclerView.ViewHolder {

    private TextView containerTextView, titleTextView, auctionTypeTextView,
            auctionStatusTextView, expirationDateTextView;

    private ImageView auctionTypeIconImageView, auctionStatusIconImageView, expirationDateIconImageView;

    private SliderView imagesSliderView;

    private CardView cardView;


    public SilentAuctionViewHolder(@NonNull View itemView) {
        super(itemView);
        containerTextView = itemView.findViewById(R.id.container_text_view_item_silent_auction);
        titleTextView = itemView.findViewById(R.id.title_text_view_item_silent_auction);
        auctionTypeTextView = itemView.findViewById(R.id.auction_type_text_view_item_silent_auction);
        auctionStatusTextView = itemView.findViewById(R.id.auction_status_text_view_item_silent_auction);
        expirationDateTextView = itemView.findViewById(R.id.expiration_date_text_view_item_silent_auction);
        auctionTypeIconImageView = itemView.findViewById(R.id.auction_type_icon_item_silent_auction);
        auctionStatusIconImageView = itemView.findViewById(R.id.auction_status_icon_item_silent_auction);
        expirationDateIconImageView = itemView.findViewById(R.id.expiration_date_icon_item_silent_auction);
        imagesSliderView = itemView.findViewById(R.id.slider_item_silent_auction);
        cardView = itemView.findViewById(R.id.card_view_item_silent_auction);
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
        SmallScreenSliderAdapter adapter = new SmallScreenSliderAdapter(itemView.getContext());
        imagesSliderView.setSliderAdapter(adapter);

        ArrayList<Uri> images = new ArrayList<>();
        for (int i = 0; i < silentAuction.getImages().size(); i++) {
            images.add(base64ToUri(silentAuction.getImages().get(i).getBase64Data()));
        }

        adapter.renewItems(images);
    }

    public Uri base64ToUri(String base64) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        File tempFile;
        try {
            tempFile = File.createTempFile("image", "jpg", itemView.getContext().getCacheDir());
            tempFile.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(decodedString);
            fos.close();
            return Uri.fromFile(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}