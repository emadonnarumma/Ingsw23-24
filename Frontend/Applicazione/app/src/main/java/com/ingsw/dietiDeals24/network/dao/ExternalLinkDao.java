package com.ingsw.dietiDeals24.network.dao;

import com.ingsw.dietiDeals24.model.ExternalLink;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ExternalLinkDao {
    @POST("externalLink/{email}")
    Call<ExternalLink> addExternalLink(@Path("email") String email, @Body ExternalLink externalLink, @Header("Authorization") String token);

    @PUT("externalLink/{id}")
    Call<ExternalLink> updateExternalLink(@Path("id") Integer id, @Body ExternalLink externalLink, @Header("Authorization") String token);

    @DELETE("externalLink/{id}")
    Call<Void> deleteExternalLink(@Path("id") Integer id, @Header("Authorization") String token);
}
