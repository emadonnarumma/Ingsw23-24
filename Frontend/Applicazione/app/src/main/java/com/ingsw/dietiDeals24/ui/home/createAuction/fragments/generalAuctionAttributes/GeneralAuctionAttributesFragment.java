package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes;

import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.LogInController;
import com.ingsw.dietiDeals24.enumeration.Category;
import com.ingsw.dietiDeals24.enumeration.Role;
import com.ingsw.dietiDeals24.enumeration.Wear;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.userTypeAuctionAttributes.BuyerAuctionTypesFragment;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.userTypeAuctionAttributes.SellerAuctionTypesFragment;
import com.ingsw.dietiDeals24.ui.utility.slider.adapter.SmallScreenSliderAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Arrays;

public class GeneralAuctionAttributesFragment extends Fragment {
    private GeneralAuctionAttributesViewModel viewModel;
    private TextView titleTextView, descriptionTextView;
    private ActivityResultLauncher<String[]> resultLauncher;
    private ArrayList<Uri> selectedImages = new ArrayList<>();
    private SliderView sliderView;
    private FloatingActionButton addButton, deleteButton, nextStepButton;
    private SmallScreenSliderAdapter smallScreenSliderAdapter;
    private SmartMaterialSpinner<String> wearSmartSpinner, categorySmartSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(GeneralAuctionAttributesViewModel.class);
        setMenuVisibility(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_general_auction_attributes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getNewAuction().observe(getViewLifecycleOwner(), auction -> {
            if (auction != null) {
                titleTextView.setText(auction.getTitle());
                descriptionTextView.setText(auction.getDescription());

                // Add null checks before calling ordinal() method
                if (auction.getWear() != null) {
                    wearSmartSpinner.setSelection(auction.getWear().ordinal());
                }
                if (auction.getCategory() != null) {
                    categorySmartSpinner.setSelection(auction.getCategory().ordinal());
                }

                smallScreenSliderAdapter.renewItems((ArrayList<Uri>) auction.getImages());
            }
        });
        setupButtons(view);
        setupSlider(view);
        setupTextViews(view);
        setupSpinners(view);
        setupActionBar();
    }

    private void setupTextViews(View view) {
        setupTitleTextView(view);
        setupDescriptionTextView(view);
    }

    private void setupDescriptionTextView(View view) {
        descriptionTextView = view.findViewById(R.id.description_edit_text_general_auction_attributes);
        descriptionTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Wear wearValue = getWearValue();
                Category categoryValue = getCategoryValue();
                if (wearValue != null && categoryValue != null) {
                    updateViewModel();
                }
            }
        });
    }

    private void setupTitleTextView(View view) {
        titleTextView = view.findViewById(R.id.title_edit_text_general_auction_attributes);
    }

    private void setupActionBar() {
        if (getActivity() instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    private void setupSlider(@NonNull View view) {
        sliderView = view.findViewById(R.id.slider_view_small);
        smallScreenSliderAdapter = new SmallScreenSliderAdapter(getContext());
        sliderView.setSliderAdapter(smallScreenSliderAdapter);
        updateDeleteButton();
    }

    private void setupButtons(@NonNull View view) {
        setupAddImagesButton(view);
        setupDeleteButton(view);
        setupNextButton(view);
    }

    private void setupSpinners(View view) {
        setupCategorySpinner(view);
        setupRegionSpinner(view);
    }

    private void setupRegionSpinner(View view) {
        wearSmartSpinner = view.findViewById(R.id.wear_spinner_general_auction_attributes);
        wearSmartSpinner.setItem(
                Arrays.asList(
                        getResources().getStringArray(R.array.wears)
                )
        );
    }

    private void setupCategorySpinner(View view) {
        categorySmartSpinner = view.findViewById(R.id.category_spinner_general_auction_attributes);
        categorySmartSpinner.setItem(
                Arrays.asList(
                        getResources().getStringArray(R.array.categories)
                )
        );
    }

    private void setupAddImagesButton(@NonNull View view) {
        addButton = view.findViewById(R.id.add_image_button_general_auction_attributes);
        addButton.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));
        addButton.setOnClickListener(v -> resultLauncher.launch(new String[]{"image/*"}));

        resultLauncher = registerForActivityResult(new ActivityResultContracts.OpenMultipleDocuments(), uris -> {
            if (uris != null && !uris.isEmpty()) {
                selectedImages.addAll(uris);
                smallScreenSliderAdapter.renewItems(selectedImages);
                viewModel.setImages(selectedImages);
                updateViewModel();
                updateDeleteButton();
            }
        });
    }

    private void setupDeleteButton(@NonNull View view) {
        deleteButton = view.findViewById(R.id.delete_image_button_general_auction_attributes);
        deleteButton.setImageTintList(ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.white))
        );

        deleteButton.setOnClickListener(v -> {
            if (!selectedImages.isEmpty()) {
                selectedImages.remove(sliderView.getCurrentPagePosition());
                smallScreenSliderAdapter.renewItems(selectedImages);
                updateDeleteButton();
            }
        });
    }

    private void setupNextButton(@NonNull View view) {
        nextStepButton = view.findViewById(R.id.next_step_button_general_auction_attributes);
        nextStepButton.setImageTintList(ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.white))
        );

        nextStepButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            if (LogInController.loggedUser.getRole().equals(Role.SELLER)) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_home, new SellerAuctionTypesFragment())
                        .commit();
            } else {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_home, new BuyerAuctionTypesFragment())
                        .commit();
            }
        });
    }

    private void updateDeleteButton() {
        if (selectedImages.isEmpty()) {
            deleteButton.setVisibility(View.GONE);
            deleteButton.setClickable(false);
        } else {
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setClickable(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        updateViewModel();
    }

    private void updateViewModel() {
        viewModel.generalAuctionAttributesChanged(
                titleTextView.getText().toString(),
                descriptionTextView.getText().toString(),
                getWearValue(),
                getCategoryValue()
        );
    }

    private Wear getWearValue() {
        if (wearSmartSpinner.getSelectedItem() != null) {
            return Wear.fromItalianString(wearSmartSpinner.getSelectedItem());
        }
        return null;
    }

    private Category getCategoryValue() {
        if (categorySmartSpinner.getSelectedItem() != null) {
            return Category.fromItalianString(categorySmartSpinner.getSelectedItem());
        }
        return null;
    }

}
