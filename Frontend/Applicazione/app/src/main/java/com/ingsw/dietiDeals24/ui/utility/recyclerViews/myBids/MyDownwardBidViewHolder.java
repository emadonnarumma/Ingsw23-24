package com.ingsw.dietiDeals24.ui.utility.recyclerViews.myBids;

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
import com.ingsw.dietiDeals24.controller.ImageController;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.DownwardBid;
import com.ingsw.dietiDeals24.model.enumeration.BidStatus;
import com.ingsw.dietiDeals24.ui.utility.NumberFormatter;
import com.ingsw.dietiDeals24.ui.utility.slider.adapter.SmallScreenSliderAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MyDownwardBidViewHolder extends RecyclerView.ViewHolder {

    private TextView containerTextView, titleTextView, boughtAtTextView,
            bidStatusTextView;

    private ImageView bidTypeIconImageView, bidStatusIconImageView;

    private SliderView imagesSliderView;

    private CardView cardView;

    public MyDownwardBidViewHolder(@NonNull View itemView) {
        super(itemView);

        containerTextView = itemView.findViewById(R.id.container_text_view_item_my_downward_bid);
        titleTextView = itemView.findViewById(R.id.bid_title_item_my_downward_bid);

        bidStatusTextView = itemView.findViewById(R.id.bid_status_text_view_item_my_downward_bid);

        bidTypeIconImageView = itemView.findViewById(R.id.bid_type_icon_item_my_downward_bid);
        bidStatusIconImageView = itemView.findViewById(R.id.bid_status_icon_item_my_downward_bid);

        imagesSliderView = itemView.findViewById(R.id.images_item_my_downward_bid);
        cardView = itemView.findViewById(R.id.card_view_item_my_downward_bid);

        boughtAtTextView = itemView.findViewById(R.id.bought_at_text_view_item_my_downward_bid);
    }

    public void bind(DownwardBid downwardBid) {

        boughtAtTextView.setText("Comprato a: " + NumberFormatter.formatPrice(downwardBid.getMoneyAmount()));

        DownwardAuction auction = downwardBid.getDownwardAuction();

        titleTextView.setText(auction.getTitle());

        BidStatus status = downwardBid.getStatus();
        switch (status) {
            case PENDING:
                bidStatusTextView.setText(R.string.in_progress_phrase);
                bidStatusIconImageView.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.circular_yellow));
                break;

            case ACCEPTED:
                bidStatusTextView.setText(R.string.accepted_phrase);
                bidStatusIconImageView.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.circular_green));
                break;

            case DECLINED:
                bidStatusTextView.setText(R.string.declined_phrase);
                bidStatusIconImageView.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.circular_red));
                break;

            case EXPIRED:
                bidStatusTextView.setText(R.string.expired_phrase);
                bidStatusIconImageView.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.circular_red));
                break;

            case PAYED:
                bidStatusTextView.setText(R.string.payed_phrase);
                bidStatusIconImageView.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.circular_green));
                break;

            default:
                break;
        }

        if (auction.getImages() != null) {
            bindImages(auction);
        }
    }

    private void bindImages(DownwardAuction downwardAuction) {
        SmallScreenSliderAdapter adapter = new SmallScreenSliderAdapter(itemView.getContext());
        imagesSliderView.setSliderAdapter(adapter);

        ArrayList<Uri> images = new ArrayList<>();
        if (downwardAuction.getImages() != null && !downwardAuction.getImages().isEmpty()) {
            for (int i = 0; i < downwardAuction.getImages().size(); i++) {
                images.add(ImageController.base64ToUri(downwardAuction.getImages().get(i).getBase64Data(), itemView.getContext()));
            }
        } else {
            Uri defaultImageUri = Uri.parse("android.resource://com.ingsw.dietiDeals24/" + R.drawable.no_image_available);
            images.add(defaultImageUri);
        }

        adapter.renewItems(images);
    }
}
