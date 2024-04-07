package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.controller.formstate.GeneralAuctionAttributesFormState;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Role;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.userTypeAuctionAttributes.BuyerAuctionTypesFragment;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.userTypeAuctionAttributes.SellerAuctionTypesFragment;
import com.ingsw.dietiDeals24.ui.recyclerViews.auctionImages.SmallScreenImagesAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GeneralAuctionAttributesFragment extends FragmentOfHomeActivity {

    private ImageView noImageSelectedImageView;
    private Uri currentPhotoUri;
    private ActivityResultLauncher<Uri> takePictureLauncher;
    private ActivityResultLauncher<String[]> selectPictureLauncher;
    private ArrayList<Uri> selectedImages = new ArrayList<>();
    private GeneralAuctionAttributesViewModel viewModel;
    private TextView titleErrorTextView, descriptionErrorTextView;
    private TextInputLayout titleTextInputLayout, descriptionTextInputLayout;
    private EditText titleEditText, descriptionEditText;
    private SliderView sliderView;
    private FloatingActionButton addButton, deleteButton, nextStepButton;

    private SmallScreenImagesAdapter smallScreenImagesAdapter;
    private SmartMaterialSpinner<String> wearSmartSpinner, categorySmartSpinner;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            updateFormState();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackButtonEnabled(false);

        viewModel = GeneralAuctionAttributesViewModel.getInstance();
        setMenuVisibility(true);
        createTakePictureLauncher();
        createSelectPictureLauncher();
    }

    @Override
    public void onResume() {
        super.onResume();
        titleEditText.setError(null);
        descriptionEditText.setError(null);
    }

    private void createSelectPictureLauncher() {
        selectPictureLauncher = registerForActivityResult(new ActivityResultContracts.OpenMultipleDocuments(), uris -> {
            if (uris != null && !uris.isEmpty()) {
                selectedImages.addAll(uris);
                smallScreenImagesAdapter.renewItems(selectedImages);
                updateDeleteButton();
            }
        });
    }

    private void createTakePictureLauncher() {
        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), isSuccess -> {
            if (isSuccess) {
                selectedImages.add(currentPhotoUri);
                smallScreenImagesAdapter.renewItems(selectedImages);
                updateDeleteButton();
            }
            requireContext().revokeUriPermission(currentPhotoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
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
        noImageSelectedImageView = view.findViewById(R.id.default_create_auction_image_view);
        setupActionBar();
        setupButtons(view);
        setupSlider(view);
        setupTextViews(view);
        setupSpinners(view);
        setupTextInputLayouts(view);

        titleEditText.addTextChangedListener(textWatcher);
        descriptionEditText.addTextChangedListener(textWatcher);
        wearSmartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateFormState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed here
            }
        });

        categorySmartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateFormState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        observeFormState();
        updateFormState();
        restoreData();
    }

    private void setupTextInputLayouts(View view) {
        titleTextInputLayout = view.findViewById(R.id.title_layout_general_auction_attributes);
        descriptionTextInputLayout = view.findViewById(R.id.description_layout_general_auction_attributes);
    }

    private void observeFormState() {
        viewModel.getGeneralAuctionAttributesFormState().observe(getViewLifecycleOwner(), formState -> {
            if (formState == null) {
                return;
            }
            nextStepButton.setEnabled(formState.isDataValid());
            if (formState.getTitleError() != null) {
                showErrorAndChangeColor(
                        formState,
                        titleEditText,
                        titleErrorTextView,
                        titleTextInputLayout
                );
            } else {
                hideErrorAndChangeColor(
                        titleEditText,
                        titleErrorTextView,
                        titleTextInputLayout
                );
            }
            if (formState.getDescriptionError() != null) {
                showErrorAndChangeColor(
                        formState,
                        descriptionEditText,
                        descriptionErrorTextView,
                        descriptionTextInputLayout
                );
            } else {
                hideErrorAndChangeColor(
                        descriptionEditText,
                        descriptionErrorTextView,
                        descriptionTextInputLayout
                );
            }
        });
    }

    private void hideErrorAndChangeColor(EditText editText, TextView errorTextView, TextInputLayout textInputLayout) {
        errorTextView.setVisibility(View.GONE);
        editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
        textInputLayout.setBoxStrokeColor(ContextCompat.getColor(requireContext(), R.color.green));
    }

    private void showErrorAndChangeColor(GeneralAuctionAttributesFormState formState, EditText editText, TextView errorTextView, TextInputLayout layout) {
        if (errorTextView.equals(titleErrorTextView)) {
            errorTextView.setText(formState.getTitleError());
        } else {
            errorTextView.setText(formState.getDescriptionError());
        }
        errorTextView.setVisibility(View.VISIBLE);
        editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
        layout.setBoxStrokeColor(ContextCompat.getColor(requireContext(), R.color.red));
    }

    private void setupButtons(@NonNull View view) {
        setupAddImagesButton(view);
        setupDeleteButton(view);
        setupNextButton(view);
    }

    private void setupSlider(@NonNull View view) {
        sliderView = view.findViewById(R.id.slider_view_small);
        smallScreenImagesAdapter = new SmallScreenImagesAdapter(getContext());
        sliderView.setSliderAdapter(smallScreenImagesAdapter);
        updateDeleteButton();
    }

    private void setupSpinners(View view) {
        setupCategorySpinner(view);
        setupWearSpinner(view);
    }

    private void setupTextViews(View view) {
        titleErrorTextView = view.findViewById(R.id.title_error_text_view_general_auction_attributes);
        descriptionErrorTextView = view.findViewById(R.id.description_error_text_view_general_auction_attributes);
        setupTitleTextView(view);
        setupDescriptionTextView(view);
    }

    private void setupActionBar() {
        if (getActivity() instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    private void restoreData() {
        viewModel.getNewAuction().observe(getViewLifecycleOwner(), auction -> {
            if (auction != null) {
                titleEditText.setText(auction.getTitle());
                descriptionEditText.setText(auction.getDescription());

                if (auction.getWear() != null) {
                    wearSmartSpinner.setSelection(auction.getWear().ordinal());
                }

                if (auction.getCategory() != null) {
                    categorySmartSpinner.setSelection(auction.getCategory().ordinal());
                }

                selectedImages = auction.getImages();
                smallScreenImagesAdapter.renewItems((ArrayList<Uri>) auction.getImages());
                updateDeleteButton();
            }
        });
    }

    private void setupDescriptionTextView(View view) {
        descriptionEditText = view.findViewById(R.id.description_edit_text_general_auction_attributes);
    }

    private void setupTitleTextView(View view) {
        titleEditText = view.findViewById(R.id.title_edit_text_general_auction_attributes);
    }

    private void setupWearSpinner(View view) {
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
        addButton.setOnClickListener(v -> showImageSourceDialog());
    }

    private void showImageSourceDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Scegli l'origine dell'immagine")
                .setPositiveButton("Fotocamera", (dialog, which) -> {
                    Uri imageUri = getOutputMediaFileUri();
                    if (imageUri != null) {
                        currentPhotoUri = imageUri;
                        List<ResolveInfo> cameraActivities = requireContext().getPackageManager().queryIntentActivities(
                                new Intent(MediaStore.ACTION_IMAGE_CAPTURE), PackageManager.MATCH_DEFAULT_ONLY

                        );

                        for (ResolveInfo activity : cameraActivities) {
                            requireContext().grantUriPermission(activity.activityInfo.packageName,
                                    imageUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        }

                        takePictureLauncher.launch(imageUri);

                    } else {
                        throw new RuntimeException("Impossibile creare il file");
                    }

                })
                .setNegativeButton("Galleria", (dialog, which) -> {
                    selectPictureLauncher.launch(new String[]{"image/*"});
                })
                .show();
    }

    private Uri getOutputMediaFileUri() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "DietiDeals24");

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            return null;
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return FileProvider.getUriForFile(requireContext(), requireContext().getPackageName() + ".provider", mediaFile);
    }

    private void setupDeleteButton(@NonNull View view) {
        deleteButton = view.findViewById(R.id.delete_image_button_general_auction_attributes);
        deleteButton.setImageTintList(ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.white))
        );

        deleteButton.setOnClickListener(v -> {
            if (!selectedImages.isEmpty()) {
                selectedImages.remove(sliderView.getCurrentPagePosition());
                smallScreenImagesAdapter.renewItems(selectedImages);
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
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_home, getUserTypeFragment())
                    .commit();
        });
    }

    @NonNull
    private static Fragment getUserTypeFragment() {
        Fragment fragment;
        if (UserHolder.user.getRole().equals(Role.SELLER)) {
            fragment = new SellerAuctionTypesFragment();
        } else {
            fragment = new BuyerAuctionTypesFragment();
        }

        return fragment;
    }

    private void updateDeleteButton() {
        if (selectedImages.isEmpty()) {
            deleteButton.setVisibility(View.GONE);
            deleteButton.setClickable(false);

            noImageSelectedImageView.setVisibility(View.VISIBLE);
        } else {
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setClickable(true);

            noImageSelectedImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        updateViewModel();
    }

    private void updateViewModel() {
        viewModel.generalAuctionAttributesChanged(
                titleEditText.getText().toString(),
                descriptionEditText.getText().toString(),
                getWearValue(),
                getCategoryValue(),
                selectedImages
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

    private void updateFormState() {
        viewModel.generalAuctionInputChanged(
                titleEditText.getText().toString(),
                descriptionEditText.getText().toString(),
                wearSmartSpinner.getSelectedItem() != null,
                categorySmartSpinner.getSelectedItem() != null,
                getContext()
        );
    }
}