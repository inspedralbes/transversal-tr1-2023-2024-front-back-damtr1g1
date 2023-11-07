package com.example.tr1_takeaway.ui.profile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProfileApiService {
    @GET("/api/getUserDataByName")
    Call<ProfileDataParse> getUserDataByName(
            @Query("nom") String nom
    );
}
