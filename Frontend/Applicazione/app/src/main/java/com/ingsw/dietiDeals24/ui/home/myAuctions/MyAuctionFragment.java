package com.ingsw.dietiDeals24.ui.home.myAuctions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MyAuctionsController;
import com.ingsw.dietiDeals24.controller.UserHolder;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.ui.utility.recyclerViews.myAuctions.AuctionAdapter;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyAuctionFragment extends Fragment {
    RecyclerView recyclerView;

    public static MyAuctionFragment newInstance() {
        return new MyAuctionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_auctions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_my_auctions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        try {
            recyclerView.setAdapter(new AuctionAdapter(
                    MyAuctionsController.getSilentAuctions(UserHolder.user.getEmail()).get(),
                    MyAuctionsController.getDownwardAuctions(UserHolder.user.getEmail()).get(),
                    MyAuctionsController.getReverseAuctions(UserHolder.user.getEmail()).get()
            ));
        } catch (ExecutionException e) {
            recyclerView.setAdapter(new AuctionAdapter(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
            requireActivity().runOnUiThread(() -> ToastManager.showToast(getContext(), e.getCause().getMessage()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}