package com.ingsw.dietiDeals24.network.createAuction;

import com.ingsw.dietiDeals24.model.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InsertImagesDao {

    @POST("image")
    Call<Image> insertImage(@Body Image image, @Header("Authorization") String token);
}
