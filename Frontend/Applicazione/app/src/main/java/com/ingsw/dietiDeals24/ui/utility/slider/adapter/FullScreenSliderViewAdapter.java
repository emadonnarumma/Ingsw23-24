package com.ingsw.dietiDeals24.ui.utility.slider.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.utility.slider.activity.FullScreenSliderActivity;
import com.ingsw.dietiDeals24.ui.utility.slider.holder.CreateAuctionSmallSliderViewHolder;
import com.ingsw.dietiDeals24.ui.utility.slider.holder.FullScreenSliderViewHolder;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class FullScreenSliderViewAdapter extends SliderViewAdapter<FullScreenSliderViewHolder> {


    private Context context;
    private ArrayList<Uri> images = new ArrayList<>();




    public FullScreenSliderViewAdapter(Context context) {
        this.context = context;
    }




    public void renewItems(ArrayList<Uri> images) {
        this.images = images;
        notifyDataSetChanged();
    }




    @Override
    public FullScreenSliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_large_layout_item, null);
        return new FullScreenSliderViewHolder(inflate);
    }




    @Override
    public void onBindViewHolder(FullScreenSliderViewHolder viewHolder, final int position) {
        Uri image = images.get(position);

        visualizeImage(viewHolder, image);
    }




    private static void visualizeImage(FullScreenSliderViewHolder viewHolder, Uri image) {
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

