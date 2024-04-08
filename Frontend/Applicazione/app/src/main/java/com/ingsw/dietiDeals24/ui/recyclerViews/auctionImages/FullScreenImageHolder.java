package com.ingsw.dietiDeals24.ui.recyclerViews.auctionImages;

import android.view.View;
import android.widget.ImageView;

import com.ingsw.dietiDeals24.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class FullScreenImageHolder extends SliderViewAdapter.ViewHolder {


    public View itemView;
    public ImageView imageView;




    public FullScreenImageHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.zoom_image_view_slider_large);;
        this.itemView = itemView;
    }
}
