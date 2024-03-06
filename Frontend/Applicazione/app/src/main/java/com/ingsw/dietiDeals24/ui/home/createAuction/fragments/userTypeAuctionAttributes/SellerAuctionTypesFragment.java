package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.userTypeAuctionAttributes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes.DownwardAuctionAttributesFragment;
import com.ingsw.dietiDeals24.ui.home.createAuction.fragments.specificAuctionAttributes.SilentAuctionAttributesFragment;

public class SellerAuctionTypesFragment extends FragmentOfHomeActivity {

    private ImageView silentAuctionButton, downwardAuctionButton;

    private ImageView questionMarkDownwardAuction, questionMarkSilentAuction;
    private BottomSheetDialog bottomSheetDownwardAuctionDialog, bottomSheetSilentAuctionDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seller_auction_types, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(true);

        setupBottomSheetDialogs();
        setupQuestionMarks(view);
        setupButtons(view);
    }

    private void setupBottomSheetDialogs() {
        bottomSheetDownwardAuctionDialog = new BottomSheetDialog(requireContext());
        bottomSheetDownwardAuctionDialog.setContentView(R.layout.bottom_sheet_dialog_questionmark_layout);

        TextView questionMark = bottomSheetDownwardAuctionDialog.findViewById(R.id.question_bottom_sheet_text_view);
        TextView questionMarkExplanation = bottomSheetDownwardAuctionDialog.findViewById(R.id.question_bottom_sheet_text_view_description);

        questionMark.setText(R.string.downward_auction_question);
        questionMarkExplanation.setText(R.string.downward_auction_description);

        bottomSheetSilentAuctionDialog = new BottomSheetDialog(requireContext());
        bottomSheetSilentAuctionDialog.setContentView(R.layout.bottom_sheet_dialog_questionmark_layout);

        questionMark = bottomSheetSilentAuctionDialog.findViewById(R.id.question_bottom_sheet_text_view);
        questionMarkExplanation = bottomSheetSilentAuctionDialog.findViewById(R.id.question_bottom_sheet_text_view_description);

        questionMark.setText(R.string.silent_auction_question);
        questionMarkExplanation.setText(R.string.silent_auction_description);
    }

    private void setupQuestionMarks(View view) {
        setupQuestionMarkDownwardAuction(view);
        setupQuestionMarkSilentAuction(view);
    }

    private void setupQuestionMarkDownwardAuction(View view) {
        questionMarkDownwardAuction = view.findViewById(R.id.question_mark_seller_downward_auction);
        questionMarkDownwardAuction.setOnClickListener(v -> bottomSheetDownwardAuctionDialog.show());
    }

    private void setupQuestionMarkSilentAuction(View view) {
        questionMarkSilentAuction = view.findViewById(R.id.question_mark_seller_silent_auction);
        questionMarkSilentAuction.setOnClickListener(v -> bottomSheetSilentAuctionDialog.show());
    }

    private void setupButtons(View view) {
        setupDownwardAuctionButton(view);
        setupSilentAuctionButton(view);
    }

    private void setupSilentAuctionButton(View view) {
        silentAuctionButton = view.findViewById(R.id.silent_auction_type_button_seller_auction_types);
        silentAuctionButton.setOnClickListener(v ->
                getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_home, new SilentAuctionAttributesFragment())
                .commit()
        );
    }

    private void setupDownwardAuctionButton(View view) {
        downwardAuctionButton = view.findViewById(R.id.downaward_auction_type_button_seller_auction_types);
        downwardAuctionButton.setOnClickListener(v ->
                getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_home, new DownwardAuctionAttributesFragment())
                .commit()
        );
    }
}