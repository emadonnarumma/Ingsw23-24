package com.ingsw.dietiDeals24.network.dao;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.Image;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface MyAuctionsDao {
    @GET("auction/{email}/getSilentAuctions")
    public Call<List<SilentAuction>> getSilentAuctions(@Path("email") String email, @Header("Authorization") String authToken);

    @GET("auction/{email}/getDownwardAuctions")
    public Call<List<DownwardAuction>> getDownwardAuctions(@Path("email") String email, @Header("Authorization") String authToken);

    @GET("auction/{email}/getReverseAuctions")
    public Call<List<ReverseAuction>> getReverseAuctions(@Path("email") String email, @Header("Authorization") String authToken);

    @GET("image/{auctionId}/getAllAuctionImages")
    public Call<List<Image>> getAllAuctionImages(@Path("auctionId") Integer auctionId, @Header("Authorization") String authToken);
}
