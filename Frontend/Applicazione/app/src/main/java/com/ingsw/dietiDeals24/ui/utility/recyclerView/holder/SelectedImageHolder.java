package com.ingsw.dietiDeals24.ui.utility.recyclerView.holder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;

public class SelectedImageHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;

    public SelectedImageHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.selected_image);
    }
}
