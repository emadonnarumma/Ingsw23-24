package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.CreateAuctionController;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.ui.utility.recyclerView.adapter.ImagesRecyclerViewAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GeneralAuctionAttributesFragment extends Fragment {

    private ImagesRecyclerViewAdapter imageAdapter;
    private Auction newAuction = CreateAuctionController.newAuction;
    private ActivityResultLauncher<String[]> resultLauncher;
    private List<Bitmap> selectedImages = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resultLauncher = registerForActivityResult(new ActivityResultContracts.OpenMultipleDocuments(),
                result -> {
            if (result != null && !result.isEmpty()) {
                for (Uri uri : result) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                requireActivity().getContentResolver().openInputStream(uri)
                        );
                        selectedImages.add(bitmap);
                        imageAdapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {

            }
        });
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_general_auction_attributes, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = view.findViewById(R.id.button2);
        button.setOnClickListener(v -> resultLauncher.launch(new String[]{"image/*"}));

        RecyclerView recyclerView = view.findViewById(R.id.create_auction_recyclerView);
        imageAdapter = new ImagesRecyclerViewAdapter(selectedImages);
        recyclerView.setAdapter(imageAdapter);
    }



    private void insertImagesIntoAuction(Bitmap bitmap) {
        byte[] imageBytes = getBitmapAsByteArray(bitmap);
        newAuction.setImages(imageBytes);
    }


    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
