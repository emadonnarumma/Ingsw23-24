package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes;

import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.CreateAuctionController;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.ui.utility.slider.adapter.SmallScreenSliderAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class GeneralAuctionAttributesFragment extends Fragment {


    private Auction newAuction = CreateAuctionController.newAuction;
    private ActivityResultLauncher<String[]> resultLauncher;
    private ArrayList<Uri> selectedImages = new ArrayList<>();
    private SliderView sliderView;
    private FloatingActionButton addButton, deleteButton;
    private SmallScreenSliderAdapter smallScreenSliderAdapter;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_general_auction_attributes, container, false);
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userClickAddButton(view);
        userClickDeleteButton(view);
        setTheSlider(view);
    }




    private void setTheSlider(@NonNull View view) {
        sliderView = view.findViewById(R.id.slider_view_small);
        smallScreenSliderAdapter = new SmallScreenSliderAdapter(getContext());
        sliderView.setSliderAdapter(smallScreenSliderAdapter);
        disableDeleteButton();
    }




    private void userClickAddButton(@NonNull View view) {
        addButton = view.findViewById(R.id.add_image_button_general_auction_attributes);
        addButton.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));
        addButton.setOnClickListener(v -> {
                resultLauncher.launch(new String[]{"image/*"});
        });

        resultLauncher = registerForActivityResult(new ActivityResultContracts.OpenMultipleDocuments(), o -> {
            if (o != null && !o.isEmpty()) {
                selectedImages.addAll(o);
                smallScreenSliderAdapter.renewItems(selectedImages);
                enableDeleteButton();
            }
        });
    }



    private void userClickDeleteButton(@NonNull View view) {
        deleteButton = view.findViewById(R.id.delete_image_button_general_auction_attributes);
        deleteButton.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));

        deleteButton.setOnClickListener(v -> {
            if (!selectedImages.isEmpty()) {
                int position = sliderView.getCurrentPagePosition();
                selectedImages.remove(position);
                smallScreenSliderAdapter.renewItems(selectedImages);

                checkUpdateDeleteButton();
            }
        });
    }




    private void checkUpdateDeleteButton() {
        if (!selectedImages.isEmpty()) {
            enableDeleteButton();
        } else {
            disableDeleteButton();
        }
    }




    private void disableDeleteButton() {
        if (selectedImages.isEmpty()) {
            deleteButton.setVisibility(View.GONE);
            deleteButton.setClickable(false);
        }
    }




    private void enableDeleteButton() {
        if (!selectedImages.isEmpty()) {
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setClickable(true);
        }
    }
}
