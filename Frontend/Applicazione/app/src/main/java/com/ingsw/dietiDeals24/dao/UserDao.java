package com.ingsw.dietiDeals24.dao;

import com.ingsw.dietiDeals24.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserDao {
    @GET("user/{email}/{password}")
    public Call<User> getUserByEmailAndPassword(@Path("email") String email, @Path("password") String password);
}
