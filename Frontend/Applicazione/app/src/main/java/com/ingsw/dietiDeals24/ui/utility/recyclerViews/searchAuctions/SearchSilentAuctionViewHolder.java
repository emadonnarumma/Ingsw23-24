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
import com.ingsw.dietiDeals24.controller.ImageController;
import com.ingsw.dietiDeals24.controller.MyAuctionsController;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.ui.utility.slider.adapter.SmallScreenSliderAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SearchSilentAuctionViewHolder  extends RecyclerView.ViewHolder {

    private TextView containerTextView, titleTextView, auctionTypeTextView,
            auctionCategoryTextView, expirationDateTextView;

    private ImageView auctionTypeIconImageView, expirationDateIconImageView;

    private SliderView imagesSliderView;

    private CardView cardView;


    public SearchSilentAuctionViewHolder(@NonNull View itemView) {
        super(itemView);

        containerTextView = itemView.findViewById(R.id.container_text_view_item_silent_auction);
        titleTextView = itemView.findViewById(R.id.title_text_view_item_silent_auction);
        auctionTypeTextView = itemView.findViewById(R.id.auction_type_text_view_item_silent_auction);
        auctionCategoryTextView = itemView.findViewById(R.id.auction_category_text_view_item_silent_auction);
        expirationDateTextView = itemView.findViewById(R.id.expiration_date_text_view_item_silent_auction);
        auctionTypeIconImageView = itemView.findViewById(R.id.auction_type_icon_item_silent_auction);
        expirationDateIconImageView = itemView.findViewById(R.id.expiration_date_icon_item_silent_auction);
        imagesSliderView = itemView.findViewById(R.id.slider_item_silent_auction);
        cardView = itemView.findViewById(R.id.card_view_item_silent_auction);
    }

    public void bind(SilentAuction silentAuction) {
        titleTextView.setText(silentAuction.getTitle());
        auctionTypeTextView.setText(AuctionType.toItalianString(silentAuction.getType()));

        auctionCategoryTextView.setText(Category.toItalianString(silentAuction.getCategory()));

        expirationDateTextView.setText(MyAuctionsController.getFormattedExpirationDate(silentAuction));

        if (silentAuction.getImages() != null) {
            bindImages(silentAuction);
        }
    }


    private void bindImages(SilentAuction silentAuction) {
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
