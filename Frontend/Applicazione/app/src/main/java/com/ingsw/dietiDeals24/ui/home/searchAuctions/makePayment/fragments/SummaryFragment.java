package com.ingsw.dietiDeals24.ui.home.searchAuctions.makePayment.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MakePaymentController;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.Bid;
import com.ingsw.dietiDeals24.model.CreditCard;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.ui.utility.NumberFormatter;
import com.ingsw.dietiDeals24.ui.utility.PopupGeneratorOf;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.ui.utility.recyclerViews.searchAuctions.SearchAuctionAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SummaryFragment extends FragmentOfMakePaymentActivity {
    private TextView auctionOwnerTextView, paymentMethodHintTextView,
            ownerTextView, cardCodeTextView,
            expirationDateTextView, totalTextView;

    private RecyclerView auctionRecyclerView;
    private CircularProgressButton buyButton, cancelButton;

    private CreditCard creditCard = MakePaymentController.getCreditCard();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAuctionOwnerTextView(view);
        setupPaymentMethodHintTextView(view);
        setupNameTextView(view);
        setupCardCodeTextView(view);
        setupExpirationDateTextView(view);
        setupTotalTextView(view);
        setupAuctionRecyclerView(view);
        setupBuyButton(view);
        cancelButton = view.findViewById(R.id.cancel_button_fragment_summary);
    }

    private void setupBuyButton(@NonNull View view) {
        buyButton = view.findViewById(R.id.buy_button_fragment_summary);
        buyButton.setOnClickListener(v -> new Thread(() -> {
                    requireActivity().runOnUiThread(() -> buyButton.startAnimation());
                    try {

                        if (MakePaymentController.getAuction().getClass() == DownwardAuction.class) {

                            MakePaymentController.makeDownwardBid().get();

                        } else {
                            MakePaymentController.payBid();
                        }

                        requireActivity().runOnUiThread(() -> {
                            buyButton.revertAnimation();
                            PopupGeneratorOf.bidSendedSuccessfullyPopup(requireContext());
                        });

                    } catch (ExecutionException e) {
                        requireActivity().runOnUiThread(() -> {
                            buyButton.revertAnimation();
                            ToastManager.showToast(requireContext(), e.getCause().getMessage());
                        });
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start()
        );
    }

    private void setupAuctionRecyclerView(@NonNull View view) {
        SearchAuctionAdapter adapter = getAdapterWithOneAuction();
        auctionRecyclerView = view.findViewById(R.id.auction_recycler_view_fragment_summary);
        auctionRecyclerView.setAdapter(adapter);
        auctionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        makeRecyclerViewNotClickable();
    }

    @NonNull
    private static SearchAuctionAdapter getAdapterWithOneAuction() {
        List<Auction> auctions = new ArrayList<>();
        auctions.add(MakePaymentController.getAuction());
        SearchAuctionAdapter adapter = new SearchAuctionAdapter(auctions);
        return adapter;
    }

    private void makeRecyclerViewNotClickable() {
        auctionRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return true;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    private void setupTotalTextView(@NonNull View view) {
        totalTextView = view.findViewById(R.id.total_text_view_fragment_summary);

        Bid bid = MakePaymentController.getBid();

        if (bid == null) {

            DownwardAuction downwardAuction = (DownwardAuction) MakePaymentController.getAuction();
            totalTextView.setText("TOTALE: " + NumberFormatter.formatPrice(downwardAuction.getCurrentPrice()));

        } else {
            totalTextView.setText("TOTALE: " + NumberFormatter.formatPrice(bid.getMoneyAmount()));
        }
    }

    private void setupExpirationDateTextView(@NonNull View view) {
        expirationDateTextView = view.findViewById(R.id.expiration_date_text_view_fragment_summary);
        expirationDateTextView.setText(creditCard.getExpirationDate());
    }

    private void setupCardCodeTextView(@NonNull View view) {
        cardCodeTextView = view.findViewById(R.id.card_code_text_view_fragment_summary);
        cardCodeTextView.setText(creditCard.getCardNumber().replace("-", " "));
    }

    private void setupNameTextView(@NonNull View view) {
        ownerTextView = view.findViewById(R.id.name_text_view_fragment_summary);
        ownerTextView.setText(creditCard.getCardOwnerName() + " " + creditCard.getCardOwnerSurname());
    }

    private void setupPaymentMethodHintTextView(@NonNull View view) {
        paymentMethodHintTextView = view.findViewById(R.id.payment_method_hint_text_view_fragment_summary);
        paymentMethodHintTextView.setText("PAGAMENTO CON CARTA DI CREDITO");
    }

    private void setupAuctionOwnerTextView(@NonNull View view) {
        auctionOwnerTextView = view.findViewById(R.id.auction_owner_text_view_fragment_summary);
        auctionOwnerTextView.setText("ANNUNCIO DI: " + MakePaymentController.getAuction().getOwner().getName());
    }
}
