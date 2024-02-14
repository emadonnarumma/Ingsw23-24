package com.ingsw.dietiDeals24.ui.utility.recyclerViews.externalLinks;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;

public class ExternalLinkHolder extends RecyclerView.ViewHolder {
    TextView titleTextView;
    TextView urlTextView;

    public ExternalLinkHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.title_item_external_link);
        urlTextView = itemView.findViewById(R.id.url_item_external_link);
    }
}
