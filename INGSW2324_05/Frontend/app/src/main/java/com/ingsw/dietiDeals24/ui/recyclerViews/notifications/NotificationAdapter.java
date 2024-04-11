package com.ingsw.dietiDeals24.ui.recyclerViews.notifications;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.MyAuctionDetailsController;
import com.ingsw.dietiDeals24.model.Notification;
import com.ingsw.dietiDeals24.ui.home.myAuctions.auctionDetails.downwardAuction.FailedDownwardAuctionActivity;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationHolder> {
    private List<Notification> notifications;

    public NotificationAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifications, parent, false);
        return new NotificationHolder(view, this, notifications);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.bind(notification);
        View itemView = holder.itemView;
        itemView.setOnClickListener(v -> {
            Intent intent = new Intent(itemView.getContext(), FailedDownwardAuctionActivity.class);
            MyAuctionDetailsController.setAuction(notification.getAuction());
            itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}
