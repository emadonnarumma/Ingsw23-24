package com.ingsw.dietiDeals24.ui.utility.recyclerView.myAuction;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.R;

public class SilentAuctionViewHolder extends RecyclerView.ViewHolder {

    private TextView containerTextView;
    private TextView categoryTextView;

    public SilentAuctionViewHolder(@NonNull View itemView) {
        super(itemView);
        containerTextView = itemView.findViewById(R.id.container_text_view_item_silent_auction);
        categoryTextView = itemView.findViewById(R.id.catogory_text_view_item_silent_auction);
    }

    public void bind(SilentAuction silentAuction) {
        categoryTextView.setText(silentAuction.getCategory().toString());
    }
}