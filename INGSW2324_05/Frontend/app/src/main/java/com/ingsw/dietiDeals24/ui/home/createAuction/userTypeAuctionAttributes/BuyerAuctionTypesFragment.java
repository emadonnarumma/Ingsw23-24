package com.ingsw.dietiDeals24.ui.home.createAuction.userTypeAuctionAttributes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.ui.home.FragmentOfHomeActivity;
import com.ingsw.dietiDeals24.ui.home.createAuction.specificAuctionAttributes.ReverseAuctionAttributesFragment;

public class BuyerAuctionTypesFragment extends FragmentOfHomeActivity {

    private ImageView reverseAuctionButton;
    private ImageView questionMarkReverseAuction;
    private BottomSheetDialog bottomSheetReverseAuctionDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buyer_auction_types, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonEnabled(true);

        setupReverseButton(view);

        setupBottomSheetDialogs();
        setupQuestionMarkReverseAuction(view);
    }

    private void setupReverseButton(@NonNull View view) {
        reverseAuctionButton = view.findViewById(R.id.reverse_auction_type_button_buyer_auction_types);
        reverseAuctionButton.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_home, new ReverseAuctionAttributesFragment())
                    .commit();
        });
    }

    private void setupBottomSheetDialogs() {
        bottomSheetReverseAuctionDialog = new BottomSheetDialog(requireContext());
        bottomSheetReverseAuctionDialog.setContentView(R.layout.bottom_sheet_dialog_questionmark_layout);

        TextView questionMark = bottomSheetReverseAuctionDialog.findViewById(R.id.question_bottom_sheet_text_view);
        TextView questionMarkExplanation = bottomSheetReverseAuctionDialog.findViewById(R.id.question_bottom_sheet_text_view_description);

        questionMark.setText(R.string.reverse_auction_question);
        questionMarkExplanation.setText(R.string.reverse_auction_description);
    }

    private void setupQuestionMarkReverseAuction(@NonNull View view) {
        questionMarkReverseAuction = view.findViewById(R.id.question_mark_buyer_reverse_auction);

        questionMarkReverseAuction.setOnClickListener(v -> bottomSheetReverseAuctionDialog.show());
    }
}

