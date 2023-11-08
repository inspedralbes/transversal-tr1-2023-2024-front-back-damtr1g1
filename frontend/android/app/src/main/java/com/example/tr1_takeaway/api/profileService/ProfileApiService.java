package com.example.tr1_takeaway.api.profileService;

import com.example.tr1_takeaway.ui.profile.ProfileDataParse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProfileApiService {
    @GET("/api/getUserDataByName")
    Call<ProfileDataParse> getUserDataByName(
            @Query("nom") String nom
    );
}
