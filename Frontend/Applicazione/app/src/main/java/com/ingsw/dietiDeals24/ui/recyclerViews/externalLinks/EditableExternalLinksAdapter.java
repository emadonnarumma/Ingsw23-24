package com.ingsw.dietiDeals24.ui.recyclerViews.externalLinks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.model.ExternalLink;

import java.util.List;

public class EditableExternalLinksAdapter extends RecyclerView.Adapter<EditableExternalLinkHolder> {
    private static List<ExternalLink> externalLinks;
    private Fragment fragment;

    public EditableExternalLinksAdapter(@NonNull List<ExternalLink> externalLinks, Fragment fragment) {
        this.externalLinks = externalLinks;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public EditableExternalLinkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_editable_external_link, parent, false);
        return new EditableExternalLinkHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditableExternalLinkHolder holder, int position) {
        ExternalLink externalLink = externalLinks.get(position);
        holder.bind(fragment, externalLink);
    }

    @Override
    public int getItemCount() {
        return externalLinks.size();
    }
}
