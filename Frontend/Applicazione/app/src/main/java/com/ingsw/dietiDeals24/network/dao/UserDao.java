package com.ingsw.dietiDeals24.network.dao;

import com.ingsw.dietiDeals24.model.Buyer;
import com.ingsw.dietiDeals24.model.Seller;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.model.enumeration.Region;
import com.ingsw.dietiDeals24.model.enumeration.Role;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserDao {
    @GET("user/{email}/buyer")
    Call<Buyer> getBuyerByEmail(@Path("email") String email, @Header("Authorization") String token);

    @GET("user/{email}/seller")
    Call<Seller> getSellerByEmail(@Path("email") String email, @Header("Authorization") String token);

    @PUT("user/{email}/{role}/updateRegion")
    Call<User> updateRegion(@Path("email") String email, @Path("role") Role role, @Body Region region, @Header("Authorization") String token);

    @PUT("user/{email}/{role}/updateBio")
    Call<User> updateBio(@Path("email") String email, @Path("role") Role role, @Body String bio, @Header("Authorization") String token);

    /*
    @PUT("user/{email}/updateRole")
    Call<User> updateRole(@Path("email") String email, @Body Role role, @Header("Authorization") String token);
     */

    /*
    @POST("user")
    Call<User> addAccount(@Body User user, @Header("Authorization") String token);
     */

    @GET("user/does-account-exist/{email}/{role}")
    Call<Boolean> doesAccountExist(@Path("email") String email, @Path("role") Role role, @Header("Authorization") String token);

    /*
    @GET("user/{email}/hasBankAccount")
    Call<Boolean> hasBankAccount(@Path("email") String email, @Header("Authorization") String token);
     */
}
