package com.ingsw.dietiDeals24.ui.utility.recyclerViews.auctionBids;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MyAuctionDetailsController;
import com.ingsw.dietiDeals24.controller.MyAuctionsController;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.SilentBid;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.AuctionDetailsActivity;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.silentAuction.InProgressSilentAuctionActivity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AuctionBidAdapter extends RecyclerView.Adapter<AuctionBidHolder> {
    private List<SilentBid> silentBids;

    public AuctionBidAdapter(List<SilentBid> silentBids) {
        this.silentBids = silentBids;
    }

    @NonNull
    @Override
    public AuctionBidHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auction_bid, parent, false);
        return new AuctionBidHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuctionBidHolder holder, int position) {
        SilentBid silentBid = silentBids.get(position);
        holder.bind(silentBid);

        holder.acceptButton.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Conferma")
                    .setMessage("Sei sicuro di voler accettare?")
                    .setPositiveButton("Si", (dialog, which) -> {
                        new Thread(() -> {
                            try {
                                boolean isAccepted = MyAuctionDetailsController.acceptBid(silentBid.getIdBid()).get();
                                if (isAccepted) {
                                    MyAuctionsController.setUpdatedSilent(true);
                                    ((AuctionDetailsActivity) v.getContext()).runOnUiThread(() -> ((AuctionDetailsActivity) v.getContext()).finish());
                                }
                            } catch (ExecutionException e) {
                                v.post(() -> Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show());
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }).start();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return silentBids.size();
    }
}
