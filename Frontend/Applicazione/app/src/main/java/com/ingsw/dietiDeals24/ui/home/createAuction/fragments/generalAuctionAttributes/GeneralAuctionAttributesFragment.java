package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.CreateAuctionController;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.ui.utility.slider.adapter.CreateAuctionSmallSliderAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class GeneralAuctionAttributesFragment extends Fragment {


    private Auction newAuction = CreateAuctionController.newAuction;
    private ActivityResultLauncher<String[]> resultLauncher;
    private List<Uri> selectedImages = new ArrayList<>();
    private SliderView sliderView;
    private CreateAuctionSmallSliderAdapter createAuctionSmallSliderAdapter;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        resultLauncher = registerForActivityResult(new ActivityResultContracts.OpenMultipleDocuments(), o -> {
            if (o != null && !o.isEmpty()) {
                createAuctionSmallSliderAdapter.renewItems((ArrayList<Uri>) o);
                selectedImages.addAll(o);
            }
        });

        return inflater.inflate(R.layout.fragment_general_auction_attributes, container, false);
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View button = view.findViewById(R.id.add_image_button_general_auction_attributes);

        sliderView = view.findViewById(R.id.image_slider_view_small);
        createAuctionSmallSliderAdapter = new CreateAuctionSmallSliderAdapter(getContext());
        sliderView.setSliderAdapter(createAuctionSmallSliderAdapter);

        button.setOnClickListener(v -> resultLauncher.launch(new String[]{"image/*"}));
    }
}
