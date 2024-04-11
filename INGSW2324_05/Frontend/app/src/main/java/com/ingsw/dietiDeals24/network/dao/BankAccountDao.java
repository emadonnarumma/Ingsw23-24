package com.ingsw.dietiDeals24.network.dao;

import com.ingsw.dietiDeals24.model.BankAccount;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BankAccountDao {
    @POST("bankAccount/{email}")
    Call<BankAccount> addBankAccount(@Path("email") String email, @Body BankAccount bankAccount, @Header("Authorization") String token);

    @PUT("bankAccount/{id}")
    Call<BankAccount> updateBankAccount(@Path("id") Integer id, @Body BankAccount bankAccount, @Header("Authorization") String token);
}
