package com.ingsw.dietiDeals24.ui.recyclerViews.searchAuctions;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.ImageController;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.ui.recyclerViews.auctionImages.SmallScreenImagesAdapter;
import com.ingsw.dietiDeals24.utility.NumberFormatter;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class SearchDownwardAuctionViewHolder extends RecyclerView.ViewHolder {

    private TextView containerTextView, titleTextView,
            auctionTypeTextView, buyNowTextView,
            auctionCategoryTextView;

    private ImageView auctionTypeIconImageView;

    private SliderView imagesSliderView;

    private CardView cardView;

    public SearchDownwardAuctionViewHolder(@NonNull View itemView) {
        super(itemView);

        containerTextView = itemView.findViewById(R.id.container_text_view_item_downward_auction);
        titleTextView = itemView.findViewById(R.id.title_text_view_item_downward_auction);
        auctionTypeTextView = itemView.findViewById(R.id.auction_type_text_view_item_downward_auction);
        auctionCategoryTextView = itemView.findViewById(R.id.auction_category_text_view_item_downward_auction);
        buyNowTextView = itemView.findViewById(R.id.buy_now_text_view_item_downward_auction);
        auctionTypeIconImageView = itemView.findViewById(R.id.auction_type_icon_item_downward_auction);
        imagesSliderView = itemView.findViewById(R.id.slider_item_downward_auction);
        cardView = itemView.findViewById(R.id.card_view_item_downward_auction);
    }

    public void bind(DownwardAuction downwardAuction) {
        titleTextView.setText(downwardAuction.getTitle());
        auctionTypeTextView.setText(AuctionType.toItalianString(downwardAuction.getType()));
        buyNowTextView.setText(NumberFormatter.formatPrice(downwardAuction.getCurrentPrice()));
        auctionCategoryTextView.setText(Category.toItalianString(downwardAuction.getCategory()));
        bindImages(downwardAuction);
    }

    private void bindImages(DownwardAuction reverseAuction) {
        SmallScreenImagesAdapter adapter = new SmallScreenImagesAdapter(itemView.getContext());
        imagesSliderView.setSliderAdapter(adapter);

        ArrayList<Uri> images = new ArrayList<>();
        if (reverseAuction.getImages() != null && !reverseAuction.getImages().isEmpty()) {
            for (int i = 0; i < reverseAuction.getImages().size(); i++) {
                images.add(ImageController.base64ToUri(reverseAuction.getImages().get(i).getBase64Data(), itemView.getContext()));
            }
        } else {
            Uri defaultImageUri = Uri.parse("android.resource://com.ingsw.dietiDeals24/" + R.drawable.no_image_available);
            images.add(defaultImageUri);
        }

        adapter.renewItems(images);
    }
}