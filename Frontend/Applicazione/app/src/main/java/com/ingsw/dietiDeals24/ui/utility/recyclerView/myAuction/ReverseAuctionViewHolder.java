package com.ingsw.dietiDeals24.ui.utility.recyclerView.myAuction;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.model.ReverseAuction;

public class ReverseAuctionViewHolder extends RecyclerView.ViewHolder {
    private TextView containerTextView;
    private TextView categoryTextView;

    public ReverseAuctionViewHolder(@NonNull View itemView) {
        super(itemView);
        containerTextView = itemView.findViewById(R.id.container_text_view_item_reverse_auction);
        categoryTextView = itemView.findViewById(R.id.catogory_text_view_item_reverse_auction);
    }

    public void bind(ReverseAuction silentAuction) {
        categoryTextView.setText(silentAuction.getCategory().toString());
    }
}
