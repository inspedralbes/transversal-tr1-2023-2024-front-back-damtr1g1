package com.example.tr1_takeaway.loginService;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface LoginApiService {


    @GET("/api/validateLogin")
    Call<LoginResponse> validarLogin(
            @Query("nom") String usuario,
            @Query("contrasenya") String contrasenya
    );

    @GET("/api/getUserDataByName")
    Call<LoginResponse> getUserDataByName(
            @Query("nom") String nom
    );
}

