package com.ingsw.dietiDeals24.ui.home.myAuctions.recyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.model.DownwardAuction;

public class DownwardAuctionViewHolder extends RecyclerView.ViewHolder {

    private TextView containerTextView;
    private TextView categoryTextView;

    public DownwardAuctionViewHolder(@NonNull View itemView) {
        super(itemView);
        containerTextView = itemView.findViewById(R.id.container_text_view_item_downward_auction);
        categoryTextView = itemView.findViewById(R.id.category_text_view_item_downward_auction);
    }

    public void bind(DownwardAuction silentAuction) {
        categoryTextView.setText(silentAuction.getCategory().toString());
    }
}
