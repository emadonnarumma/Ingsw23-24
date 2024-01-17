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
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class CreateAuctionSmallSliderAdapter extends SliderViewAdapter<CreateAuctionSmallSliderViewHolder> {


    private Context context;
    private ArrayList<Uri> images = new ArrayList<>();




    public CreateAuctionSmallSliderAdapter(Context context) {
        this.context = context;
    }




    public void renewItems(ArrayList<Uri> images) {
        this.images = images;
        notifyDataSetChanged();
    }




    public void deleteItem(int position) {
        this.images.remove(position);
        notifyDataSetChanged();
    }




    public void addItem(Uri image) {
        this.images.add(image);
        notifyDataSetChanged();
    }




    @Override
    public CreateAuctionSmallSliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_small_layout_item, null);
        return new CreateAuctionSmallSliderViewHolder(inflate);
    }




    @Override
    public void onBindViewHolder(CreateAuctionSmallSliderViewHolder viewHolder, final int position) {
        Uri image = images.get(position);

        visualizeImage(viewHolder, image);
        userClickImage(viewHolder);
    }




    private static void visualizeImage(CreateAuctionSmallSliderViewHolder viewHolder, Uri image) {
        Glide.with(viewHolder.itemView)
                .load(image)
                .fitCenter()
                .into(viewHolder.imageView);
    }




    @Override
    public int getCount() {
        return images.size();
    }




    private void userClickImage(CreateAuctionSmallSliderViewHolder viewHolder) {
        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullScreenSliderActivity.class);
            intent.putParcelableArrayListExtra("images", images);
            context.startActivity(intent);
        });
    }
}

