package com.ingsw.dietiDeals24.ui.home.searchAuctions.makePayment.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ingsw.dietiDeals24.R;

public class SelectPaymentMethodFragment extends FragmentOfMakePaymentActivity {
    private FrameLayout creditCardPayment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackButtonEnabled(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_payment_method, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        creditCardPayment = view.findViewById(R.id.frame_layout_credit_card_payment_select_payment_method);
        creditCardPayment.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_make_payment, new CreditCardAttributesFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }
}
