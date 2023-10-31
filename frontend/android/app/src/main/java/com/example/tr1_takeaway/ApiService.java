package com.example.tr1_takeaway;

import com.example.tr1_takeaway.loginService.LoginResponse;
import com.example.tr1_takeaway.ui.shop.ProductDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {


    @GET("/api/validateLogin")
    Call<LoginResponse> validarLogin(
            @Query("nom") String usuario,
            @Query("contrasenya") String contrasenya
    );

    @GET("/api/getProducts")
    Call<List<ProductDataModel>> getProducts();
}

