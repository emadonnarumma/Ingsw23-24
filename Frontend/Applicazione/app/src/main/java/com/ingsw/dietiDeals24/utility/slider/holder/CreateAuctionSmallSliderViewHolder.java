package com.ingsw.dietiDeals24.utility.slider.holder;

import android.view.View;
import android.widget.ImageView;

import com.ingsw.dietiDeals24.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class CreateAuctionSmallSliderViewHolder extends SliderViewAdapter.ViewHolder {


    public View itemView;
    public ImageView imageView;




    public CreateAuctionSmallSliderViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_view_slider_small);;
        this.itemView = itemView;
    }
}
