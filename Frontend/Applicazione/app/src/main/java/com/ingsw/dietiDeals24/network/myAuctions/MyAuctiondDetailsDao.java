package com.ingsw.dietiDeals24.network.myAuctions;

import com.ingsw.dietiDeals24.model.Auction;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyAuctiondDetailsDao {
    @DELETE("auction/{id}")
    Call<Void> deleteAuction(@Path("id") Integer id, @Header("Authorization") String authToken);

    @POST("auction/relaunch")
    Call<Void> relaunchAuction(@Body Auction auction, @Header("Authorization") String authToken);
}
