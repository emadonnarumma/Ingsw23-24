package com.ingsw.dietiDeals24.network.createAuction;

import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CreateAuctionDao {
    @POST("auction")
    Call<ReverseAuction> createAuction(@Body ReverseAuction auction, @Header("Authorization") String token);

    @POST("auction")
    Call<SilentAuction> createAuction(@Body SilentAuction auction, @Header("Authorization") String token);

    @POST("auction")
    Call<DownwardAuction> createAuction(@Body DownwardAuction auction, @Header("Authorization") String token);
}
