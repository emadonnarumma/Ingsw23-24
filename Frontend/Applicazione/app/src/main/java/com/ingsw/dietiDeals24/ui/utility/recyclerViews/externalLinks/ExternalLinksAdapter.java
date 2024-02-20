package com.ingsw.dietiDeals24.ui.utility.recyclerViews.externalLinks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.model.ExternalLink;

import java.util.List;

public class ExternalLinksAdapter extends RecyclerView.Adapter<ExternalLinkHolder> {
    private static List<ExternalLink> externalLinks;
    private Fragment fragment;

    public ExternalLinksAdapter(@NonNull List<ExternalLink> externalLinks, Fragment fragment) {
        this.externalLinks = externalLinks;
        this.fragment = fragment;
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
        holder.bind(externalLink, fragment);
    }

    @Override
    public int getItemCount() {
        return externalLinks.size();
    }
}
