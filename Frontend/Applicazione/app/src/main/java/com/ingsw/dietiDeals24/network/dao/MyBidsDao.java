package com.ingsw.dietiDeals24.network.dao;

import com.ingsw.dietiDeals24.model.DownwardBid;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.SilentBid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface MyBidsDao {

    @GET("bid/silent/buyer/{buyerEmail}")
    Call<List<SilentBid>> getSilentBids(@Path("buyerEmail") String buyerEmail, @Header("Authorization") String authToken);

    @GET("bid/downward/buyer/{buyerEmail}")
    Call<List<DownwardBid>> getDownwardBids(@Path("buyerEmail") String buyerEmail, @Header("Authorization") String authToken);

    @GET("bid/reverse/seller/{sellerEmail}")
    Call<List<ReverseBid>> getReverseBids(@Path("sellerEmail") String sellerEmail, @Header("Authorization") String authToken);

    @DELETE("bid/{idBid}")
    Call<Boolean> deleteBid(@Path("idBid") Integer idBid, @Header("Authorization") String authToken);
}

