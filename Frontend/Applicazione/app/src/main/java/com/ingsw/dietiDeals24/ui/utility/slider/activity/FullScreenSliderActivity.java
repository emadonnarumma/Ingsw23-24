package com.ingsw.dietiDeals24.ui.utility.slider.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.utility.slider.adapter.CreateAuctionSmallSliderAdapter;
import com.ingsw.dietiDeals24.ui.utility.slider.adapter.FullScreenSliderViewAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class FullScreenSliderActivity extends AppCompatActivity {


    private View layout;
    private ArrayList<Uri> images;
    private SliderView sliderView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_slider);

        getImagesFromSlider();


        sliderView = findViewById(R.id.image_slider_view_full_screen);
       FullScreenSliderViewAdapter fullScreenSliderViewAdapter =
                new FullScreenSliderViewAdapter(getApplicationContext());
        sliderView.setSliderAdapter(fullScreenSliderViewAdapter);
        fullScreenSliderViewAdapter.renewItems(images);
    }



    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {

        return super.onCreateView(parent, name, context, attrs);
    }




    @Override
    protected void onResume() {
        super.onResume();


    }




    private void getImagesFromSlider() {
        Intent intent = getIntent();
        images = intent.getParcelableArrayListExtra("images");
    }
}
