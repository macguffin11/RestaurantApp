package com.example.restaurantapp.apis;

import com.example.restaurantapp.models.Geocode;
import com.example.restaurantapp.models.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("search")
    Call<Search> search(
            @Query("entity_id") int entityId,
            @Query("entity_type") String entityType,
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Header("user-key") String apiKey
    );

    @GET("search")
    Call<Search> search(
            @Query("q") String q,
            @Query("entity_id") int entityId,
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Header("user-key") String apiKey
    );

    @GET("geocode")
    Call<Geocode> geocode(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Header("user-key") String apiKey
    );
}
