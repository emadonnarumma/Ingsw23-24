package com.ingsw.dietiDeals24.ui.utility.recyclerViews.searchAuctions;

import android.net.Uri;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MyAuctionsController;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.ui.utility.slider.adapter.SmallScreenSliderAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SearchReverseAuctionViewHolder extends RecyclerView.ViewHolder {

    private TextView containerTextView, titleTextView,
            auctionTypeTextView, currentBidTextView,
            auctionCategoryTextView, expirationDateTextView;

    private ImageView auctionTypeIconImageView, expirationDateIconImageView;

    private SliderView imagesSliderView;

    private CardView cardView;

    public SearchReverseAuctionViewHolder(@NonNull View itemView) {
        super(itemView);

        containerTextView = itemView.findViewById(R.id.container_text_view_item_reverse_auction);
        titleTextView = itemView.findViewById(R.id.title_text_view_item_reverse_auction);
        auctionTypeTextView = itemView.findViewById(R.id.auction_type_text_view_item_reverse_auction);
        auctionCategoryTextView = itemView.findViewById(R.id.auction_category_text_view_item_reverse_auction);
        expirationDateTextView = itemView.findViewById(R.id.expiration_date_text_view_item_reverse_auction);
        currentBidTextView = itemView.findViewById(R.id.current_bid_text_view_item_reverse_auction);
        auctionTypeIconImageView = itemView.findViewById(R.id.auction_type_icon_item_reverse_auction);
        expirationDateIconImageView = itemView.findViewById(R.id.expiration_date_icon_item_reverse_auction);
        imagesSliderView = itemView.findViewById(R.id.slider_item_reverse_auction);
        cardView = itemView.findViewById(R.id.card_view_item_reverse_auction);
    }

    public void bind(ReverseAuction reverseAuction) {
        titleTextView.setText(reverseAuction.getTitle());
        auctionTypeTextView.setText(AuctionType.toItalianString(reverseAuction.getType()));
        currentBidTextView.setText(String.valueOf(reverseAuction.getStartingPrice()) + " €");

        expirationDateTextView.setText(MyAuctionsController.getFormattedExpirationDate(reverseAuction));

        expirationDateTextView.setText(MyAuctionsController.getFormattedExpirationDate(reverseAuction));
        bindImages(reverseAuction);
    }

    private void bindImages(ReverseAuction reverseAuction) {
        SmallScreenSliderAdapter adapter = new SmallScreenSliderAdapter(itemView.getContext());
        imagesSliderView.setSliderAdapter(adapter);

        ArrayList<Uri> images = new ArrayList<>();
        if (reverseAuction.getImages() != null && !reverseAuction.getImages().isEmpty()) {
            for (int i = 0; i < reverseAuction.getImages().size(); i++) {
                images.add(base64ToUri(reverseAuction.getImages().get(i).getBase64Data()));
            }
        } else {
            Uri defaultImageUri = Uri.parse("android.resource://com.ingsw.dietiDeals24/" + R.drawable.no_image_available);
            images.add(defaultImageUri);
        }

        adapter.renewItems(images);
    }

    private Uri base64ToUri(String base64) {
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
