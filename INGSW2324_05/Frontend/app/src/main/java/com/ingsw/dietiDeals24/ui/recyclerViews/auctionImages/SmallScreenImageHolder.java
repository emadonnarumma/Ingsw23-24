package com.ingsw.dietiDeals24.ui.recyclerViews.auctionImages;

import android.view.View;
import android.widget.ImageView;

import com.ingsw.dietiDeals24.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SmallScreenImageHolder extends SliderViewAdapter.ViewHolder {


    public View itemView;
    public ImageView imageView;




    public SmallScreenImageHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_view_slider_small);;
        this.itemView = itemView;
    }
}
