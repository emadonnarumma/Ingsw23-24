package com.ingsw.dietiDeals24.network.currentUser;

import com.ingsw.dietiDeals24.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface CurrentUserDao {
    @GET("user/{email}")
    Call<User> getUser(@Path("email") String email, @Header("Authorization") String token);
}
