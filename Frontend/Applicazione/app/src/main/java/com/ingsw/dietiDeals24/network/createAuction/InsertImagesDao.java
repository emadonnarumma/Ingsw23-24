package com.ingsw.dietiDeals24.network.createAuction;

import com.ingsw.dietiDeals24.model.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InsertImagesDao {

    @POST("image")
    public Call<List<Image>> insertImage(@Body List<Image> images, @Header("Authorization") String token);
}
