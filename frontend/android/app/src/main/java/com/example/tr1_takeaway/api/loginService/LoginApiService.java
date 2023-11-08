package com.example.tr1_takeaway.api.loginService;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface LoginApiService {
    @GET("/api/validateLogin")
    Call<LoginResponse> validateLogin(
            @Query("nom") String nom,
            @Query("contrasenya") String contrasenya
    );
}

