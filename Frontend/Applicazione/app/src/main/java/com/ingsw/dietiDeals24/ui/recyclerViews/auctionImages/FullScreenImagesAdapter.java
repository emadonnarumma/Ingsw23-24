package com.ingsw.dietiDeals24.ui.recyclerViews.auctionImages;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ingsw.dietiDeals24.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class FullScreenImagesAdapter extends SliderViewAdapter<FullScreenImageHolder> {


    private Context context;
    private ArrayList<Uri> images = new ArrayList<>();




    public FullScreenImagesAdapter(Context context) {
        this.context = context;
    }




    public void renewItems(ArrayList<Uri> images) {
        this.images = images;
        notifyDataSetChanged();
    }




    @Override
    public FullScreenImageHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_large_layout_item, null);
        return new FullScreenImageHolder(inflate);
    }




    @Override
    public void onBindViewHolder(FullScreenImageHolder viewHolder, final int position) {
        Uri image = images.get(position);

        visualizeImage(viewHolder, image);
    }




    private static void visualizeImage(FullScreenImageHolder viewHolder, Uri image) {
        Glide.with(viewHolder.itemView)
                .load(image)
                .fitCenter()
                .into(viewHolder.imageView);
    }




    @Override
    public int getCount() {
        return images.size();
    }
}

