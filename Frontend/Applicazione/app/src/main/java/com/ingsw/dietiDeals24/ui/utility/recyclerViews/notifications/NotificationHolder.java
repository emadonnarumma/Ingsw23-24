package com.ingsw.dietiDeals24.ui.utility.recyclerViews.notifications;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.ImageController;
import com.ingsw.dietiDeals24.controller.NotificationController;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.Notification;
import com.ingsw.dietiDeals24.ui.utility.ToastManager;
import com.ingsw.dietiDeals24.ui.utility.slider.adapter.SmallScreenSliderAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotificationHolder extends RecyclerView.ViewHolder {
    private SliderView imagesSliderView;
    private TextView descriptionTextView;
    private ImageButton deleteNotificationButton;
    private NotificationAdapter adapter;
    private List<Notification> notifications;

    public NotificationHolder(@NonNull View itemView, NotificationAdapter adapter, List<Notification> notifications) {
        super(itemView);
        imagesSliderView = itemView.findViewById(R.id.slider_view_item_notifications);
        descriptionTextView = itemView.findViewById(R.id.description_text_view_item_notifications);
        deleteNotificationButton = itemView.findViewById(R.id.delete_item_notifications);
        this.adapter = adapter;
        this.notifications = notifications;
    }

    public void bind(Notification notification) {
        descriptionTextView.setText(notification.getMessage());

        setupDeleteButton(notification);
        bindImages(notification.getAuction());
    }

    private void setupDeleteButton(Notification notification) {
        deleteNotificationButton.setOnClickListener(v -> {
            try {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    notifications.remove(position);
                    adapter.notifyItemRemoved(position);
                }
                NotificationController.deleteNotification(notification).get();
            } catch (ExecutionException e) {
                if (e.getCause() instanceof AuthenticationException) {
                    ((Activity) itemView.getContext()).runOnUiThread(() -> ToastManager.showToast(itemView.getContext(), "Sessione scaduta, effettua nuovamente il login"));
                } else if (e.getCause() instanceof ConnectionException) {
                    ((Activity) itemView.getContext()).runOnUiThread(() -> ToastManager.showToast(itemView.getContext(), "Errore di connessione"));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
    }

    private void bindImages(Auction auction) {
        SmallScreenSliderAdapter adapter = new SmallScreenSliderAdapter(itemView.getContext());
        imagesSliderView.setSliderAdapter(adapter);

        ArrayList<Uri> images = new ArrayList<>();
        if (auction.getImages() != null && !auction.getImages().isEmpty()) {
            for (int i = 0; i < auction.getImages().size(); i++) {
                images.add(ImageController.base64ToUri(auction.getImages().get(i).getBase64Data(), itemView.getContext()));
            }
        } else {
            Uri defaultImageUri = Uri.parse("android.resource://com.ingsw.dietiDeals24/" + R.drawable.no_image_available);
            images.add(defaultImageUri);
        }

        adapter.renewItems(images);
    }
}
