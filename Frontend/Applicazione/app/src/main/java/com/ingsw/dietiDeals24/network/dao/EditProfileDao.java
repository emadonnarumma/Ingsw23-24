package com.ingsw.dietiDeals24.network.dao;

import com.ingsw.dietiDeals24.model.enumeration.Region;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EditProfileDao {
    @PUT("user/{email}/updateRegion")
    Call<Void> updateRegion(@Path("email") String email, @Body Region region, @Header("Authorization") String token);

    @PUT("user/{email}/updateBio")
    Call<Void> updateBio(@Path("email") String email, @Body String bio, @Header("Authorization") String token);
}
