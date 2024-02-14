package com.ingsw.dietiDeals24.ui.utility.recyclerViews.externalLinks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.model.ExternalLink;

import java.util.List;

public class ExternalLinksAdapter extends RecyclerView.Adapter<ExternalLinkHolder> {
    private static List<ExternalLink> externalLinks;
    private Context context;

    public ExternalLinksAdapter(@NonNull List<ExternalLink> externalLinks, Context context) {
        this.externalLinks = externalLinks;
        this.context = context;
    }

    @NonNull
    @Override
    public ExternalLinkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_external_link, parent, false);
        return new ExternalLinkHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExternalLinkHolder holder, int position) {
        ExternalLink externalLink = externalLinks.get(position);
        holder.titleTextView.setText(externalLink.getTitle());
        holder.urlTextView.setText(externalLink.getUrl());
    }

    @Override
    public int getItemCount() {
        return externalLinks.size();
    }
}
