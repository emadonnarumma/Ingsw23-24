package com.ingsw.dietiDeals24.network.dao;

import com.ingsw.dietiDeals24.model.ReverseBid;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface SearchAuctionDetailsDao {

    @GET("bid/reverse/{auctionId}")
    Call<ReverseBid> getMinReverseBid(@Path("auctionId") Integer auctionId, @Header("Authorization") String authToken);
}
