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

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.ui.utility.slider.adapter.SmallScreenSliderAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DownwardAuctionViewHolder extends RecyclerView.ViewHolder {

    private TextView containerTextView, titleTextView,
            auctionTypeTextView, buyNowTextView,
            auctionStatusTextView;

    private ImageView auctionTypeIconImageView, auctionStatusIconImageView;

    private SliderView imagesSliderView;

    private CardView cardView;


    public DownwardAuctionViewHolder(@NonNull View itemView) {
        super(itemView);
        containerTextView = itemView.findViewById(R.id.container_text_view_item_downward_auction);
        titleTextView = itemView.findViewById(R.id.title_text_view_item_downward_auction);
        auctionTypeTextView = itemView.findViewById(R.id.auction_type_text_view_item_downward_auction);
        auctionStatusTextView = itemView.findViewById(R.id.auction_status_text_view_item_downward_auction);
        buyNowTextView = itemView.findViewById(R.id.buy_now_text_view_item_downward_auction);
        auctionTypeIconImageView = itemView.findViewById(R.id.auction_type_icon_item_downward_auction);
        auctionStatusIconImageView = itemView.findViewById(R.id.auction_status_icon_item_downward_auction);
        imagesSliderView = itemView.findViewById(R.id.slider_item_downward_auction);
        cardView = itemView.findViewById(R.id.card_view_item_downward_auction);
    }

    public void bind(DownwardAuction downwardAuction) {
        titleTextView.setText(downwardAuction.getTitle());
        auctionTypeTextView.setText(AuctionType.toItalianString(downwardAuction.getType()));
        buyNowTextView.setText(downwardAuction.getCurrentPrice() + " €");

        AuctionStatus status = downwardAuction.getStatus();
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

        bindImages(downwardAuction);
    }

    private void bindImages(DownwardAuction reverseAuction) {
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
