package com.ingsw.dietiDeals24.ui.utility.recyclerView.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.utility.recyclerView.holder.SelectedImageHolder;

import java.util.List;

public class ImagesRecyclerViewAdapter extends RecyclerView.Adapter<SelectedImageHolder> {
    private List<Bitmap> selectedImages;

    public ImagesRecyclerViewAdapter(List<Bitmap> selectedImages) {
        this.selectedImages = selectedImages;
    }

    @Override
    public SelectedImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.create_auction_recycler_view_layout,
                parent,
                false
        );
        return new SelectedImageHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectedImageHolder holder, int position) {
        Bitmap bitmap = selectedImages.get(position);
        holder.imageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return selectedImages.size();
    }
}
