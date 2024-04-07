package com.ingsw.dietiDeals24.ui.recyclerViews.auctionImages;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.home.imageSlider.FullScreenSliderActivity;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SmallScreenImagesAdapter extends SliderViewAdapter<SmallScreenImageHolder> {
    private Context context;
    private ArrayList<Uri> images = new ArrayList<>();

    public SmallScreenImagesAdapter(Context context) {
        this.context = context;
    }




    public void renewItems(ArrayList<Uri> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    public void renewItems(List<Uri> images) {
        this.images = (ArrayList<Uri>) images;
        notifyDataSetChanged();
    }

    @Override
    public SmallScreenImageHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_small_layout_item, null);
        return new SmallScreenImageHolder(inflate);
    }




    @Override
    public void onBindViewHolder(SmallScreenImageHolder viewHolder, final int position) {
        Uri image = images.get(position);
        visualizeImage(viewHolder, image);
        userClickImage(viewHolder, position);
    }




    private static void visualizeImage(SmallScreenImageHolder viewHolder, Uri image) {
        Glide.with(viewHolder.itemView)
                .load(image)
                .fitCenter()
                .into(viewHolder.imageView);
    }




    @Override
    public int getCount() {
        return images.size();
    }




    private void userClickImage(SmallScreenImageHolder viewHolder, int position) {
        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullScreenSliderActivity.class);
            intent.putParcelableArrayListExtra("images", images);
            intent.putExtra("position", position);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }
}

