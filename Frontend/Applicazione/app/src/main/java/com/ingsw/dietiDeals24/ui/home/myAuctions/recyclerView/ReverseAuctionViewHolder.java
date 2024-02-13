package com.ingsw.dietiDeals24.ui.home.myAuctions.recyclerView;

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
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.ui.utility.slider.adapter.SmallScreenSliderAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ReverseAuctionViewHolder extends RecyclerView.ViewHolder {
    private TextView containerTextView, titleTextView,
            descriptionTextView, auctionTypeTextView, currentBidTextView,
            categoryTextView, auctionStatusTextView, expirationDateTextView;

    private ImageView auctionTypeIconImageView, auctionStatusIconImageView, expirationDateIconImageView;

    private SliderView imagesSliderView;

    private CardView cardView;


    public ReverseAuctionViewHolder(@NonNull View itemView) {
        super(itemView);
        containerTextView = itemView.findViewById(R.id.container_text_view_item_reverse_auction);
        categoryTextView = itemView.findViewById(R.id.category_text_view_item_reverse_auction);
        titleTextView = itemView.findViewById(R.id.title_text_view_item_reverse_auction);
        descriptionTextView = itemView.findViewById(R.id.description_text_view_item_reverse_auction);
        auctionTypeTextView = itemView.findViewById(R.id.auction_type_text_view_item_reverse_auction);
        auctionStatusTextView = itemView.findViewById(R.id.auction_status_text_view_item_reverse_auction);
        expirationDateTextView = itemView.findViewById(R.id.expiration_date_text_view_item_reverse_auction);
        currentBidTextView = itemView.findViewById(R.id.current_bid_text_view_item_reverse_auction);
        auctionTypeIconImageView = itemView.findViewById(R.id.auction_type_icon_item_reverse_auction);
        auctionStatusIconImageView = itemView.findViewById(R.id.auction_status_icon_item_reverse_auction);
        expirationDateIconImageView = itemView.findViewById(R.id.expiration_date_icon_item_reverse_auction);
        imagesSliderView = itemView.findViewById(R.id.slider_item_reverse_auction);
        cardView = itemView.findViewById(R.id.card_view_item_reverse_auction);
    }

    public void bind(ReverseAuction reverseAuction) {
        titleTextView.setText(reverseAuction.getTitle());
        descriptionTextView.setText(reverseAuction.getDescription());
        categoryTextView.setText(Category.toItalianString(reverseAuction.getCategory()));
        auctionTypeTextView.setText(AuctionType.toItalianString(reverseAuction.getType()));
        currentBidTextView.setText(String.valueOf(reverseAuction.getCurrentPrice()) + " €");

        AuctionStatus status = reverseAuction.getStatus();
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

        expirationDateTextView.setText(reverseAuction.getExpirationDate().substring(0, 10));
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
