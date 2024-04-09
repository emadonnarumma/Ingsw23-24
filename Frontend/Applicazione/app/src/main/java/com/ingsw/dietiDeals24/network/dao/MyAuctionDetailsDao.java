package com.ingsw.dietiDeals24.network.dao;

import com.ingsw.dietiDeals24.model.DownwardBid;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.SilentBid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyAuctionDetailsDao {
    @DELETE("auction/{id}")
    Call<Boolean> deleteAuction(@Path("id") Integer idBid, @Header("Authorization") String authToken);

    @POST("bid/declineBid/{id}")
    Call<Boolean> deleteBid(@Path("id") Integer idBid, @Header("Authorization") String authToken);

    @GET("bid/silent/{auctionId}")
    Call<List<SilentBid>> getAllSilentBidsBySilentAuctionId(@Path("auctionId") Integer auctionId, @Header("Authorization") String authToken);

    @GET("bid/silent/inProgress/{auctionId}")
    Call<List<SilentBid>> getInProgressSilentBidsByAuctionId(@Path("auctionId") Integer auctionId, @Header("Authorization") String authToken);

    @GET("bid/reverse/{auctionId}")
    Call<ReverseBid> getMinPricedReverseBidsByAuctionId(@Path("auctionId") Integer auctionId, @Header("Authorization") String authToken);

    @GET("bid/silent/winning/{auctionId}")
    Call<SilentBid> getWinningSilentBidByAuctionId(@Path("auctionId") Integer auctionId, @Header("Authorization") String authToken);

    @GET("bid/reverse/winning/{auctionId}")
    Call<ReverseBid> getWinningReverseBidByAuctionId(@Path("auctionId") Integer auctionId, @Header("Authorization") String authToken);

    @GET("bid/downward/winning/{auctionId}")
    Call<DownwardBid> getWinningDownwardBidByAuctionId(@Path("auctionId") Integer auctionId, @Header("Authorization") String authToken);

    @POST("bid/acceptBid/{id}")
    Call<Boolean> acceptBid(@Path("id") Integer idBid, @Header("Authorization") String authToken);
}
