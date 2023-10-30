package com.example.tr1_takeaway.loginService;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface LoginApiService {


    @GET("/api/validacioLogin")
    Call<LoginResponse> validarLogin(
            @Query("usuario") String usuario,
            @Query("contrasenya") String contrasenya
    );
}

