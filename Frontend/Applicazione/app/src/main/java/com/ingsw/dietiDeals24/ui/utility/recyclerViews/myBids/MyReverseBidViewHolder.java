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
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.enumeration.BidStatus;
import com.ingsw.dietiDeals24.ui.utility.NumberFormatter;
import com.ingsw.dietiDeals24.ui.utility.slider.adapter.SmallScreenSliderAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MyReverseBidViewHolder extends RecyclerView.ViewHolder {

    private TextView containerTextView, titleTextView, myOfferTextView,
            bidStatusTextView;

    private ImageView bidTypeIconImageView, bidStatusIconImageView;

    private SliderView imagesSliderView;

    private CardView cardView;

    public MyReverseBidViewHolder(@NonNull View itemView) {
        super(itemView);

        containerTextView = itemView.findViewById(R.id.container_text_view_item_my_reverse_bid);
        titleTextView = itemView.findViewById(R.id.bid_title_item_my_reverse_bid);

        bidStatusTextView = itemView.findViewById(R.id.bid_status_text_view_item_my_reverse_bid);

        bidTypeIconImageView = itemView.findViewById(R.id.bid_type_icon_item_my_reverse_bid);
        bidStatusIconImageView = itemView.findViewById(R.id.bid_status_icon_item_my_reverse_bid);

        imagesSliderView = itemView.findViewById(R.id.images_item_my_reverse_bid);
        cardView = itemView.findViewById(R.id.card_view_item_my_reverse_bid);

        myOfferTextView = itemView.findViewById(R.id.my_offer_view_item_my_reverse_bid);
    }

    public void bind(ReverseBid reverseBid) {

        myOfferTextView.setText("Mia offerta: " + NumberFormatter.formatPrice(reverseBid.getMoneyAmount()));

        ReverseAuction auction = reverseBid.getReverseAuction();

        titleTextView.setText(auction.getTitle());

        BidStatus status = reverseBid.getStatus();
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

    private void bindImages(ReverseAuction silentAuction) {
        SmallScreenSliderAdapter adapter = new SmallScreenSliderAdapter(itemView.getContext());
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
