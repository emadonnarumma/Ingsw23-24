package com.ingsw.dietiDeals24.network.dao;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface MyBidDetailsDao {

    @DELETE("bid/{idBid}")
    Call<Boolean> deleteBid(@Path("idBid") Integer idBid, @Header("Authorization") String authToken);

    @GET("bid/silent/{idBid}/isWithdrawable")
    Call<Boolean> isSilentBidWithdrawable(@Path("idBid") Integer idBid, @Header("Authorization") String authToken);
}
