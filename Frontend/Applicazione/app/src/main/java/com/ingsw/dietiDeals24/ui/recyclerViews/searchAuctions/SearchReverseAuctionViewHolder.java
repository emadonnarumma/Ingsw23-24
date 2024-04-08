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
import com.ingsw.dietiDeals24.controller.MyAuctionsController;
import com.ingsw.dietiDeals24.controller.SearchAuctionsController;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.utility.NumberFormatter;
import com.ingsw.dietiDeals24.utility.ToastManager;
import com.ingsw.dietiDeals24.ui.recyclerViews.auctionImages.SmallScreenImagesAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SearchReverseAuctionViewHolder extends RecyclerView.ViewHolder {

    private TextView containerTextView, titleTextView,
            auctionTypeTextView, currentBidTextView, currentBidHintTextView,
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
        currentBidHintTextView = itemView.findViewById(R.id.current_bid_hint_text_view_item_reverse_auction);
        auctionTypeIconImageView = itemView.findViewById(R.id.auction_type_icon_item_reverse_auction);
        expirationDateIconImageView = itemView.findViewById(R.id.expiration_date_icon_item_reverse_auction);
        imagesSliderView = itemView.findViewById(R.id.slider_item_reverse_auction);
        cardView = itemView.findViewById(R.id.card_view_item_reverse_auction);
    }

    public void bind(ReverseAuction reverseAuction) {
        titleTextView.setText(reverseAuction.getTitle());
        auctionTypeTextView.setText(AuctionType.toItalianString(reverseAuction.getType()));
        auctionCategoryTextView.setText(Category.toItalianString(reverseAuction.getCategory()));
        expirationDateTextView.setText(MyAuctionsController.getFormattedExpirationDate(reverseAuction));
        setupCurrentBidTextView(reverseAuction);
        bindImages(reverseAuction);
    }

    private void setupCurrentBidTextView(ReverseAuction reverseAuction) {
        ReverseBid currentBid = null;
        try {
            currentBid = SearchAuctionsController.getCurrentReverseBid(reverseAuction).get();
        } catch (ExecutionException e) {
            ToastManager.showToast(itemView.getContext(), e.getCause().getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (currentBid == null) {
            currentBidHintTextView.setText("Prezzo iniziale:");
            currentBidTextView.setText(NumberFormatter.formatPrice(reverseAuction.getStartingPrice()));
        } else {
            currentBidHintTextView.setText("Offerta attuale:");
            currentBidTextView.setText(NumberFormatter.formatPrice(currentBid.getMoneyAmount()));
        }
    }

    private void bindImages(ReverseAuction reverseAuction) {
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
