package com.example.restaurantapp.apis;

import com.example.restaurantapp.models.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("search")
    Call<Search> search(
            @Query("q") String query,
            @Header("user-key") String apiKey
    );
}
