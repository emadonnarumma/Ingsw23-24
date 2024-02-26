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

    @GET("auction/silent")
    public Call<List<SilentAuction>> getAllSilentAuctions(@Header("Authorization") String authToken);

    @GET("auction/silent/category/{category}")
    public Call<List<SilentAuction>> getAllSilentAuctionsByCategory(@Path("category") Category category, @Header("Authorization") String authToken);

    @GET("auction/silent/search/{keyword}")
    public Call<List<SilentAuction>> getAllSilentAuctionsByKeyword(@Path("keyword") String keyword, @Header("Authorization") String authToken);

    @GET("auction/silent/search/{keyword}/category/{category}")
    public Call<List<SilentAuction>> getAllSilentAuctionsByCategoryAndKeyword(@Path("keyword") String keyword, @Path("category") Category category, @Header("Authorization") String authToken);

    @GET("auction/reverse")
    public Call<List<ReverseAuction>> getAllReverseAuctions(@Header("Authorization") String authToken);

    @GET("auction/reverse/category/{category}")
    public Call<List<ReverseAuction>> getAllReverseAuctionsByCategory(@Path("category") Category category, @Header("Authorization") String authToken);

    @GET("auction/reverse/search/{keyword}")
    public Call<List<ReverseAuction>> getAllReverseAuctionsByKeyword(@Path("keyword") String keyword, @Header("Authorization") String authToken);

    @GET("auction/reverse/search/{keyword}/category/{category}")
    public Call<List<ReverseAuction>> getAllReverseAuctionsByCategoryAndKeyword(@Path("keyword") String keyword, @Path("category") Category category, @Header("Authorization") String authToken);


    @GET("auction/downward")
    public Call<List<DownwardAuction>> getAllDownwardAuctions(@Header("Authorization") String authToken);

    @GET("auction/downward/category/{category}")
    public Call<List<DownwardAuction>> getAllDownwardAuctionsByCategory(@Path("category") Category category, @Header("Authorization") String authToken);

    @GET("auction/downward/search/{keyword}")
    public Call<List<DownwardAuction>> getAllDownwardAuctionsByKeyword(@Path("keyword") String keyword, @Header("Authorization") String authToken);

    @GET("auction/downward/search/{keyword}/category/{category}")
    public Call<List<DownwardAuction>> getAllDownwardAuctionsByCategoryAndKeyword(@Path("keyword") String keyword, @Path("category") Category category, @Header("Authorization") String authToken);



    @GET("image/{auctionId}/getAllAuctionImages")
    public Call<List<Image>> getAllAuctionImages(@Path("auctionId") Integer auctionId, @Header("Authorization") String authToken);
}
