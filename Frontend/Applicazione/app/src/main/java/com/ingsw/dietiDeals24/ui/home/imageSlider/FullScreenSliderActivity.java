package com.ingsw.dietiDeals24.ui.home.imageSlider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.recyclerViews.auctionImages.FullScreenImagesAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

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

        FullScreenImagesAdapter fullScreenImagesAdapter =
                new FullScreenImagesAdapter(getApplicationContext());
        sliderView.setSliderAdapter(fullScreenImagesAdapter);
        fullScreenImagesAdapter.renewItems(images);

        sliderView.setCurrentPagePosition(getPositionFromSlider());

        setSupportActionBar(findViewById(R.id.toolbar_full_screen_slider_activity));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
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
