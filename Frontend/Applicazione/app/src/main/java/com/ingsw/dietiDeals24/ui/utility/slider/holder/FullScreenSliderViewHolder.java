package com.ingsw.dietiDeals24.ui.utility.slider.holder;

import android.view.View;
import android.widget.ImageView;

import com.ingsw.dietiDeals24.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class FullScreenSliderViewHolder extends SliderViewAdapter.ViewHolder {


    public View itemView;
    public ImageView imageView;




    public FullScreenSliderViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.zoom_image_view_slider_large);;
        this.itemView = itemView;
    }
}
