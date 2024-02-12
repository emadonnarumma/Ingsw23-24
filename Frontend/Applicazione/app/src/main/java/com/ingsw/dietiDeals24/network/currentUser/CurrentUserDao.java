package com.ingsw.dietiDeals24.network.currentUser;

import com.ingsw.dietiDeals24.model.enumeration.Role;
import com.ingsw.dietiDeals24.model.Buyer;
import com.ingsw.dietiDeals24.model.Seller;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface CurrentUserDao {
    @GET("user/{email}/gigi")
    Call<Role> getRole(@Path("email") String email, @Header("Authorization") String token);

    @GET("user/{email}/buyer")
    Call<Buyer> getBuyerByEmail(@Path("email") String email, @Header("Authorization") String token);

    @GET("user/{email}/seller")
    Call<Seller> getSellerByEmail(@Path("email") String email, @Header("Authorization") String token);
}
