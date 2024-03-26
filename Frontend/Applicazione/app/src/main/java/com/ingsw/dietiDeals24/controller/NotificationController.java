package com.ingsw.dietiDeals24.controller;

import static com.ingsw.dietiDeals24.controller.UserHolder.user;

import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.Notification;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.dao.NotificationDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class NotificationController implements RetroFitHolder {
    private static List<Notification> notifications = new ArrayList<>();

    public static List<Notification> getNotifications() {
        return notifications;
    }

    public static void setNotifications(List<Notification> notifications) {
        NotificationController.notifications = notifications;
    }

    public static CompletableFuture<Void> updateNotifications() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationDao notificationDao = retrofit.create(NotificationDao.class);
                Response<List<Notification>> response = notificationDao.getAllNotificationsByUserId(user.getEmail(), user.getRole(), TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    notifications = response.body();
                } else if (response.code() == 403) {
                    throw new AuthenticationException("Errore di autenticazione");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return null;
        });
    }

    public static CompletableFuture<Void> deleteNotification(Notification notification) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationDao notificationDao = retrofit.create(NotificationDao.class);
                Response<Void> response = notificationDao.deleteNotification(notification.getIdNotification(), TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    updateNotifications();
                } else if (response.code() == 403) {
                    throw new AuthenticationException("Errore di autenticazione");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return null;
        });
    }
}
