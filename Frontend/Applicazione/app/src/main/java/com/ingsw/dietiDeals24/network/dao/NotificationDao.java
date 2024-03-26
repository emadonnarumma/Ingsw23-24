package com.ingsw.dietiDeals24.network.dao;

import com.ingsw.dietiDeals24.model.Notification;
import com.ingsw.dietiDeals24.model.enumeration.Role;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface NotificationDao {
    @GET("notification/{userEmail}/{userRole}/getAllNotifications")
    Call<List<Notification>> getAllNotificationsByUserId(@Path("userEmail") String email, @Path("userRole") Role role, @Header("Authorization") String token);

    @DELETE("notification/{id}")
    Call<Void> deleteNotification(@Path("id") Integer id, @Header("Authorization") String token);
}
