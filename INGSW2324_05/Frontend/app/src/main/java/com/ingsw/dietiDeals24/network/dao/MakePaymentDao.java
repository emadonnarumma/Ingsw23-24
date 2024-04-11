package com.ingsw.dietiDeals24.network.dao;

import com.ingsw.dietiDeals24.model.Bid;
import com.ingsw.dietiDeals24.model.DownwardBid;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MakePaymentDao {

    @PUT("bid/payBid/{idBid}")
    Call<Bid> payBid(@Path("idBid") Integer idBid, @Header("Authorization") String token);

    @POST("bid/downward")
    Call<DownwardBid> makeDownwardBid(@Body DownwardBid downwardBid, @Header("Authorization") String token);
}
