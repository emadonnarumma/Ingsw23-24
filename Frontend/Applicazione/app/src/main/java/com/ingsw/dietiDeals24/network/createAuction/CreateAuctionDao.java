package com.ingsw.dietiDeals24.network.createAuction;

import com.ingsw.dietiDeals24.model.Auction;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CreateAuctionDao {
    @POST("auction")
    public Call<Auction> createAuction(@Body Auction auction);

}
