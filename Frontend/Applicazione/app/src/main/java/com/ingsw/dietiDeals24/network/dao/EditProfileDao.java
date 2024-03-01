package com.ingsw.dietiDeals24.network.dao;

import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.model.enumeration.Region;
import com.ingsw.dietiDeals24.model.enumeration.Role;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EditProfileDao {
    @PUT("user/{email}/updateRegion")
    Call<User> updateRegion(@Path("email") String email, @Body Region region, @Header("Authorization") String token);

    @PUT("user/{email}/updateBio")
    Call<User> updateBio(@Path("email") String email, @Body String bio, @Header("Authorization") String token);

    @PUT("user/{email}/updateRole")
    Call<User> updateRole(@Path("email") String email, @Body Role role, @Header("Authorization") String token);

    @GET("user/{email}/hasBankAccount")
    Call<Boolean> hasBankAccount(@Path("email") String email, @Header("Authorization") String token);
}
