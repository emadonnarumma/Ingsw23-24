package com.ingsw.dietiDeals24.network.dao;

import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.Image;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.enumeration.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface SearchAuctionsDao {

    @GET("auction/silent/category/{category}")
    Call<List<SilentAuction>> getAllSilentAuctionsByCategory(@Path("category") Category category, @Header("Authorization") String authToken);

    @GET("auction/silent")
    Call<List<SilentAuction>> getAllSilentAuctions(@Header("Authorization") String authToken);

    @GET("auction/silent/search/{keyword}")
    Call<List<SilentAuction>> getAllSilentAuctionsByKeyword(@Path("keyword") String keyword, @Header("Authorization") String authToken);

    @GET("auction/silent/search/{keyword}/category/{category}")
    Call<List<SilentAuction>> getAllSilentAuctionsByKeywordAndCategory(@Path("keyword") String keyword, @Path("category") Category category, @Header("Authorization") String authToken);

    @GET("auction/reverse")
    Call<List<ReverseAuction>> getAllReverseAuctions(@Header("Authorization") String authToken);

    @GET("auction/reverse/category/{category}")
    Call<List<ReverseAuction>> getAllReverseAuctionsByCategory(@Path("category") Category category, @Header("Authorization") String authToken);

    @GET("auction/reverse/search/{keyword}")
    Call<List<ReverseAuction>> getAllReverseAuctionsByKeyword(@Path("keyword") String keyword, @Header("Authorization") String authToken);

    @GET("auction/reverse/search/{keyword}/category/{category}")
    Call<List<ReverseAuction>> getAllReverseAuctionsByKeywordAndCategory(@Path("keyword") String keyword, @Path("category") Category category, @Header("Authorization") String authToken);

    @GET("auction/downward")
    Call<List<DownwardAuction>> getAllDownwardAuctions(@Header("Authorization") String authToken);

    @GET("auction/downward/category/{category}")
    Call<List<DownwardAuction>> getAllDownwardAuctionsByCategory(@Path("category") Category category, @Header("Authorization") String authToken);

    @GET("auction/downward/search/{keyword}")
    Call<List<DownwardAuction>> getAllDownwardAuctionsByKeyword(@Path("keyword") String keyword, @Header("Authorization") String authToken);

    @GET("auction/downward/search/{keyword}/category/{category}")
    Call<List<DownwardAuction>> getAllDownwardAuctionsByKeywordAndCategory(@Path("keyword") String keyword, @Path("category") Category category, @Header("Authorization") String authToken);

    @GET("auction/{email}/getInProgressReverseAuctions")
    Call<List<ReverseAuction>> getInProgressOtherUserReverseAuctions(@Path("email") String email, @Header("Authorization") String authToken);

    @GET("auction/{email}/getInProgressSilentAuctions")
    Call<List<SilentAuction>> getInProgressOtherUserSilentAuctions(@Path("email") String email, @Header("Authorization") String authToken);

    @GET("auction/{email}/getInProgressDownwardAuctions")
    Call<List<DownwardAuction>> getInProgressOtherUserDownwardAuctions(@Path("email") String email, @Header("Authorization") String authToken);

    @GET("image/{auctionId}/getAllAuctionImages")
    Call<List<Image>> getAllAuctionImages(@Path("auctionId") Integer auctionId, @Header("Authorization") String authToken);
}
