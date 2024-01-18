package com.ingsw.dietiDeals24.ui.utility.slider.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.utility.slider.adapter.FullScreenSliderViewAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Objects;

public class FullScreenSliderActivity extends AppCompatActivity {


    private View layout;
    private ArrayList<Uri> images;
    private SliderView sliderView;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_slider);

        getImagesFromSlider();

        layout = findViewById(R.id.full_screen_slider_activity_layout);
        sliderView = findViewById(R.id.slider_view_large);

        FullScreenSliderViewAdapter fullScreenSliderViewAdapter =
                new FullScreenSliderViewAdapter(getApplicationContext());
        sliderView.setSliderAdapter(fullScreenSliderViewAdapter);
        fullScreenSliderViewAdapter.renewItems(images);

        sliderView.setCurrentPagePosition(getPositionFromSlider());


        toolbar = (Toolbar) findViewById(R.id.toolbar_full_screen_slider_activity);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_back_arrow_white_24dp));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

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




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    private void getImagesFromSlider() {
        Intent intent = getIntent();
        images = intent.getParcelableArrayListExtra("images");
    }




    private int getPositionFromSlider() {
        Intent intent = getIntent();
        return intent.getIntExtra("position", 0);
    }
}
