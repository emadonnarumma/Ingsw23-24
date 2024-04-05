package com.ingsw.dietiDeals24.utility.recyclerViews.externalLinks;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.model.ExternalLink;

public class ExternalLinkHolder extends RecyclerView.ViewHolder {
    private TextView titleTextView;
    private TextView urlTextView;

    public ExternalLinkHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.title_of_external_link);
        urlTextView = itemView.findViewById(R.id.url_of_external_link);
    }

    public void bind(ExternalLink externalLink) {
        titleTextView.setText(externalLink.getTitle());
        urlTextView.setText(externalLink.getUrl());
    }
}
