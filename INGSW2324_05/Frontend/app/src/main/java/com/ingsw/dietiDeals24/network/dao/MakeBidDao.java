package com.ingsw.dietiDeals24.network.dao;

import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.SilentBid;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MakeBidDao {
    @POST("bid/silent")
    Call<SilentBid> makeSilentBid(@Body SilentBid silentBid, @Header("Authorization") String token);

    @POST("bid/reverse")
    Call<ReverseBid> makeReverseBid(@Body ReverseBid reverseBid, @Header("Authorization") String token);


}
