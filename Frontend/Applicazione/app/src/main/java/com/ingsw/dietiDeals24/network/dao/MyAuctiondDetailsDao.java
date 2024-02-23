package com.ingsw.dietiDeals24.network.dao;

import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.SilentBid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyAuctiondDetailsDao {
    @DELETE("auction/{id}")
    Call<Void> deleteAuction(@Path("id") Integer id, @Header("Authorization") String authToken);

    @GET("bid/silent/{auctionId}")
    Call<List<SilentBid>> getAllSilentBidsBySilentAuctionId(@Path("auctionId") Integer auctionId, @Header("Authorization") String authToken);
}
